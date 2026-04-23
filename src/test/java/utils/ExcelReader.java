package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {

    // ✅ Get cell data (safe + formatted)
    public static String getCellData(String filePath, int rowNum, int colNum) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);

            if (row == null) return "";

            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // ✅ Get total number of rows (important for loop)
    public static int getRowCount(String filePath) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            return sheet.getLastRowNum();  // last row index

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ✅ Get total number of columns
    public static int getColumnCount(String filePath, int rowNum) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);

            if (row != null) {
                return row.getLastCellNum();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ✅ Get entire row data (VERY USEFUL)
    public static String[] getRowData(String filePath, int rowNum) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);

            if (row == null) return new String[0];

            int colCount = row.getLastCellNum();
            String[] data = new String[colCount];

            DataFormatter formatter = new DataFormatter();

            for (int i = 0; i < colCount; i++) {
                Cell cell = row.getCell(i);
                data[i] = (cell == null) ? "" : formatter.formatCellValue(cell);
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[0];
    }

    // ✅ Get sheet by name (flexible)
    public static Sheet getSheet(String filePath, String sheetName) {

        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);

            return workbook.getSheet(sheetName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}