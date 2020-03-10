package com.joneshsu.great.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    String fileName;

    public ExcelHelper(String fileName) {
        this.fileName = fileName;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void createFile(ArrayList<Goods> goodsList) {
        System.out.println("Start create excel file");
        int rowNum = 0;
        for (Iterator<Goods> iterator = goodsList.iterator(); iterator.hasNext(); rowNum++) {
            Row row = sheet.createRow(rowNum);
            Goods goods = iterator.next();
            String[] data = goods.getExportableData();
            int colNum = 0;
            for (String columnData: data) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(columnData);
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}