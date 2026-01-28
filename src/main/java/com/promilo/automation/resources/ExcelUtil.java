package com.promilo.automation.resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private Workbook workbook;
    private Sheet sheet;
    private String path;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public ExcelUtil(String excelPath, String sheetName) throws IOException {
        this.path = excelPath;
        FileInputStream fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        fis.close();
    }

    // -------------------------
    // READ CELL BY INDEX
    // -------------------------
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == Math.floor(numValue)) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }

    // -------------------------
    // WRITE CELL
    // -------------------------
    public void setCellData(int rowNum, int colNum, String value) throws IOException {
        Row row = sheet.getRow(rowNum);
        if (row == null)
            row = sheet.createRow(rowNum);

        Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(value);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }
    }

    // -------------------------
    // GET COLUMN INDEX BY HEADER NAME
    // -------------------------
    public int getColumnIndex(String columnName) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null)
            throw new RuntimeException("Header row not found in Excel sheet!");

        for (int col = 0; col < headerRow.getLastCellNum(); col++) {
            Cell cell = headerRow.getCell(col);
            if (cell != null && columnName.equalsIgnoreCase(cell.getStringCellValue().trim())) {
                return col;
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }

    // -------------------------
    // GET CELL DATA USING COLUMN NAME + ROW NUMBER
    // -------------------------
    public String getCellData(String columnName, int rowNum) {
        int colNum = getColumnIndex(columnName);
        return getCellData(rowNum, colNum);
    }

    // -------------------------
    // GET ROW INDEX BY TESTCASE ID (FIRST COLUMN)
    // -------------------------
    public int getRowIndex(String testCaseId) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (getCellData(i, 0).equalsIgnoreCase(testCaseId)) {
                return i;
            }
        }
        throw new RuntimeException("TestCase ID not found: " + testCaseId);
    }

    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    // -------------------------
    // GET TEST DATA (ENTIRE ROW)
    // -------------------------
    public Object[][] getTestData(String filterTestCaseId) {
        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[1][colCount];

        for (int i = 1; i <= rowCount; i++) {
            if (getCellData(i, 0).equalsIgnoreCase(filterTestCaseId)) {
                for (int j = 0; j < colCount; j++) {
                    data[0][j] = getCellData(i, j);
                }
                break;
            }
        }
        return data;
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }
}
