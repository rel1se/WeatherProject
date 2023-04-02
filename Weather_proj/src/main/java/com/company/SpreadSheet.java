package com.company;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SpreadSheet {
    String path;


    public SpreadSheet(String path) {
        this.path = path;
    }

    public String readCell() throws IOException {
        FileInputStream spread = new FileInputStream("aboutPoints.xlsx");
        XSSFWorkbook excel = new XSSFWorkbook(spread);
        Sheet sheet = excel.getSheet("Sheet1");
        Row row = sheet.getRow(2);
        return String.valueOf(row.getCell(2));
    }
}
