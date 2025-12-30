package com.promilo.automation.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadUtil {

    private List<Map<String, String>> dataList = new ArrayList<>();

    public ExcelReadUtil(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        Row header = sheet.getRow(0);
        int colCount = header.getLastCellNum();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                String key = header.getCell(j).getStringCellValue().trim();
                Cell cell = row.getCell(j);
                String value = "";
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    value = cell.getStringCellValue().trim();
                }
                map.put(key, value);
            }
            dataList.add(map);
        }
        workbook.close();
        fis.close();
    }

    public List<Map<String, String>> getDataByKeyword(String keyword) {
        List<Map<String, String>> filtered = new ArrayList<>();
        for (Map<String, String> row : dataList) {
            if (row.get("Keyword") != null && row.get("Keyword").equalsIgnoreCase(keyword)) {
                filtered.add(row);
            }
        }
        return filtered;
    }
}
