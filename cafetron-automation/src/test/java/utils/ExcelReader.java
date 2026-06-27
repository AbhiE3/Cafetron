package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ExcelReader {

    private ExcelReader() {
    }

    public static List<Map<String, String>> readSheet(Path filePath, String sheetName) {
        DataFormatter formatter = new DataFormatter();

        try (InputStream inputStream = Files.newInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return List.of();
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(formatter.formatCellValue(cell).trim());
            }

            List<Map<String, String>> rows = new ArrayList<>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                Map<String, String> values = new LinkedHashMap<>();
                for (int cellIndex = 0; cellIndex < headers.size(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    values.put(headers.get(cellIndex), formatter.formatCellValue(cell).trim());
                }
                rows.add(values);
            }
            return rows;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read Excel file: " + filePath, e);
        }
    }

    public static String getCellData(Path filePath, String sheetName, int rowIndex, int columnIndex) {
        DataFormatter formatter = new DataFormatter();

        try (InputStream inputStream = Files.newInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return "";
            }

            return formatter.formatCellValue(row.getCell(columnIndex)).trim();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read Excel file: " + filePath, e);
        }
    }
}

