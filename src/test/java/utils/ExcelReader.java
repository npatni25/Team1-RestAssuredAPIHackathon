package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {

    // Read from config.properties once and reuse
    //private static final String filePath = ConfigReader.getProperty("Excel_Path");
	
	private static final String filePath ="/src/test/resources/TestData/ExcelTestData.xlsx";
	
	public static List<Map<String, String>> readDieticianData(String sheetName) {
	    List<Map<String, String>> data = new ArrayList<>();
	    
	    File file = new File(System.getProperty("user.dir") + filePath);
	    if (!file.exists()) {
	        throw new RuntimeException("Excel file not found at path: " + file.getAbsolutePath());
	    }
	    
	    try (FileInputStream fis = new FileInputStream(file);
	         Workbook workbook = WorkbookFactory.create(fis)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        Row headerRow = sheet.getRow(0);
	        int colCount = headerRow.getLastCellNum();

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Map<String, String> rowData = new HashMap<>();
	            Row currentRow = sheet.getRow(i);

	            for (int j = 0; j < colCount; j++) {
	                Cell header = headerRow.getCell(j);
	                Cell cell = currentRow.getCell(j);

	                String key = header != null ? header.toString().trim() : "UNKNOWN";

	                // Safely convert cell value to String regardless of cell type
	                String value = "";
	                if (cell != null) {
	                    switch (cell.getCellType()) {
	                        case STRING:
	                            value = cell.getStringCellValue().trim();
	                            break;
	                        case NUMERIC:
	                            if (DateUtil.isCellDateFormatted(cell)) {
	                                value = cell.getLocalDateTimeCellValue().toString(); // ISO format
	                            } else {
	                                value = String.valueOf(cell.getNumericCellValue());
	                            }
	                            break;
	                        case BOOLEAN:
	                            value = String.valueOf(cell.getBooleanCellValue());
	                            break;
	                        case FORMULA:
	                            // Evaluate formula result as string
	                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	                            CellValue cellValue = evaluator.evaluate(cell);
	                            switch (cellValue.getCellType()) {
	                                case STRING:
	                                    value = cellValue.getStringValue().trim();
	                                    break;
	                                case NUMERIC:
	                                    value = String.valueOf(cellValue.getNumberValue());
	                                    break;
	                                case BOOLEAN:
	                                    value = String.valueOf(cellValue.getBooleanValue());
	                                    break;
	                                default:
	                                    value = "";
	                            }
	                            break;
	                        case BLANK:
	                        case _NONE:
	                        case ERROR:
	                        default:
	                            value = "";
	                    }
	                }
	                
	                rowData.put(key, value);
	            }
	            data.add(rowData);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return data;
	}



    public static Map<String, String> getTestDataByTestCaseId(String sheetName, String testCaseId) {
        List<Map<String, String>> allData = readDieticianData(sheetName);
        System.out.println("Looking for testCaseId: '" + testCaseId + "' in sheet: " + sheetName);
        for (Map<String, String> row : allData) {
            if (row.get("TestCaseID").trim().equalsIgnoreCase(testCaseId)) {
                return row;
            }
        }
        throw new RuntimeException("Test case ID not found: " + testCaseId);
    }
}
