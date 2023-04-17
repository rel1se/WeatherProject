package com.company;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        SpreadSheet points = new SpreadSheet();
        points.readBluePoints();
        points.excelFileWriter();

    }
}
