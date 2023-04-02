
public class PascalCounter {
    private double[] p = {1032.86, 1035.50, 1037.69,1034.46, 1035.81, 1037.00, 1035.57, 1035.46, 1034.83, 1034.47, 1032.36, 1034.73,1027.11,1022.35,1033.13,1029.35};
    private double W;
    private double S;
    private double D;
    private String Rumb;
    private double F;
    private double ZW;
    private double ZS;
    private double Z;

//    PascalCounter(int p[]) {
//        this.p = p;
//    }


    public double circulationIndexCounter() {
        W = 0.5 * (p[11] + p[12]) - 0.5 * (p[3] + p[4]);
        S = 1.86 * (0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]));
        int n = 0;
        if (S < 0 && W < 0) {
            n = 0;
        } else if ((S > 0 && W > 0) || (S > 0 && W < 0)) {
            n = 180;
        } else n = 360;
        D = Math.toDegrees(Math.atan(W / S)) + n;
        if (D > 337.5) Rumb = "N";
        else if (D > 292.5) Rumb = "NW";
        else if (D > 247.5) Rumb = "W";
        else if (D > 202.5) Rumb = "SW";
        else if (D > 157.5) Rumb = "S";
        else if (D > 112.5) Rumb = "SE";
        else if (D > 67.5) Rumb = "E";
        else if (D > 22.5) Rumb = "NE";
        else Rumb = "N";
        F = Math.pow(Math.pow(S, 2) + Math.pow(W, 2), 0.5);
        ZW = 1.06 * (0.5 * (p[14] + p[15]) - 0.5 * (p[7] + p[8])) - 0.95 * (0.5 * (p[7] + p[8]) - 0.5 * (p[0] + p[1]));
        ZS = 1.73 * (0.25 * (p[5] + 2 * p[9] + p[13]) - 0.25 * (p[4] + 2 * p[8] + p[12]) - 0.25 * (p[3] + 2 * p[7] + p[11]) + 0.25 * (p[2] + 2 * p[6] + p[10]));
        return Z = ZW + ZS;
    }

    public String classificationOfDays() {
        String cyclone = "";
        if (Math.abs(Z) < F) return Rumb;
        else if (Math.abs(Z) > F && Z > 0)  cyclone = "ZN";
        else if (Math.abs(Z) > F && Z < 0)  cyclone = "AZ";
        else if (Math.abs(Z) > 2*F && Z > 0)  cyclone = "C";
        else if (Math.abs(Z) > 2*F && Z < 0)  cyclone = "A";
        if ((cyclone.equals("C")) && (Math.abs(Z) < 2*F) && (Math.abs(Z) > F))
            return cyclone + Rumb;
        else if ((cyclone.equals("A")) && (Math.abs(Z) < 2*F) && (Math.abs(Z) > F))
            return cyclone + Rumb;
        else if ((cyclone.equals("ZN")))
            return "C" + Rumb;
        else if ((cyclone.equals("AZ")))
            return "A" + Rumb;
        else if (Math.abs(Z) < 6 && F < 6)
            return "U";
        else return "Неверные входные данные";
    }
}
