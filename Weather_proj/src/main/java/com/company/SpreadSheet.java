package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class SpreadSheet {
    double[][] bluePoints;

    public SpreadSheet(double longitude, double latitude) {
        FindPoints findPoints = new FindPoints(longitude, latitude);
        bluePoints = findPoints.calcPoints().clone();
    }

    /*public void readBluePoints() throws FileNotFoundException {
        Scanner bluePointsReader = new Scanner (new File("16Points.txt"));
        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 2; ++j)
            {
                if(bluePointsReader.hasNextDouble())
                {
                    bluePoints[i][j] = bluePointsReader.nextDouble();
                }
            }
        }
    }*/

    public void excelFileWriter(JProgressBar progressBar, int totalPoints) throws IOException {
        int processedPoints = 0;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        Row row;
        Cell cell;
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("Date");
        for (int i = 1; i < 17; i++) {
            cell = row.createCell(i);
            cell.setCellValue("P" + i);
        }
        cell = row.createCell(18);
        cell.setCellValue("Result");
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
            }
            else if (pastDate != ((int) Double.parseDouble(inf[2]))) {
                rows++;
                row = sheet.createRow(rows);
                all16 = 0;
                pastDate = (int) Double.parseDouble(inf[2]);
            }

            for (int i = 0; i < 16; i++) {
                if (bluePoints[i][0] == Double.parseDouble(inf[0]) &&
                        bluePoints[i][1] == Double.parseDouble(inf[1])) {
                    calendar.setTimeInMillis((long) (Double.parseDouble(inf[2]) - 8036) * 24 * 60 * 60 * 1000);
                    date = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                            (calendar.get(Calendar.MONTH) + 1) + "." +
                            calendar.get(Calendar.YEAR);
                    if (all16 == 0) {
                        cell = row.createCell(0);
                        cell.setCellValue(date);
                    }
                    cell = row.createCell(i + 1);
                    cell.setCellValue(Double.parseDouble(inf[3]) / 100);
                    pressure[i] = Double.parseDouble(inf[3]) / 100;
                    all16++;
                    if (all16 == 16) {
                        pascalCounter.pressureFiller(pressure);
                        pascalCounter.circulationIndexCounter();
                        cell = row.createCell(18);
                        cell.setCellValue(pascalCounter.classificationOfDays());
                    }
                    break;
                }
            }
            processedPoints++;
            double progress = ((double)processedPoints / (double) totalPoints) * 100; // Рассчитываем текущий прогресс в процентах
            progressBar.setValue((int) progress); // Устанавливаем значение прогресса в прогресс бар
        }

        File f = new File("aboutPoints.xlsx");
        String path = f.getAbsolutePath();


        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
    }
}
