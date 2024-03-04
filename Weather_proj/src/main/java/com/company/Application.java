package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//117.5 57.5
public class Application {
    public void GUIExec() {
        JFrame frame = new JFrame();
        JButton button = new JButton("Создать таблицу");
        button.setBounds(150, 200, 220, 50);
        frame.add(button);

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
        JComboBox<Double> longitudeComboBox = new JComboBox<>(longitudeValues.toArray(new Double[0]));
        longitudeComboBox.setBounds(150, 100, 100, 30);
        longitudeComboBox.setSelectedItem(117.5);
        frame.add(longitudeComboBox);

        // Создаем список значений для выбора широты
        List<Double> latitudeValues = new ArrayList<>();
        for (double lat = 45.0; lat <= 70.0; lat += 2.50) {
            latitudeValues.add(lat);
        }
        // Создаем JComboBox для выбора широты
        JComboBox<Double> latitudeComboBox = new JComboBox<>(latitudeValues.toArray(new Double[0]));
        latitudeComboBox.setBounds(150, 150, 100, 30);
        latitudeComboBox.setSelectedItem(57.5);
        frame.add(latitudeComboBox);

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

        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalPoints = 1603181;
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        progressBar.setVisible(true);
                        double longitude = (double) longitudeComboBox.getSelectedItem();
                        double latitude = (double) latitudeComboBox.getSelectedItem();
                        SpreadSheet points = new SpreadSheet(longitude, latitude);
                        try {
                            points.excelFileWriter(progressBar, totalPoints);
                            JOptionPane.showMessageDialog(frame, "Файл создан", "Success", JOptionPane.INFORMATION_MESSAGE);
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