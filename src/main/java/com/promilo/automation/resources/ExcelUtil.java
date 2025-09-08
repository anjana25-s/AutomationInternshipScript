package com.promilo.automation.resources;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    private Workbook workbook;
    private Sheet sheet;
    private FileInputStream fis;

    /**
     * Initializes ExcelUtil with the provided file path and sheet name.
     *
     * @param excelPath Absolute path of the Excel file
     * @param sheetName Name of the sheet to load
     * @throws IOException if file or sheet cannot be loaded
     */
    public ExcelUtil(String excelPath, String sheetName) throws IOException {
        try {
            fis = new FileInputStream(excelPath);
            workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("❌ Sheet '" + sheetName + "' not found in '" + excelPath + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("❌ Unable to load Excel file or sheet: " + e.getMessage());
        }
    }

    public Sheet getSheet() {
        return sheet;
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public String getCellData(int row, int col) {
        DataFormatter formatter = new DataFormatter();
        Row sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            return "";
        }
        Cell cell = sheetRow.getCell(col);
        if (cell == null) {
            return "";
        }
        return formatter.formatCellValue(cell);
    }

    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void setCellData(int rowIndex, int mailPhoneColIndex, String randomMobile) {
		// TODO Auto-generated method stub
		
	}
}
