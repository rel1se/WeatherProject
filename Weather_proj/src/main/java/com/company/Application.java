package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//117.5 57.5
public class Application {
    public void GUIExec() {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(new Color(255, 250, 250));
        JButton button = new JButton("Создать таблицу");
        button.setBounds(150, 200, 220, 50);
        button.setBackground(new Color(255,255,255));
        button.setBorder(new LineBorder(new Color(0,0,0)));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(211,211,211)); // Устанавливаем новый цвет окантовки при наведении
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255,255,255)); // Возвращаем стандартный цвет окантовки при выходе курсора
            }
        });
        frame.add(button);

        JLabel title = new JLabel("Введите данные для ввода");
        title.setBounds(150, 0, 500, 100);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setPreferredSize(new Dimension(400, 100));
        frame.add(title);

        // Добавляем подписи к полям ввода текста
        JLabel longitudeLabel = new JLabel("Долгота:");
        longitudeLabel.setBounds(50, 100, 100, 30);
        frame.add(longitudeLabel);


        JLabel latitudeLabel = new JLabel("Широта:");
        latitudeLabel.setBounds(50, 150, 100, 30);
        frame.add(latitudeLabel);

        List<Double> longitudeValues = new ArrayList<>();
        for (double lon = 85.0; lon <= 125.0; lon += 2.50) {
            longitudeValues.add(lon);
        }
        // Создаем JComboBox для выбора долготы
//        JComboBox<Double> longitudeComboBox = new JComboBox<>(longitudeValues.toArray(new Double[0]));
//        longitudeComboBox.setBounds(150, 100, 220, 30);
//        longitudeComboBox.setSelectedItem(117.5);
//        longitudeComboBox.setBackground(new Color(255,255,255));
//        frame.add(longitudeComboBox);
        JTextField longitudeTextField = new JTextField();
        longitudeTextField.setBounds(150, 100, 220, 30);
        longitudeTextField.setText("117.5"); // Устанавливаем начальное значение
        longitudeTextField.setBackground(new Color(255, 255, 255));
        frame.add(longitudeTextField);

        // Создаем список значений для выбора широты
        List<Double> latitudeValues = new ArrayList<>();
        for (double lat = 45.0; lat <= 70.0; lat += 2.50) {
            latitudeValues.add(lat);
        }
        // Создаем JComboBox для выбора широты
        JTextField latitudeTextField = new JTextField();
        latitudeTextField.setBounds(150, 150, 220, 30);
        latitudeTextField.setText("57.5");
        latitudeTextField.setBackground(new Color(255,255,255));
        frame.add(latitudeTextField);

        // Добавляем поля ввода текста
        JTextField longitudeField = new JTextField();
        longitudeField.setBounds(150, 100, 100, 30);
        frame.add(longitudeField);

        JTextField latitudeField = new JTextField();
        latitudeField.setBounds(150, 150, 100, 30);
        frame.add(latitudeField);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(150, 300, 220, 25);
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setVisible(true);
        progressBar.setVisible(false);
        frame.add(progressBar);

        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setTitle("Weather project v1.0");
        frame.setLayout(null);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalPoints = 1603181;
                button.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        progressBar.setVisible(true);
                        double longitude = Double.parseDouble(longitudeTextField.getText());
                        double latitude = Double.parseDouble(latitudeTextField.getText());
                        SpreadSheet points = new SpreadSheet(longitude, latitude);
                        try {
                            points.excelFileWriter(progressBar, totalPoints);
                            JOptionPane.showMessageDialog(frame, "Файл создан", "Success", JOptionPane.INFORMATION_MESSAGE);
                            button.setEnabled(true);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return null;
                    }
                };
                worker.addPropertyChangeListener(evt -> {
                    if (evt.getPropertyName().equals("state") && SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                        progressBar.setVisible(false);
                    }
                });
                worker.execute();
            }
        });
    }
}