package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HelpfulMethods {

    Application app;

    public HelpfulMethods(Application app) {
        this.app = app;
    }

    double minLatitude = Double.MAX_VALUE;
    double maxLatitude = Double.MIN_VALUE;
    double minLongitude = Double.MAX_VALUE;
    double maxLongitude = Double.MIN_VALUE;

    ArrayList<Double> longitudeValues = new ArrayList<>();
    ArrayList<Double> latitudeValues = new ArrayList<>();

    public void BoundaryCalculator() {

        File[] files = app.getSelectedFolder().listFiles();

        if (files != null && files.length > 0) {
            File firstFile = files[0];
            try (BufferedReader br = new BufferedReader(new FileReader(firstFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[0]);

                    minLatitude = Math.min(minLatitude, latitude + 10);
                    maxLatitude = Math.max(maxLatitude, latitude - 10);
                    minLongitude = Math.min(minLongitude, longitude + 15);
                    maxLongitude = Math.max(maxLongitude, longitude - 15);

                    longitudeArray();
                    latitudeArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Нет файлов данных в указанной папке.");
        }
    }

    private void longitudeArray() {
        longitudeValues.clear();
        for (double i = minLongitude; i <= maxLongitude; i += 2.5) {
            longitudeValues.add(i);
        }
    }

    private void latitudeArray() {
        latitudeValues.clear();
        for (double i = minLatitude; i <= maxLatitude; i += 2.5) {
            latitudeValues.add(i);
        }
    }

    public String magnet(double value, int longOrLat) {
        ArrayList<Double> ar;
        if (longOrLat == 0) {
            ar = longitudeValues;
        }
        else {
            ar = latitudeValues;
        }
        double nearest = -1;
        double bestDistanceFoundYet = Double.MAX_VALUE;
        for (Double aDouble : ar) {
            if (aDouble == value) {
                return String.valueOf(value);
            } else {
                double d = Math.abs(value - aDouble);
                if (d < bestDistanceFoundYet) {
                    bestDistanceFoundYet = d;
                    nearest = aDouble;
                }
            }
        }
        return String.valueOf(nearest);
    }
}
