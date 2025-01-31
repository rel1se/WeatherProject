package com.company;

import java.util.ArrayList;
import java.util.List;

public class NewPascalCounter {
    private double[] p = {};
    private double F;
    private String Rumb;
    private double Z, s, w;
    private final double latitude;

    NewPascalCounter(double latitude) {
        this.latitude = Math.toRadians(latitude);
    }

    public void pressureFiller(double[] ar) {
        p = ar.clone();
    }

    public double[] circulationIndexCounter() {
        double koefZW1 = Math.round(Math.sin(latitude) / Math.sin(latitude - Math.toRadians(5)) * 100) / 100.0;
        double koefZW2 = Math.round(Math.sin(latitude) / Math.sin(latitude + Math.toRadians(5)) * 100) / 100.0;
        double koefZS = Math.round(1 / (2 * Math.pow(Math.cos(latitude), 2)) * 100) / 100.0;
        double koefS = Math.round(1 / Math.cos(latitude) * 100) / 100.0;
        s = koefS * (0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]));
        w = 0.5 * (p[11] + p[12]) - 0.5 * (p[3] + p[4]);
        F = Math.pow(Math.pow(s, 2) + Math.pow(w, 2), 0.5);
        double d = Math.toDegrees(Math.atan(w / s));
        if ((s > 0 && w > 0) || (s > 0 && w < 0)) {
            d += 180;
        } else if (s < 0 && w > 0) {
            d += 360;
        }

        if (d > 337.5) Rumb = "N";
        else if (d > 292.5) Rumb = "NW";
        else if (d > 247.5) Rumb = "W";
        else if (d > 202.5) Rumb = "SW";
        else if (d > 157.5) Rumb = "S";
        else if (d > 112.5) Rumb = "SE";
        else if (d > 67.5) Rumb = "E";
        else if (d > 22.5) Rumb = "NE";
        else Rumb = "N";
        double ZS = koefZS * (0.25 * (p[5] + 2 * p[9] + p[13]) - 0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]) + 0.25 * (p[2] + 2 * p[6] + p[10]));
        double ZW = koefZW1 * (0.5 * (p[14] + p[15]) - 0.5 * (p[7] + p[8])) - koefZW2 * (0.5 * (p[7] + p[8]) - 0.5 * (p[0] + p[1]));
        Z = ZW + ZS;

        List<Double> resultList = new ArrayList<>();
        resultList.add(w);
        resultList.add(s);
        resultList.add(F);
        resultList.add(Z);
        return resultList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public String classificationOfDays(double k1, double k2) {
        double absZ = Math.abs(Z);
        String AT;
        String AU;
        if (s != 0) {
            if (absZ > (k1 * F) && Z > 0) AT = "C";
            else if (absZ > (k1 * F) && Z < 0) AT = "A";
            else if (absZ < F) AT = Rumb; // PUR-DIR
            else if (absZ > F && Z > 0) AT = "C" + Rumb; // ZN
            else if (absZ > F && Z < 0) AT = "A" + Rumb; // AZ
            else AT = "1";

            if (F < k2 && absZ < k2) {
                AU = "U";
            } else {
                AU = AT;
            }
            return AU;
        }
        else {
            if (F < k2 && absZ < k2) {
                return "U";
            }
            else if (absZ < F && w > 0) {
                return "W";
            }
            else if (absZ < F && w < 0) {
                return "E";
            }
            else if (absZ > k1 * F && Z > 0) {
                return "C";
            }
            else if (absZ > k1 * F && Z < 0) {
                return "A";
            }
            else if (F < absZ && absZ < k1 * F && Z > 0 && w > 0) {
                return "CW";
            }
            else if (F < absZ && absZ < k1 * F && Z > 0 && w < 0) {
                return "CE";
            }
            else if (F < absZ && absZ < k1 * F && Z < 0 && w > 0) {
                return "AW";
            }
            else if (F < absZ && absZ < k1 * F && Z < 0 && w < 0) {
                return "AE";
            }
            else {
                return "U";
            }
        }
    }
}

/*
else if (absZ < F) {
        if (s == 0) {
AT = w < 0 ? "E":"W";
        }
        else {
AT = Rumb; // PUR-DIR
            }
                    }
                    else if (absZ > F && Z > 0) {
        if (s == 0) {
AT = w < 0 ? "CE":"CW";
        }
        else {
AT = "C" + Rumb; // ZN
            }
                    }
                    else if (absZ > F && Z < 0) {
        if (s == 0) {
AT = w < 0 ? "AE":"AW";
        }
        else {
AT = "A" + Rumb; // AZ
            }
                    }*/
