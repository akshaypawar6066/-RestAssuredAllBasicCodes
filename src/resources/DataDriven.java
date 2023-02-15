package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	// Identify TestCases column by scanning entire 1st row
	// Once TestCases column is identified then scan entire TestCases column to
	// identify purchase test case
	// After you grab purchase test case row pull

	public ArrayList<String> getData(String testCaseName) throws IOException {
		ArrayList<String> a = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("C:\\Users\\aksha\\OneDrive\\Documents\\DemoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // Accept file input stream argument
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("TestData")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				// Identify TestCases column by scanning entire 1st row
				Iterator<Row> rows = sheet.iterator(); // Collection of rows
				Row firstRow = rows.next();
				Iterator<Cell> ce = firstRow.iterator();
				int k = 0;
				int column = 0;// Row is a collection of cells.

				while (ce.hasNext()) {
					Cell cell = ce.next();
					if (cell.getStringCellValue().equalsIgnoreCase("TestCases")) {

						// Desired column
						column = k;
					}

					k++;
				}
				System.out.println(column);

				// Once TestCases column is identified then scan entire TestCases column to
				// identify purchase test case
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {

							Cell cellValues = cv.next();

							a.add(cellValues.getStringCellValue());
						}

						// To check type of Excel cell is it numeric or String

						/*
						 * if (cell.getCellType() == CellType.NUMERIC) {
						 * System.out.println("The cell is a numeric value"); else if
						 * (cell.getCellType() == CellType.STRING) {
						 * System.out.println("The cell is a string value");
						 */

						// To convert number into the string in the Excel cell--->
						// numberToStringConverter(cellValues.getNumericCellValue())

					}

				}

			}
		}
		return a;
	}

}
