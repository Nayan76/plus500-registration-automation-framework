package com.plus500.automation.utils;

import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelUtils {

    static XSSFWorkbook workbook = new XSSFWorkbook();
    static XSSFSheet sheet = workbook.createSheet("Test Data");
    static int rowNum = 0;

    static {
        // Create header row
        XSSFRow header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Step Name");
        header.createCell(1).setCellValue("URL");
        header.createCell(2).setCellValue("Status");
        header.createCell(3).setCellValue("Timestamp");
    }

    public static void writeData(String step, String url, String status) {

        try {
            XSSFRow row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(step);
            row.createCell(1).setCellValue(url);
            row.createCell(2).setCellValue(status);

            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            row.createCell(3).setCellValue(time);

            // Create folder if not exists
            String path = System.getProperty("user.dir") + "/target/excel/";
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(path + "TestResults.xlsx");
            workbook.write(fos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}