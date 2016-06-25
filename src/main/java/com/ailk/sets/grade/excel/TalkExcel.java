package com.ailk.sets.grade.excel;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.excel.intf.ITalkExcel;
import com.ailk.sets.grade.excel.intf.Position;
import com.ailk.sets.grade.intf.LoadConst;
import com.ailk.sets.grade.intf.LoadTalksResponse;
import com.ailk.sets.grade.intf.TalkResult;

@Service
public class TalkExcel implements ITalkExcel {

	private static final Logger logger = Logger.getLogger(TalkExcel.class);

	public static final String[] COLUMN_NAMES = { "学校", "城市", "宣讲具体地点、教室等",
			"预计座位数", "宣讲日期", "宣讲预计开始时间", "预计结束时间", "手机信号强度" };
	public static final int COLUMN_SCHOOL = 0;
	public static final int COLUMN_CITY = 1;
	public static final int COLUMN_ADDRESS = 2;
	public static final int COLUMN_SEATS = 3;
	public static final int COLUMN_DATE = 4;
	public static final int COLUMN_BEGIN_TIME = 5;
	public static final int COLUMN_END_TIME = 6;
	public static final int COLUMN_SIGNAL_STRENGTH = 7;

	@Override
	public LoadTalksResponse loadTalks(InputStream in, boolean isXSSF)
			throws Exception {
		LoadTalksResponse response = new LoadTalksResponse();
		List<TalkResult> results = new ArrayList<TalkResult>();
		Workbook workBook;
		if (isXSSF)
			workBook = new XSSFWorkbook(in);
		else
			workBook = new HSSFWorkbook(in);

		List<Integer> columnIndexes = new ArrayList<Integer>();
		Sheet sheet = workBook.getSheetAt(0);
		Row head = sheet.getRow(0);
		for (int i = head.getFirstCellNum(); i < head.getLastCellNum(); i++) {
			boolean found = false;
			String columnName = head.getCell(i).getStringCellValue();

			for (int j = 0; j < COLUMN_NAMES.length; j++) {
				String prefix = COLUMN_NAMES[j];

				if (columnName.startsWith(prefix)) {
					columnIndexes.add(j);
					found = true;
					break;
				}
			}

			if (!found)
				columnIndexes.add(-1);
		}

		// 获取表格的合并字段映射
		Map<Position, Position> positionMap = getPositionMap(sheet);

		// 第一行为标题，去掉
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			List<ExcelColumn> columns = getColumns(columnIndexes, positionMap,
					sheet, i);

			TalkResult result = handleRow(i, columns);
			if (result != null) {
				results.add(result);
				if (result.getErrorType() != LoadConst.TYPE_SUCCESS)
					response.setSuccess(false);
			}
		}

		if (!results.isEmpty())
			response.setResults(results);

		return response;
	}

	private List<ExcelColumn> getColumns(List<Integer> columnIndexes,
			Map<Position, Position> positionMap, Sheet sheet, int rowIndex)
			throws Exception {
		List<ExcelColumn> columns = new ArrayList<ExcelColumn>();

		// 设置列值
		for (int i = 0; i < columnIndexes.size(); i++) {
			int columnIndex = columnIndexes.get(i);
			Position key = new Position();
			key.setRow(rowIndex);
			key.setColumn(i);
			Position position = positionMap.get(key);
			if (position == null)
				position = key;

			Row row = sheet.getRow(position.getRow());
			Cell cell = row.getCell(position.getColumn());
			if (cell == null)
				continue;

			String value;
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = Boolean.toString(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				switch (columnIndex) {
				case COLUMN_DATE:
					if (cell.getDateCellValue() == null) {
						throw new FormatException("【"
								+ COLUMN_NAMES[columnIndex] + "】不能为空");
					}

					try {
						Date date = cell.getDateCellValue();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						value = format.format(date);
					} catch (Exception e) {
						throw new FormatException("【"
								+ COLUMN_NAMES[columnIndex]
								+ "】格式不正确，正确格式：yyyy-MM-dd");
					}
					break;
				case COLUMN_BEGIN_TIME:
				case COLUMN_END_TIME:
					if (cell.getDateCellValue() == null) {
						throw new FormatException("【"
								+ COLUMN_NAMES[columnIndex] + "】不能为空");
					}

					try {
						Date date = cell.getDateCellValue();
						SimpleDateFormat format = new SimpleDateFormat("HH:mm");
						value = format.format(date);
					} catch (Exception e) {
						throw new FormatException("【"
								+ COLUMN_NAMES[columnIndex]
								+ "】格式不正确，正确格式：HH:mm");
					}
					break;
				default:
					if ((double) (int) cell.getNumericCellValue() == cell
							.getNumericCellValue()) {
						value = Integer.toString((int) cell
								.getNumericCellValue());
					} else {
						value = Double.toString(cell.getNumericCellValue());
					}
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				value = null;
				break;
			default:
				continue;
			}

			ExcelColumn column = new ExcelColumn();
			column.setIndex(columnIndex);
			column.setValue(value);
			columns.add(column);
		}

		return columns;
	}

	private Map<Position, Position> getPositionMap(Sheet sheet) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		Map<Position, Position> positionMap = new HashMap<Position, Position>();
		positionMap.clear();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			Position value = new Position();
			value.setRow(firstRow);
			value.setColumn(firstColumn);

			for (int j = firstRow; j <= lastRow; j++) {
				for (int k = firstColumn; k <= lastColumn; k++) {
					Position key = new Position();
					key.setRow(j);
					key.setColumn(k);
					positionMap.put(key, value);
				}
			}
		}

		return positionMap;
	}

	private TalkResult handleRow(int rowNum, List<ExcelColumn> columns) {
		// 检查该行是否有数据
		boolean hasValues = false;
		for (ExcelColumn column : columns) {
			if (column.getValue() != null && !column.getValue().isEmpty()) {
				hasValues = true;
				break;
			}
		}
		if (!hasValues)
			return null;

		TalkResult result = new TalkResult();
		result.setRowNum(rowNum);

		try {
			// 设置列值
			for (ExcelColumn column : columns) {
				switch (column.getIndex()) {
				case COLUMN_SCHOOL:
					result.setSchool(column.getValue());
					break;
				case COLUMN_CITY:
					result.setCity(column.getValue());
					break;
				case COLUMN_ADDRESS:
					result.setAddress(column.getValue());
					break;
				case COLUMN_SEATS:
					try {
						if (column.getValue() != null)
							result.setSeats(Integer.parseInt(column.getValue()));
					} catch (NumberFormatException e) {
						throw new FormatException("【"
								+ COLUMN_NAMES[COLUMN_SEATS] + "】格式不正确");
					}
					break;
				case COLUMN_DATE:
					result.setDate(column.getValue());
					break;
				case COLUMN_BEGIN_TIME:
					result.setBeginTime(column.getValue());
					break;
				case COLUMN_END_TIME:
					result.setEndTime(column.getValue());
					break;
				case COLUMN_SIGNAL_STRENGTH:
					if (column.getValue() != null
							&& !column.getValue().isEmpty()) {
						try {
							result.setSignalStrength(Integer.parseInt(column
									.getValue()));
						} catch (NumberFormatException e) {
							throw new FormatException("【"
									+ COLUMN_NAMES[COLUMN_SIGNAL_STRENGTH]
									+ "】格式不正确");
						}
					}
					break;
				}
			}

			int count = 0;
			StringBuilder builder = new StringBuilder();

			// 检查必填项
			if (result.getSchool() == null || result.getSchool().isEmpty()) {
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_SCHOOL] + "】");
				count++;
			}

			if (result.getCity() == null || result.getCity().isEmpty()) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_CITY] + "】");
				count++;
			}

			if (result.getAddress() == null || result.getAddress().isEmpty()) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_ADDRESS] + "】");
				count++;
			}

			if (result.getSeats() == null || result.getSeats() <= 0) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_SEATS] + "】");
				count++;
			}

			if (result.getDate() == null || result.getDate().isEmpty()) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_DATE] + "】");
				count++;
			}

			if (result.getBeginTime() == null
					|| result.getBeginTime().isEmpty()) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_BEGIN_TIME] + "】");
				count++;
			}

			if (result.getEndTime() == null || result.getEndTime().isEmpty()) {
				if (builder.length() > 0)
					builder.append("，");
				builder.append("缺少【" + COLUMN_NAMES[COLUMN_END_TIME] + "】");
				count++;
			}

			if (count > 0) {
				result.setErrorType(LoadConst.TYPE_FORMAT);
				result.setCause(builder.toString());
				logger.debug(result.getCause());
			} else {
				if (result.getEndTime().compareTo(result.getBeginTime()) <= 0) {
					throw new FormatException("【"
							+ COLUMN_NAMES[COLUMN_END_TIME] + "】必须在【"
							+ COLUMN_NAMES[COLUMN_BEGIN_TIME] + "】之后");
				}

				result.setErrorType(LoadConst.TYPE_SUCCESS);
			}
		} catch (FormatException e) {
			result.setErrorType(LoadConst.TYPE_FORMAT);
			result.setCause(e.getErrDesc());
			logger.debug(result.getCause());
		}

		return result;
	}

}
