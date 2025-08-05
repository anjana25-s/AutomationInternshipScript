package com.promilo.automation.resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;

public class ExcelUtil {

    private Workbook workbook;
    private Sheet sheet;
    private String path;

    public ExcelUtil(String excelPath, String sheetName) throws IOException {
        this.path = excelPath;
        FileInputStream fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        fis.close(); // ✅ close input stream
    }

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
                    return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void setCellData(int rowNum, int colNum, String value) throws IOException {
        Row row = sheet.getRow(rowNum);
        if (row == null)
            row = sheet.createRow(rowNum);

        Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(value);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos); // ✅ ensures data is flushed
        }
    }
    
    
    
    public int getColumnIndex(String columnName) {
        Row headerRow = sheet.getRow(0); // assuming first row has headers
        if (headerRow == null) throw new RuntimeException("Header row not found");

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && columnName.equalsIgnoreCase(cell.getStringCellValue().trim())) {
                return i;
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }


    public int getRowIndex(String testCaseId) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            String id = getCellData(i, 0);
            if (id.equalsIgnoreCase(testCaseId))
                return i;
        }
        throw new RuntimeException("TestCase ID not found: " + testCaseId);
    }

    public Object[][] getTestData(String filterTestCaseId) {
        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[1][colCount];

        for (int i = 1; i <= rowCount; i++) {
            String testCaseId = getCellData(i, 0);
            if (testCaseId.equalsIgnoreCase(filterTestCaseId)) {
                for (int j = 0; j < colCount; j++) {
                    data[0][j] = getCellData(i, j);
                }
                break;
            }
        }
        return data;
    }
}
