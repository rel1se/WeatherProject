package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Application {

    private final HelpfulMethods helpfulMethods = new HelpfulMethods(this);
    private File selectedFolder;
    private boolean divideByHundred = true; // Флаг для деления на 100

    public File getSelectedFolder() {
        return selectedFolder;
    }

    public void GUIExec() {
        // Создание окна
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(new Color(255, 250, 250));

        // Подпись сверху
        JLabel title = new JLabel("Введите данные для расчетов");
        title.setBounds(150, -10, 500, 100);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setPreferredSize(new Dimension(400, 100));
        frame.add(title);

        // Создание рычажка (Toggle Button) для переключения деления на 100
        JToggleButton divideToggle = new JToggleButton("Делить на 100", true);
        divideToggle.setBounds(150, 60, 220, 30);
        divideToggle.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                divideByHundred = true;
                divideToggle.setText("Делить на 100");
            } else {
                divideByHundred = false;
                divideToggle.setText("Без деления");
            }
        });
        frame.add(divideToggle);

        // Подпись долготы
        JLabel longitudeLabel = new JLabel("Долгота:");
        longitudeLabel.setBounds(50, 100, 100, 30);
        frame.add(longitudeLabel);

        // Подпись широты
        JLabel latitudeLabel = new JLabel("Широта:");
        latitudeLabel.setBounds(50, 160, 100, 30);
        frame.add(latitudeLabel);

        // Поле для долготы
        JTextField longitudeTextField = new JTextField();
        longitudeTextField.setBounds(150, 100, 220, 30);
        longitudeTextField.setText("117.5");
        longitudeTextField.setBackground(new Color(255, 255, 255));
        frame.add(longitudeTextField);

        // Подпись для границ долготы
        JLabel longitudeBoundaries = new JLabel();
        longitudeBoundaries.setBounds(150, 130, 350, 20);
        frame.add(longitudeBoundaries);

        // Поле для широты
        JTextField latitudeTextField = new JTextField();
        latitudeTextField.setBounds(150, 160, 220, 30);
        latitudeTextField.setText("57.5");
        latitudeTextField.setBackground(new Color(255, 255, 255));
        frame.add(latitudeTextField);

        // Подпись для границ широты
        JLabel latitudeBoundaries = new JLabel();
        latitudeBoundaries.setBounds(150, 190, 350, 20);
        frame.add(latitudeBoundaries);

        // Поле для k1
        JTextField k1 = new JTextField();
        k1.setText("2");
        k1.setBounds(375, 100, 40, 30);
        k1.setBackground(new Color(255, 255, 255));
        frame.add(k1);

        // Подпись k1
        JLabel k1Label = new JLabel("k1", SwingConstants.CENTER);
        k1Label.setBounds(375, 88, 40, 10);
        frame.add(k1Label);

        // Поле для k2
        JTextField k2 = new JTextField();
        k2.setText("6");
        k2.setBounds(375, 160, 40, 30);
        k2.setBackground(new Color(255, 255, 255));
        frame.add(k2);

        // Подпись k2
        JLabel k2Label = new JLabel("k2", SwingConstants.CENTER);
        k2Label.setBounds(375, 148, 40, 10);
        frame.add(k2Label);

        // Строка прогресса
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(150, 370, 220, 25);
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setVisible(false);
        frame.add(progressBar);

        // Кнопка создания таблицы
        JButton createTableButton = new JButton("Создать таблицу");
        createTableButton.setBounds(150, 230, 220, 50);
        createTableButton.setBackground(new Color(255, 255, 255));
        createTableButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        createTableButton.setEnabled(false);

        // Настройка курсора на кнопке создания таблицы
        createTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createTableButton.setBackground(new Color(211, 211, 211));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createTableButton.setBackground(new Color(255, 255, 255));
            }
        });
        frame.add(createTableButton);

        // Кнопка для выбора папки
        JButton chooseFolderButton = new JButton("Выбрать папку");
        chooseFolderButton.setBounds(150, 300, 220, 50);

        // Настройка курсора на кнопке выбора папок
        chooseFolderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                chooseFolderButton.setBackground(new Color(211, 211, 211));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chooseFolderButton.setBackground(new Color(255, 255, 255));
            }
        });
        frame.add(chooseFolderButton);

        // Настройка лисенера кнопки для выбора папок
        chooseFolderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./coordsData"));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFolder = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(frame, "Выбрана папка: " + selectedFolder.getAbsolutePath());
                helpfulMethods.BoundaryCalculator();
                longitudeBoundaries.setText(String.format("Изменяется от %.2f до %.2f", helpfulMethods.minLongitude, helpfulMethods.maxLongitude));
                latitudeBoundaries.setText(String.format("Изменяется от %.2f до %.2f", helpfulMethods.minLatitude, helpfulMethods.maxLatitude));
                createTableButton.setEnabled(true);
            }
        });

        // Настройка лисенера кнопки создания таблицы
        createTableButton.addActionListener(e -> {
            createTableButton.setEnabled(false);
            chooseFolderButton.setEnabled(false);
            int totalPoints = 1603181;
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    progressBar.setVisible(true);
                    double longitude = Double.parseDouble(longitudeTextField.getText());
                    double latitude = Double.parseDouble(latitudeTextField.getText());
                    double k1Double = Double.parseDouble(k1.getText());
                    double k2Double = Double.parseDouble(k2.getText());
                    SpreadSheet points = new SpreadSheet(longitude, latitude, k1Double, k2Double, selectedFolder, Application.this);
                    try {
                        points.excelFileWriter(progressBar, totalPoints);
                        JOptionPane.showMessageDialog(frame, "Файл создан", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        createTableButton.setEnabled(true);
                        chooseFolderButton.setEnabled(true);
                        progressBar.setVisible(false);
                    }
                    return null;
                }
            };
            worker.execute();
        });

        // Проверка ввода данных для долготы
        longitudeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    double longitude = Double.parseDouble(longitudeTextField.getText());
                    if (longitude < helpfulMethods.minLongitude || longitude > helpfulMethods.maxLongitude) {
                        longitudeTextField.setBorder(new LineBorder(Color.RED));
                        createTableButton.setEnabled(false);
                        longitudeBoundaries.setForeground(Color.RED);
                    } else {
                        longitudeTextField.setBorder(new LineBorder(Color.BLACK));
                        createTableButton.setEnabled(true);
                        longitudeBoundaries.setForeground(Color.BLACK);
                        longitudeTextField.setText(helpfulMethods.magnet(Double.parseDouble(longitudeTextField.getText()), 0));
                    }
                } catch (NumberFormatException ex) {
                    longitudeTextField.setBorder(new LineBorder(Color.RED));
                    createTableButton.setEnabled(false);
                    longitudeBoundaries.setForeground(Color.RED);
                }
            }
        });

        // Проверка ввода данных для широты
        latitudeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    double latitude = Double.parseDouble(latitudeTextField.getText());
                    if (latitude < helpfulMethods.minLatitude || latitude > helpfulMethods.maxLatitude) {
                        latitudeTextField.setBorder(new LineBorder(Color.RED));
                        createTableButton.setEnabled(false);
                        latitudeBoundaries.setForeground(Color.RED);
                    } else {
                        latitudeTextField.setBorder(new LineBorder(Color.BLACK));
                        createTableButton.setEnabled(true);
                        latitudeBoundaries.setForeground(Color.BLACK);
                        latitudeTextField.setText(helpfulMethods.magnet(Double.parseDouble(latitudeTextField.getText()), 1));
                    }
                } catch (NumberFormatException ex) {
                    latitudeTextField.setBorder(new LineBorder(Color.RED));
                    createTableButton.setEnabled(false);
                    latitudeBoundaries.setForeground(Color.RED);
                }
            }
        });

        // Настройки окна
        frame.setSize(500, 450);
        frame.setResizable(false);
        frame.setTitle("Weather project v1.1");
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public boolean isDivideByHundred() {
        return divideByHundred;
    }
}
