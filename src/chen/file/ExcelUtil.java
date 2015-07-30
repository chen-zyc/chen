package chen.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private String			fileName;
	private FileInputStream	fis;
	private Workbook	wb;
	private Sheet		sheet;

	public ExcelUtil(String fileName) {
		this.fileName = fileName;
	}

	public void init() throws IOException {
		fis = new FileInputStream(fileName);
		if (fileName.toLowerCase().endsWith(".xls")) {
			wb = new HSSFWorkbook(fis);
		} else if (fileName.toLowerCase().endsWith(".xlsx")) {
			wb = new XSSFWorkbook(fis);
		}
	}

	public void close() throws IOException {
		if (fis != null) {
			fis.close();
		}
	}

	public int sheetCount() {
		return wb.getNumberOfSheets();
	}

	public boolean useSheetAt(int n) {
		sheet = wb.getSheetAt(n);
		return sheet != null;
	}

	public int rowCount() {
		return sheet.getLastRowNum();
	}

	public List<String> getRow(int n) {
		Row row = sheet.getRow(n);
		if (row == null) {
			return null;
		}
		int cells = row.getLastCellNum();
		if (cells <= 0) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>(cells);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int j = 0; j < cells; j++) {
			Cell cell = row.getCell(j);
			if (cell == null) {
				list.add("");
				continue;
			}
			String value = null;

			switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date d = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
						value = sdf.format(d);
					} else {
						double v = cell.getNumericCellValue();
						value = new BigDecimal(v).toPlainString();
					}
					break;

				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;

				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue() + "";
					break;

				default:
					value = cell.getStringCellValue();
			}
			list.add(value);
		}
		return list;
	}

}
