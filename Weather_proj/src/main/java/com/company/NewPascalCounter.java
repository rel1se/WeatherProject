package com.company;

public class NewPascalCounter {
    private double[] p = {};
    private double F;
    private String Rumb;
    private double Z;

    NewPascalCounter() {
    }

    public void pressureFiller(double[] ar) {
        p = ar.clone();
    }

    public void circulationIndexCounter() {
        double s = 1.86 * (0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]));
        double w = 0.5 * (p[11] + p[12]) - 0.5 * (p[3] + p[4]);
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
        double ZS = 1.73 * (0.25 * (p[5] + 2 * p[9] + p[13]) - 0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]) + 0.25 * (p[2] + 2 * p[6] + p[10]));
        double ZW = 1.06 * (0.5 * (p[14] + p[15]) - 0.5 * (p[7] + p[8])) - 0.95 * (0.5 * (p[7] + p[8]) - 0.5 * (p[0] + p[1]));
        Z = ZW + ZS;
    }

    public String classificationOfDays() {
        double absZ = Math.abs(Z);
        String AT;
        if (absZ > (2 * F) && Z > 0) AT = "C";
        else if (absZ > (2 * F) && Z < 0) AT = "A";
        else if (absZ < F) AT = Rumb;
        else if (absZ > F && Z > 0) AT = "C" + Rumb;
        else if (absZ > F && Z < 0) AT = "A" + Rumb;
        else AT = "1";

        String AU;
        if (F < 6 && absZ < 6) {
            AU = "U";
        } else {
            AU = AT;
        }
        return AU;
    }
}
