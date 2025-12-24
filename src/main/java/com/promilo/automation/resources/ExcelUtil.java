package com.promilo.automation.resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private Workbook workbook;  // Represents the entire Excel file
    private Sheet sheet;        // Represents a single sheet in Excel
    private String path;        // Stores file path of the Excel

    /**
     * Constructor - Initializes Excel workbook and sheet
     * @param excelPath Path to Excel file
     * @param sheetName Sheet name to work with
     */
    public ExcelUtil(String excelPath, String sheetName) throws IOException {
        this.path = excelPath;
        FileInputStream fis = new FileInputStream(path); // Open Excel file
        workbook = new XSSFWorkbook(fis);                // Load workbook
        sheet = workbook.getSheet(sheetName);            // Load specific sheet
        fis.close(); // ✅ Always close input stream
    }

    /**
     * Fetch data from a specific cell
     * @param rowNum Row index (0-based)
     * @param colNum Column index (0-based)
     * @return String value of the cell
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);   // Get row
        if (row == null) return "";       // Handle empty row
        Cell cell = row.getCell(colNum);  // Get cell
        if (cell == null) return "";      // Handle empty cell

        // Handle different cell types
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Return date in string format
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == Math.floor(numValue)) {
                        // Whole number (e.g., 9999.0 → "9999")
                        return String.valueOf((long) numValue);
                    } else {
                        // Decimal number (e.g., 9999.55 → "9999.55")
                        return String.valueOf(numValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Return formula as string
            default:
                return "";
        }
    }

    /**
     * Write data into a specific cell
     * @param rowNum Row index (0-based)
     * @param colNum Column index (0-based)
     * @param value  Value to write into the cell
     */
    public void setCellData(int rowNum, int colNum, String value) throws IOException {
        Row row = sheet.getRow(rowNum);
        if (row == null)
            row = sheet.createRow(rowNum); // Create row if not exists

        Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(value); // Set new value

        // Write changes back to file
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos); // ✅ Save updated data
        }
    }

    /**
     * Get column index by header name
     * @param columnName Name of the column (from header row)
     * @return Column index (0-based)
     */
    public int getColumnIndex(String columnName) {
        Row headerRow = sheet.getRow(0); // Assuming first row contains headers
        if (headerRow == null) throw new RuntimeException("Header row not found");

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && columnName.equalsIgnoreCase(cell.getStringCellValue().trim())) {
                return i; // Return matching column index
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }

    /**
     * Get row index by TestCase ID (first column assumed for IDs)
     * @param testCaseId Test case identifier
     * @return Row index (0-based)
     */
    public int getRowIndex(String testCaseId) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
            String id = getCellData(i, 0); // Read first column
            if (id.equalsIgnoreCase(testCaseId))
                return i; // Return matching row
        }
        throw new RuntimeException("TestCase ID not found: " + testCaseId);
    }

    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * Fetch test data for a specific TestCase ID (row-based)
     * @param filterTestCaseId ID of the test case
     * @return Object[][] containing the test data row
     */
    public Object[][] getTestData(String filterTestCaseId) {
        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[1][colCount]; // Only one row per TestCaseId

        for (int i = 1; i <= rowCount; i++) {
            String testCaseId = getCellData(i, 0); // Read first column
            if (testCaseId.equalsIgnoreCase(filterTestCaseId)) {
                for (int j = 0; j < colCount; j++) {
                    data[0][j] = getCellData(i, j); // Copy row into array
                }
                break;
            }
        }
        return data;
    }

    /**
     * Get total number of rows in sheet
     * (currently returning 0 → should be implemented)
     */
    public int getRowCount() {
        return sheet.getLastRowNum(); // ✅ Fix: return total row count
    }

	public String getCellData(String string, int r) {
		// TODO Auto-generated method stub
		return null;
	}
}
