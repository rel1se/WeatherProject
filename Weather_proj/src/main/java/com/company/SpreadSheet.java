package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpreadSheet {
    double longitude, latitude;
    double[][] bluePoints;

    public SpreadSheet(double longitude, double latitude) {
        FindPoints findPoints = new FindPoints(longitude, latitude);
        bluePoints = findPoints.calcPoints().clone();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private void cellNumericValue(int number, double value, Row row, CellStyle cellStyle) {
        Cell cell = row.createCell(number);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void cellStringValue(int number, String value, Row row, CellStyle cellStyle) {
        Cell cell = row.createCell(number);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    public void excelFileWriter(JProgressBar progressBar, int totalPoints) throws IOException {
        int processedPoints = 0;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.format(formatter) + ".xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        for (int i = 0; i <= 7; i++) {
            sheet.setColumnWidth(i, 3000);
        }
        CellStyle cellBorder = workbook.createCellStyle();
        cellBorder.setBorderBottom(BorderStyle.THIN);
        cellBorder.setBorderLeft(BorderStyle.THIN);
        cellBorder.setBorderRight(BorderStyle.THIN);
        cellBorder.setBorderTop(BorderStyle.THIN);
        CellStyle numericStyle = workbook.createCellStyle();
        numericStyle.cloneStyleFrom(cellBorder);
        DataFormat dFormat = workbook.createDataFormat();
        numericStyle.setDataFormat(dFormat.getFormat("0.00"));
        Row row;
        row = sheet.createRow(0);
        cellStringValue(0, "Дата", row, cellBorder);
        cellStringValue(1, "X", row, cellBorder);
        cellStringValue(2, "Y", row, cellBorder);
        cellStringValue(3, "Тип погоды", row, cellBorder);
        cellStringValue(4, "W", row, cellBorder);
        cellStringValue(5, "S", row, cellBorder);
        cellStringValue(6, "F", row, cellBorder);
        cellStringValue(7, "Z", row, cellBorder);
        File allCoords = new File("allCoords.txt");
        BufferedReader br = new BufferedReader(new FileReader(allCoords));
        String line;
        String[] inf;
        Calendar calendar = Calendar.getInstance();
        String date;
        int all16 = 0;
        int rows = 1;
        int pastDate = 22646;
        double[] pressure = new double[16];
        NewPascalCounter pascalCounter = new NewPascalCounter();
        row = sheet.createRow(rows);
        while ((line = br.readLine()) != null) {
            inf = line.split(" ");
            if (all16 == 16 && pastDate == ((int) Double.parseDouble(inf[2]))) {
                continue;
            } else if (pastDate != ((int) Double.parseDouble(inf[2]))) {
                rows++;
                row = sheet.createRow(rows);
                all16 = 0;
                pastDate = (int) Double.parseDouble(inf[2]);
            }

            for (int i = 0; i < 16; i++) {
                if (bluePoints[i][0] == Double.parseDouble(inf[0]) &&
                        bluePoints[i][1] == Double.parseDouble(inf[1])) {
                    calendar.setTimeInMillis((long) (Double.parseDouble(inf[2]) - 8036) * 24 * 60 * 60 * 1000);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    date = simpleDateFormat.format(calendar.getTime());
                    if (all16 == 0) {
                        cellStringValue(0, date, row, cellBorder);
                    }
                    cellNumericValue(1, longitude, row, cellBorder);
                    cellNumericValue(2, latitude, row, cellBorder);
                    pressure[i] = Double.parseDouble(inf[3]) / 100;
                    all16++;
                    if (all16 == 16) {
                        pascalCounter.pressureFiller(pressure);
                        double[] variables = pascalCounter.circulationIndexCounter();
                        cellStringValue(3, pascalCounter.classificationOfDays(), row, cellBorder);
                        cellNumericValue(4, variables[0], row, numericStyle);
                        cellNumericValue(5, variables[1], row, numericStyle);
                        cellNumericValue(6, variables[2], row, numericStyle);
                        cellNumericValue(7, variables[3], row, numericStyle);
                    }
                    break;
                }
            }
            processedPoints++;
            double progress = ((double) processedPoints / (double) totalPoints) * 100;
            progressBar.setValue((int) progress);
        }

        File result = new File(formattedDate);
        String path = result.getAbsolutePath();


        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
    }
}