package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    private String filePath;
    private Workbook workbook;
    private Sheet sheet;
    private FileInputStream fis;

    // Constructor - load file and sheet
    public ExcelUtility(String filePath, String sheetName) throws IOException {
        this.filePath = filePath;
        fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in " + filePath);
        }
    }

    // Get row count
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Get column count (based on first row)
    public int getColumnCount() {
        Row row = sheet.getRow(0);
        return (row == null) ? 0 : row.getPhysicalNumberOfCells();
    }

    // Get cell data as String
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";
        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    // Set cell data
    public void setCellData(int rowNum, int colNum, String value) {
        Row row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);
        Cell cell = row.getCell(colNum);
        if (cell == null) cell = row.createCell(colNum);
        cell.setCellValue(value);
    }

    // Save changes and close file
    public void saveAndClose() throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
        if (fis != null) fis.close();
    }

    // Close workbook without saving
    public void close() throws IOException {
        if (workbook != null) workbook.close();
        if (fis != null) fis.close();
    }
}

