package com.company;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String Path = "S:\\Work\\Weather_project\\Weather_Java\\aboutPoints.xlsx";
        SpreadSheet a = new SpreadSheet(Path);
        System.out.println(a.readCell());
    }
}
