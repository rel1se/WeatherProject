package com.company;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        SpreadSheet points = new SpreadSheet(117.5, 57.5);
        //points.readBluePoints();
        points.excelFileWriter();
    }
}
