package com.company;

import java.io.File;
import java.util.Arrays;

public class FindPoints {
    private final double longitude;
    private final double latitude;

    public FindPoints(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double[][] calcPoints() {
        double[][] redPoints = new double[16][2];
        double longitudeIter = longitude - 15;
        double latitudeIter = latitude + 5;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                redPoints[i * 4 + j + 2][0] = longitudeIter;
                redPoints[i * 4 + j + 2][1] = latitudeIter;
                longitudeIter += 10;
            }
            longitudeIter = longitude - 15;
            latitudeIter -= 5;
        }
        longitudeIter = longitude - 5;
        latitudeIter = latitude + 10;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                redPoints[i * 14 + j][0] = longitudeIter;
                redPoints[i * 14 + j][1] = latitudeIter;
                longitudeIter += 10;
            }
            longitudeIter = longitude - 5;
            latitudeIter -= 20;
        }
        return redPoints;
    }
}
