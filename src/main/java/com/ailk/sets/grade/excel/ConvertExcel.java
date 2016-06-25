package com.ailk.sets.grade.excel;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IQbBaseFileDao;
import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.dao.intf.IQbUploadErrorDao;
import com.ailk.sets.grade.excel.intf.ExcelRow;
import com.ailk.sets.grade.excel.intf.IConvertExcel;
import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.excel.intf.Position;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.LoadConst;
import com.ailk.sets.grade.intf.LoadGroup;
import com.ailk.sets.grade.intf.LoadGroupKey;
import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.grade.intf.LoadRow;
import com.ailk.sets.grade.intf.LoadRowResponse;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.jdbc.QbUploadError;
import com.ailk.sets.grade.security.DESCoder;
import com.ailk.sets.grade.utils.Document;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.grade.utils.QuestionUtils.FileInfo;
import com.ailk.sets.grade.utils.Similarity;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.QbQuestionSkillId;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class ConvertExcel implements IConvertExcel {

	private static final Logger logger = Logger.getLogger(ConvertExcel.class);

	private static final Gson gson = new Gson();

	private static final String[] SHEET_NAMES = { "客观题", "主观题", "附加题",
			"技能类（选择题）", "编程题", "技能类（问答题）", "智力类（选择题）", "智力类（问答题）", "面试题" };
	private static final int[] SHEET_BEGIN_IDS = { 0, 80000, 90000, -1, -1, -1,
			-1, -1, -1 };

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IQbBaseFileDao qbBaseFileDao;

	@Autowired
	private IQbUploadErrorDao qbUploadErrorDao;

	@Autowired
	private IQbBaseDao qbBaseDao;

	@Value("${grade.encrypted}")
	private boolean encrypted;

	@Override
	public LoadResponse loadFile(InputStream in, Long fileBeginId,
			int createBy, Integer qbId, double similarityLimit,
			boolean checkTime, int testType, boolean isXSSF) throws Exception {
		LoadResponse response = new LoadResponse();

		// 设置初始值
		Holder holder = new Holder();
		holder.setSimilarityLimit(similarityLimit);
		holder.setCheckTime(checkTime);
		holder.setFileBeginId(fileBeginId);
		holder.setCreateBy(createBy);

		// 设置题库
		if (qbId == null) {
			holder.setQbId(qbBaseFileDao.get((int) (fileBeginId.longValue()
					/ GradeConst.MAX_FILE_ROWS / 100)));
		} else {
			holder.setQbId(qbId);

			// 删除历史的错误数据
			qbUploadErrorDao.deleteByQbId(qbId);
		}

		// 查找题库
		QbBase qbBase = qbBaseDao.getEntity(holder.getQbId());
		if (qbBase == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到自定义题库，qbId=" + qbId);
			return response;
		}

		if (createBy != GradeConst.CREATE_BY_SYS && qbBase.getPrebuilt() == 1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("预定义题库不允许导入，qbId=" + qbId);
			return response;
		}

		if (qbBase.getCreateBy() != createBy) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("本题库不属于创建者，createBy= " + createBy + ", qbId="
					+ qbId);
			return response;
		}

		Workbook workBook;
		if (isXSSF)
			workBook = new XSSFWorkbook(in);
		else
			workBook = new HSSFWorkbook(in);

		boolean gottenQuestions = false;
		for (int sheetId = 0; sheetId < workBook.getNumberOfSheets(); sheetId++) {
			String sheetName = workBook.getSheetName(sheetId);
			Sheet sheet = workBook.getSheetAt(sheetId);
			int sheetType;

			// 从后往前找，后面的sheet名较长
			for (sheetType = SHEET_NAMES.length - 1; sheetType >= 0; sheetType--) {
				if (sheetName.startsWith(SHEET_NAMES[sheetType]))
					break;
			}

			if (testType == GradeConst.TEST_TYPE_CAMPUS
					&& sheetType != LoadConst.SHEET_TYPE_TECH_CHOICE 
					&& sheetType != LoadConst.SHEET_TYPE_TECH_ESSAY 
					&& sheetType != LoadConst.SHEET_TYPE_INTEL_CHOICE 
					&& sheetType != LoadConst.SHEET_TYPE_INTEL_ESSAY) {
				response.setErrorCode(BaseResponse.EMATCH);
				response.setErrorDesc("校招试卷只能导入技能类选择题，技能类问答题，智力类选择题，智力类问答题");
				return response;
			}

			if (sheetType < 0)
				continue;

			switch (qbBase.getCategory()) {
			case GradeConst.CATEGORY_TECHNOLOGY:
				if (sheetType != LoadConst.SHEET_TYPE_TECH_CHOICE
						&& sheetType != LoadConst.SHEET_TYPE_PROGRAM
						&& sheetType != LoadConst.SHEET_TYPE_TECH_ESSAY) {
					response.setErrorCode(BaseResponse.EMATCH);
					response.setErrorDesc("技术题库模板中不能有非技术类的表格");
				}
				break;
			case GradeConst.CATEGORY_INTELLIGENCE:
				if (sheetType != LoadConst.SHEET_TYPE_INTEL_CHOICE
						&& sheetType != LoadConst.SHEET_TYPE_INTEL_ESSAY) {
					response.setErrorCode(BaseResponse.EMATCH);
					response.setErrorDesc("智力题库模板中不能有非智力类的表格");
				}
				break;
			case GradeConst.CATEGORY_INTERVIEW:
				if (sheetType != LoadConst.SHEET_TYPE_INTERVIEW) {
					response.setErrorCode(BaseResponse.EMATCH);
					response.setErrorDesc("面试题库模板中不能有非面试类的表格");
				}
				break;
			}

			// 设置默认值
			if (fileBeginId != null) {
				holder.setSheetBeginId(fileBeginId + SHEET_BEGIN_IDS[sheetType]);
			}
			holder.getColumnIndexes().clear();
			holder.getIdSet().clear();

			// 获取标题对应的索引
			Row head = sheet.getRow(0);
			for (int i = head.getFirstCellNum(); i < head.getLastCellNum(); i++) {
				boolean found = false;
				String columnName = head.getCell(i).getStringCellValue();

				for (int j = 0; j < ExcelConf.COLUMN_NAMES.length; j++) {
					String prefix = ExcelConf.COLUMN_NAMES[j];

					if (columnName.startsWith(prefix)) {
						holder.getColumnIndexes().add(j);
						found = true;
						break;
					}
				}

				if (!found)
					holder.getColumnIndexes().add(-1);
			}

			// 加载相关的试题
			switch (sheetType) {
			case LoadConst.SHEET_TYPE_TECH_CHOICE:
			case LoadConst.SHEET_TYPE_PROGRAM:
			case LoadConst.SHEET_TYPE_TECH_ESSAY:
			case LoadConst.SHEET_TYPE_INTEL_CHOICE:
			case LoadConst.SHEET_TYPE_INTEL_ESSAY:
				if (!gottenQuestions) {
					gottenQuestions = true;
					if (similarityLimit > 0.0)
						loadQbQuestions(holder, null, null);
				}
				holder.setPrebuilt(false);
				break;
			case LoadConst.SHEET_TYPE_INTERVIEW:
				holder.setPrebuilt(false);
				break;
			default:
				holder.setPrebuilt(true);
				break;
			}

			// 获取表格的合并字段映射
			getPositionMap(holder, sheet);

			// 第一行为标题，去掉
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				List<ExcelColumn> columns = getColumns(holder, sheet, i);

				handleRow(holder, sheetType, null, i, 0, columns);
			}
		}

		// 更新题库修改时间
		qbBase.setModifyDate(new Timestamp(System.currentTimeMillis()));
		qbBaseDao.update(qbBase);

		saveQuestions(holder);
		saveGroup(holder, 0);

		response = holder.getResponse();
		if (response.getGroups().isEmpty())
			response.setGroups(null);
		return response;
	}

	@Override
	public LoadResponse loadQuestions(int createBy, int qbId,
			Set<Long> skipQids, double similarityLimit, boolean checkTime,
			LoadRequest request) throws Exception {
		LoadResponse response = new LoadResponse();

		// 查找题库
		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到自定义题库，qbId=" + qbId);
			return response;
		}

		if (qbBase.getPrebuilt() == 1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("预定义题库不允许导出，qbId=" + qbId);
			return response;
		}

		if (qbBase.getCreateBy() != createBy) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("本题库不属于创建者，createBy= " + createBy + ", qbId="
					+ qbId);
			return response;
		}

		if (request.getRows() == null || request.getRows().isEmpty()) {
			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("缺少题目数据");
			return response;
		}

		Holder holder = new Holder();
		holder.setSimilarityLimit(similarityLimit);
		holder.setCheckTime(checkTime);
		holder.setFileBeginId(null);
		holder.setCreateBy(createBy);
		holder.setQbId(qbId);
		holder.setPrebuilt(false);

		if (similarityLimit > 0.0) {
			String condition = null;
			switch (request.getSheetType()) {
			case LoadConst.SHEET_TYPE_TECH_CHOICE:
				condition = "questionType IN ('"
						+ GradeConst.QUESTION_TYPE_NAME_S_CHOICE + "', '"
						+ GradeConst.QUESTION_TYPE_NAME_M_CHOICE + "')";
				break;
			case LoadConst.SHEET_TYPE_PROGRAM:
				condition = "questionType = '"
						+ GradeConst.QUESTION_TYPE_NAME_EXTRA_PROGRAM + "'";
				break;
			case LoadConst.SHEET_TYPE_TECH_ESSAY:
			case LoadConst.SHEET_TYPE_INTEL_ESSAY:
				condition = "questionType = '"
						+ GradeConst.QUESTION_TYPE_NAME_ESSAY + "'";
				break;
			case LoadConst.SHEET_TYPE_INTEL_CHOICE:
				condition = "questionType IN ('"
						+ GradeConst.QUESTION_TYPE_NAME_S_CHOICE + "', '"
						+ GradeConst.QUESTION_TYPE_NAME_M_CHOICE + "', '"
						+ GradeConst.QUESTION_TYPE_NAME_S_CHOICE_PLUS + "', '"
						+ GradeConst.QUESTION_TYPE_NAME_M_CHOICE_PLUS + "')";
				break;
			case LoadConst.SHEET_TYPE_INTERVIEW:
				break;
			}

			if (request.getSheetType() != LoadConst.SHEET_TYPE_INTERVIEW)
				loadQbQuestions(holder, condition, skipQids);
		}

		for (LoadRow row : request.getRows()) {
			List<ExcelColumn> columns = getLoadColumns(row);
			handleRow(holder, request.getSheetType(), request.getGroup(), 0,
					row.getSerialNo() == null ? 0 : row.getSerialNo(), columns);
		}

		// 更新题库修改时间
		qbBase.setModifyDate(new Timestamp(System.currentTimeMillis()));
		qbBaseDao.update(qbBase);

		saveQuestions(holder);
		saveGroup(holder,
				request.getSerialNo() == null ? 0 : request.getSerialNo());

		response = holder.getResponse();
		if (response.getGroups().isEmpty())
			response.setGroups(null);
		return response;
	}

	/**
	 * 获取建议时长
	 * 
	 * @param conf
	 * @return
	 */
	@Override
	public int getSuggestTime(String title, List<String> options) {
		List<String> list = new ArrayList<String>();
		list.add(title);

		if (options != null)
			list.addAll(options);

		int words = 0;
		for (String str : list) {
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if (ch < 256)
					words++;
				else
					words += 2;
			}
		}

		int suggestTime = (int) (0.13 * (words - 12) + 14);
		if (suggestTime > 90)
			suggestTime = 90;

		return suggestTime;
	}

	/**
	 * 获取Excel中的一行
	 * 
	 * @param holder
	 * @param sheet
	 *            表格
	 * @param rowIndex
	 *            行ID
	 * @return
	 */
	private List<ExcelColumn> getColumns(Holder holder, Sheet sheet,
			int rowIndex) {
		List<ExcelColumn> columns = new ArrayList<ExcelColumn>();
		Map<Position, Position> positionMap = holder.getPositionMap();

		// 设置列值
		List<Integer> columnIndexes = holder.getColumnIndexes();
		for (int i = 0; i < columnIndexes.size(); i++) {
			Position key = new Position();
			key.setRow(rowIndex);
			key.setColumn(i);
			Position position = positionMap.get(key);
			if (position == null)
				position = key;

			String value = null;
			do {
				Row row = sheet.getRow(position.getRow());
				if (row == null)
					break;

				Cell cell = row.getCell(position.getColumn());
				if (cell == null)
					break;

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue().trim();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					value = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if ((double) (int) cell.getNumericCellValue() == cell
							.getNumericCellValue()) {
						value = Integer.toString((int) cell
								.getNumericCellValue());
					} else {
						value = Double.toString(cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BLANK:
				default:
					break;
				}
			} while (false);

			ExcelColumn column = new ExcelColumn();
			column.setIndex(columnIndexes.get(i));
			column.setValue(value);
			columns.add(column);
		}

		return columns;
	}

	/**
	 * 加载用户下的所有题，用于相似度判断
	 * 
	 * @param holder
	 */
	private void loadQbQuestions(Holder holder, String condition,
			Set<Long> skipQids) {
		List<QbQuestion> qbQuestions = qbQuestionDao.getListByCreator(
				holder.getCreateBy(), condition);

		if (qbQuestions == null)
			return;

		for (QbQuestion qbQuestion : qbQuestions) {
			if (skipQids != null
					&& skipQids.contains(qbQuestion.getQuestionId()))
				continue;

			QbBase qbBase = qbBaseDao.getEntity(qbQuestion.getQbId());
			if (qbBase == null
					|| qbBase.getCategory() == GradeConst.CATEGORY_PAPER)
				continue;

			createDocument(holder, qbQuestion);
		}
	}

	/**
	 * 加载表格中的合并网格映射
	 * 
	 * @param holder
	 * @param sheet
	 *            表格
	 */
	private void getPositionMap(Holder holder, Sheet sheet) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		Map<Position, Position> positionMap = holder.getPositionMap();
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
	}

	/**
	 * 处理一行
	 * 
	 * @param holder
	 * @param sheetType
	 *            表格类型
	 * @param group
	 *            题组
	 * @param rowNum
	 *            行号
	 * @param serialNo
	 *            错误序列号
	 * @param columns
	 *            列数据
	 * @throws Exception
	 */
	private void handleRow(Holder holder, int sheetType, String group,
			int rowNum, int serialNo, List<ExcelColumn> columns)
			throws Exception {
		ExcelConf conf = new ExcelConf();
		conf.setGroup(group);

		ExcelRow row = new ExcelRow();
		row.setRowNum(rowNum);
		row.setSheetType(sheetType);
		row.setSerialNo(serialNo);
		row.setColumns(columns);
		row.setConf(conf);

		try {
			// 检查该行是否有数据
			boolean hasValues = false;
			for (ExcelColumn column : columns) {
				if (column.getValue() != null && !column.getValue().isEmpty()) {
					hasValues = true;
					break;
				}
			}
			if (!hasValues)
				throw new IgnoredException();

			boolean hasNullOption = false;
			boolean nullOptionSpecified = false;
			int optionIndex = 0;
			int maxOption = -1;
			List<String> options = new ArrayList<String>();
			conf.setOptions(options);

			List<String> tags = new ArrayList<String>();
			conf.setTags(tags);

			// 设置默认值
			conf.setStatus(1);
			conf.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
			conf.setSamples(1);

			switch (sheetType) {
			case LoadConst.SHEET_TYPE_INTERNAL_CHOICE: // 内部选择题
				conf.setAnswer(1); // 选择题默认一个选项
				conf.setType(GradeConst.QUESTION_TYPE_NAME_S_CHOICE);
				break;
			case LoadConst.SHEET_TYPE_INTERNAL_PROGRAM: // 内部编程题
				conf.setType(GradeConst.QUESTION_TYPE_NAME_PROGRAM);
				break;
			case LoadConst.SHEET_TYPE_INTERNAL_EXTRA_PROGRAM: // 内部附加编程题
				conf.setType(GradeConst.QUESTION_TYPE_NAME_EXTRA_PROGRAM);
				conf.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
				break;
			case LoadConst.SHEET_TYPE_TECH_CHOICE: // 技能类（选择题）
				conf.setId(-1);
				conf.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
				break;
			case LoadConst.SHEET_TYPE_PROGRAM: // 编程题
				conf.setId(-1);
				conf.setType(GradeConst.QUESTION_TYPE_NAME_EXTRA_PROGRAM);
				conf.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
				break;
			case LoadConst.SHEET_TYPE_TECH_ESSAY: // 技能类（问答题）
				conf.setId(-1);
				conf.setType(GradeConst.QUESTION_TYPE_NAME_ESSAY);
				conf.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
				break;
			case LoadConst.SHEET_TYPE_INTEL_CHOICE: // 智力类（选择题）
				conf.setId(-1);
				conf.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
				break;
			case LoadConst.SHEET_TYPE_INTEL_ESSAY: // 智力类（问答题）
				conf.setId(-1);
				conf.setType(GradeConst.QUESTION_TYPE_NAME_ESSAY);
				conf.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
				break;
			case LoadConst.SHEET_TYPE_INTERVIEW: // 面试题
				conf.setId(-1);
				conf.setType(GradeConst.QUESTION_TYPE_NAME_VIDEO);
				conf.setCategory(GradeConst.CATEGORY_INTERVIEW);
				break;
			}

			// 设置列值
			StringBuilder formatErrorBuilder = new StringBuilder();
			for (ExcelColumn column : columns) {
				switch (column.getIndex()) {
				case ExcelConf.COLUMN_ID:
					conf.setId(Integer.parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_MODE:
				case ExcelConf.COLUMN_TEMPLATE_MODE:
					conf.setMode(GradeConst.standardMode(column.getValue()));
					break;
				case ExcelConf.COLUMN_HTML:
					if ("1".equals(column.getValue()))
						conf.setHtml(true);
					else
						conf.setHtml(false);
					break;
				case ExcelConf.COLUMN_TYPE:
					conf.setType(column.getValue());
					break;
				case ExcelConf.COLUMN_TITLE:
				case ExcelConf.COLUMN_TEMPLATE_TITLE:
					conf.setTitle(trim(column.getValue()));
					break;
				case ExcelConf.COLUMN_SUMMARY:
					if (column.getValue() != null
							&& !column.getValue().isEmpty())
						conf.setSummary(column.getValue());
					break;
				case ExcelConf.COLUMN_CODE:
				case ExcelConf.COLUMN_TEMPLATE_REF_ANSWER:
				case ExcelConf.COLUMN_TEMPLATE_REF_EXPLAIN:
					conf.setCode(column.getValue());
					break;
				case ExcelConf.COLUMN_OPTION:
				case ExcelConf.COLUMN_TEMPLATE_OPTION: {
					String option = column.getValue();
					if (option != null && !option.isEmpty()) {
						if (hasNullOption && !nullOptionSpecified) {
							if (formatErrorBuilder.length() > 0)
								formatErrorBuilder.append("，");
							formatErrorBuilder.append("【选项】必须按顺序填写，空的选项放到最后");
							nullOptionSpecified = true;
						}
						options.add(trim(option));
						maxOption = optionIndex;
					} else {
						hasNullOption = true;
					}
					optionIndex++;
					break;
				}
				case ExcelConf.COLUMN_LEVEL:
					conf.setLevel(parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_PARAM: {
					if (column.getValue() != null) {
						String param = column.getValue().trim();
						if (!param.isEmpty())
							conf.setParam(param);
					}
					break;
				}
				case ExcelConf.COLUMN_DATA:
					conf.setData(column.getValue());
					break;
				case ExcelConf.COLUMN_TAG:
				case ExcelConf.COLUMN_TEMPLATE_SKILL:
					if (column.getValue() != null
							&& !column.getValue().isEmpty())
						tags.add(column.getValue());
					break;
				case ExcelConf.COLUMN_ANSWER:
					conf.setAnswer(parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_INCLUDE:
					conf.setInclude(column.getValue());
					break;
				case ExcelConf.COLUMN_EXCLUDE:
					conf.setExclude(column.getValue());
					break;
				case ExcelConf.COLUMN_SAMPLE:
					conf.setSample(parseInt(column.getValue()) == 1);
					break;
				case ExcelConf.COLUMN_STATUS:
					conf.setStatus(Integer.parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_ANSWER_NUM:
					conf.setAnswerNum(parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_FAIL_PERCENT: {
					String value = column.getValue();
					if (value == null || value.isEmpty()) {
						// 什么也不做
					} else if (value.endsWith("%")) {
						conf.setFailPercent(Integer.parseInt(value.substring(0,
								value.length() - 1)));
					} else {
						double percent = Double.parseDouble(value);
						if (percent < 1.0)
							percent *= 100;
						conf.setFailPercent((int) percent);
					}
					break;
				}
				case ExcelConf.COLUMN_SUGGEST_TIME:
				case ExcelConf.COLUMN_TEMPLATE_SUGGEST_SECONDS:
					if (column.getValue() != null) {
						try {
							conf.setSuggestTime(Integer.parseInt(column
									.getValue()));
						} catch (NumberFormatException e) {
							if (formatErrorBuilder.length() > 0)
								formatErrorBuilder.append("，");
							formatErrorBuilder.append("【答题时长】必须为整数");
						}
					}
					break;
				case ExcelConf.COLUMN_TEMPLATE_SUGGEST_MINUTES:
					if (column.getValue() != null) {
						try {
							conf.setSuggestTime(Integer.parseInt(column
									.getValue()) * 60);
						} catch (NumberFormatException e) {
							if (formatErrorBuilder.length() > 0)
								formatErrorBuilder.append("，");
							formatErrorBuilder.append("【答题时长】必须为整数");
						}
					}
					break;
				case ExcelConf.COLUMN_SCORE:
					conf.setScore(parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_CATEGORY:
					conf.setCategory(Integer.parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_SAMPLES:
					conf.setSamples(parseInt(column.getValue()));
					break;
				case ExcelConf.COLUMN_TEMPLATE_LEVEL:
					conf.setLevel(GradeConst.getLevel(column.getValue()));
					break;
				case ExcelConf.COLUMN_TEMPLATE_CORRECT_OPTIONS:
					if (column.getValue() != null)
						conf.setCorrectOptions(column.getValue().toUpperCase());
					break;
				case ExcelConf.COLUMN_TEMPLATE_EXPLAIN_REQUIRED: {
					String value = column.getValue();
					if (value == null || value.equals("否")) {
						conf.setExplainRequired(false);
					} else if (value.equals("是")) {
						conf.setExplainRequired(true);
					} else {
						if (formatErrorBuilder.length() > 0)
							formatErrorBuilder.append("，");
						formatErrorBuilder.append("【需要补充解释】的取值范围为：是、否");
					}
					break;
				}
				case ExcelConf.COLUMN_TEMPLATE_GROUP:
					conf.setGroup(column.getValue());
					break;
				}
			}

			// 检查必填项
			switch (sheetType) {
			case LoadConst.SHEET_TYPE_INTERNAL_CHOICE:
				if (options.size() < 2 && conf.getStatus() == 1) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("选择题选项至少为两个");
				}
				break;
			case LoadConst.SHEET_TYPE_TECH_CHOICE:
				if (options.size() < 2 && conf.getStatus() == 1) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("选择题选项至少为两个");
				}
				if (conf.getCorrectOptions() == null
						|| conf.getCorrectOptions().isEmpty()) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【正确选项】");
				}
				if (conf.getTags() == null || conf.getTags().isEmpty()) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【技能】");
				}
				if (conf.getLevel() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【难度】");
				}
				if (conf.getSuggestTime() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【作答时长】");
				}
				break;
			case LoadConst.SHEET_TYPE_INTEL_CHOICE:
				if (options.size() < 2 && conf.getStatus() == 1) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("选择题选项至少为两个");
				}
				if (conf.getCorrectOptions() == null
						|| conf.getCorrectOptions().isEmpty()) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【正确选项】");
				}
				if (conf.getSuggestTime() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【作答时长】");
				}
				if (conf.getLevel() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【难度】");
				}
				break;
			case LoadConst.SHEET_TYPE_PROGRAM:
				if (conf.getMode() == null || conf.getMode().isEmpty()) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【编程语言】");
				}
				if (conf.getSuggestTime() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【作答时长】");
				}
				if (conf.getLevel() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【难度】");
				}
				break;
			case LoadConst.SHEET_TYPE_TECH_ESSAY:
			case LoadConst.SHEET_TYPE_INTEL_ESSAY:
				if (conf.getSuggestTime() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【作答时长】");
				}
				if (conf.getLevel() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【难度】");
				}
				break;
			case LoadConst.SHEET_TYPE_INTERVIEW:
				if (conf.getGroup() == null || conf.getGroup().isEmpty()) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【面试题组】");
				}
				if (conf.getSuggestTime() == 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("缺少【作答时长】");
				}
				break;
			}

			// 如果没有标题，则这道题格式有问题，直接忽略
			if (conf.getTitle() == null || conf.getTitle().isEmpty()) {
				if (formatErrorBuilder.length() > 0)
					formatErrorBuilder.append("，");
				formatErrorBuilder.append("缺少【题干】");
			}

			if (conf.getStatus() == -1) {
				if (formatErrorBuilder.length() > 0)
					formatErrorBuilder.append("，");
				formatErrorBuilder.append("缺少【状态】");
			}

			// 检查特定的必填项
			if (conf.getId() == 0) {
				if (formatErrorBuilder.length() > 0)
					formatErrorBuilder.append("，");
				formatErrorBuilder.append("缺少【ID】");
			}
			if (holder.isPrebuilt() && conf.getMode() == null) {
				if (formatErrorBuilder.length() > 0)
					formatErrorBuilder.append("，");
				formatErrorBuilder.append("缺少【模式】");
			}
			if (holder.isPrebuilt() && conf.getTags().isEmpty()) {
				if (formatErrorBuilder.length() > 0)
					formatErrorBuilder.append("，");
				formatErrorBuilder.append("缺少【标签】");
			}

			// 如果提供了答案，则以答案为准
			if (conf.getCorrectOptions() != null
					&& !conf.getCorrectOptions().isEmpty()) {
				String correctOptions = conf.getCorrectOptions();
				StringBuilder errorBuilder = new StringBuilder();

				for (int i = 0; i < correctOptions.length(); i++) {
					char ch = correctOptions.charAt(i);
					if (ch < 'A' || ch > 'A' + maxOption)
						errorBuilder.append(ch);
				}

				if (errorBuilder.length() > 0) {
					if (formatErrorBuilder.length() > 0)
						formatErrorBuilder.append("，");
					formatErrorBuilder.append("【正确选项】中答案"
							+ errorBuilder.toString() + "为空");
				}
			}

			if (formatErrorBuilder.length() > 0)
				throw new FormatException(formatErrorBuilder.toString());

			// 设置类型默认值
			switch (sheetType) {
			case LoadConst.SHEET_TYPE_TECH_CHOICE:
				if (conf.getCorrectOptions().length() <= 1)
					conf.setType(GradeConst.QUESTION_TYPE_NAME_S_CHOICE);
				else
					conf.setType(GradeConst.QUESTION_TYPE_NAME_M_CHOICE);
				break;
			case LoadConst.SHEET_TYPE_INTEL_CHOICE:
				if (conf.getCorrectOptions().length() <= 1) {
					conf.setType(GradeConst.QUESTION_TYPE_NAME_S_CHOICE);
					if (conf.isExplainRequired())
						conf.setType(GradeConst.QUESTION_TYPE_NAME_S_CHOICE_PLUS);
				} else {
					conf.setType(GradeConst.QUESTION_TYPE_NAME_M_CHOICE);
					if (conf.isExplainRequired())
						conf.setType(GradeConst.QUESTION_TYPE_NAME_M_CHOICE_PLUS);
				}
				break;
			}

			// 设置难度默认值
			if (conf.getAnswerNum() > 0) {
				conf.setLevel((conf.getFailPercent() + 6 - 5) / 7);
				if (conf.getLevel() > 10)
					conf.setLevel(10);
			}
			if (conf.getLevel() == 0)
				conf.setLevel(5);

			// 如果提供了答案，则以答案为准
			if (conf.getCorrectOptions() != null
					&& !conf.getCorrectOptions().isEmpty()) {
				String correctOptions = conf.getCorrectOptions();

				// 重新调整次序
				List<String> options1 = new ArrayList<String>();
				List<String> options2 = new ArrayList<String>();
				for (int i = 0; i < options.size(); i++) {
					if (correctOptions.contains(Character
							.toString((char) ('A' + i))))
						options1.add(options.get(i));
					else
						options2.add(options.get(i));
				}
				options1.addAll(options2);

				options = options1;
				conf.setOptions(options);
				conf.setAnswer(correctOptions.length());
			} else if (conf.getAnswer() <= 0) {
				conf.setAnswer(1);
			}

			/*// 检查时间是否合理
			if (sheetType == LoadConst.SHEET_TYPE_TECH_CHOICE) {
				int suggestTime = getSuggestTime(conf.getTitle(),
						conf.getOptions());
				if (conf.getSuggestTime() == 0) {
					conf.setSuggestTime(suggestTime);
				} else if (holder.isCheckTime()) {
					if (conf.getSuggestTime() > suggestTime * 2) {
						throw new TimeException(suggestTime, "【作答时长】过长，建议"
								+ suggestTime + "秒");
					} else if (conf.getSuggestTime() < suggestTime / 2) {
						throw new TimeException(suggestTime, "【作答时长】过短，建议"
								+ suggestTime + "秒");
					}
				}
			}*/

			if (conf.getId() == -1) {
				// 生成ID
				conf.setId(qbQuestionDao.getNextQid());
			} else {
				// 设置ID
				conf.setId(holder.getSheetBeginId() + conf.getId());
				if (holder.getIdSet().contains(conf.getId())) {
					throw new FormatException("序列号编码已存在，id=" + conf.getId());
				}

				if ((conf.getId() / GradeConst.MAX_FILE_ROWS * GradeConst.MAX_FILE_ROWS) != holder
						.getFileBeginId()) {
					throw new FormatException("序列号超出范围，id=" + conf.getId());
				}

				holder.getIdSet().add(conf.getId());
			}

			if (holder.similarityLimit > 0.0
					&& sheetType != LoadConst.SHEET_TYPE_INTERVIEW) {
				// 检查相似性
				checkSimilarity(holder, conf);
			}

			row.setErrorType(LoadConst.TYPE_SUCCESS);
		} catch (SimilarityException e) {
			row.setErrorType(LoadConst.TYPE_SIMILARITY);
			row.setCause(e.getErrDesc());
			row.setOrigQbName(e.getQbName());
			row.setOrigQuestionId(e.getQuestionId());
			row.setOrigRow(e.getRow());
			logger.error("第" + (row.getRowNum() + 1) + "行：" + e.getErrDesc(), e);
		} /*catch (TimeException e) {
			row.setErrorType(LoadConst.TYPE_TIME);
			row.setCause(e.getErrDesc());
			row.setSuggsetTime(e.getSuggestTime());
			logger.error("第" + row.getRowNum() + "行：" + e.getErrDesc(), e);
		} */catch (FormatException e) {
			row.setErrorType(LoadConst.TYPE_FORMAT);
			row.setCause(e.getErrDesc());
			logger.error("第" + (row.getRowNum() + 1) + "行：" + e.getErrDesc(), e);
		} catch (NumberFormatException e) {
			row.setErrorType(LoadConst.TYPE_FORMAT);
			row.setCause(TraceManager.getTrace(e));
			logger.error("第" + (row.getRowNum() + 1) + "行："
					+ TraceManager.getTrace(e));
		} catch (IgnoredException e) {
			if (e.getErrorDesc() != null)
				logger.error(e.getErrorDesc());
			return;
		}

		if (conf.getGroup() == null) {
			if (row.getErrorType() != LoadConst.TYPE_SUCCESS) {
				LoadRowResponse loadRowResponse = new LoadRowResponse();
				Map<Integer, List<LoadRowResponse>> rowsMap = holder
						.getRowsMap();
				List<LoadRowResponse> rows = rowsMap.get(row.getErrorType());
				if (rows == null) {
					rows = new ArrayList<LoadRowResponse>();
					rowsMap.put(row.getErrorType(), rows);
				}
				rows.add(loadRowResponse);

				loadRowResponse.setSheetType(row.getSheetType());
				loadRowResponse.setErrorType(row.getErrorType());

				if (row.getRowNum() > 0 && row.getCause() != null) {
					loadRowResponse.setCause("第" + (row.getRowNum() + 1) + "行："
							+ row.getCause());
				} else {
					loadRowResponse.setCause(row.getCause());
				}

				loadRowResponse.setSuggestTime(row.getSuggsetTime());
				loadRowResponse.setOrigQbName(row.getOrigQbName());
				loadRowResponse.setOrigQuestionId(row.getOrigQuestionId());
				loadRowResponse.setOrigRow(row.getOrigRow());
				setData(loadRowResponse, row.getColumns());

				// 添加或更新错误记录
				QbUploadError qbUploadError = new QbUploadError();
				qbUploadError.setSerialNo(row.getSerialNo());
				qbUploadError.setQbId(holder.getQbId());
				qbUploadError.setErrorType(row.getErrorType());
				qbUploadError.setGroupName(null);

				qbUploadError.setContent(gson.toJson(loadRowResponse));
				qbUploadErrorDao.saveOrUpdate(qbUploadError);

				// 重新设置序列号
				loadRowResponse.setSerialNo(qbUploadError.getSerialNo());
			} else {
				try {
					// 删除错误记录
					if (row.getSerialNo() > 0) {
						QbUploadError qbUploadError = qbUploadErrorDao.get(row.getSerialNo());
						if (qbUploadError != null)
							logger.debug("delete qbUploadError , loadRow " + row.getSerialNo());
							qbUploadErrorDao.delete(qbUploadError);
					}

					// 保存试题
					saveRow(holder, conf);
				} catch (IgnoredException e) {
					if (e.getErrorDesc() != null)
						logger.error(e.getErrorDesc());
				}
			}
		} else {
			// 添加组记录
			Map<String, List<ExcelRow>> groupMap = holder.getGroupMap();
			List<ExcelRow> rows = groupMap.get(conf.getGroup());
			if (rows == null) {
				rows = new ArrayList<ExcelRow>();
				groupMap.put(conf.getGroup(), rows);
			}

			rows.add(row);
		}
	}

	/**
	 * 保存行记录
	 * 
	 * @param holder
	 * @param conf
	 * @throws Exception
	 */
	private void saveRow(Holder holder, ExcelConf conf) throws Exception {
		// 保存试题
		QbQuestion qbQuestion = saveQbQuestion(holder, conf);

		if (conf.getCategory() != GradeConst.CATEGORY_INTERVIEW) {
			// 保存试题详情
			saveQbQuestionDetail(conf);

			// 保存题目技能
			saveQbQuestionSkill(holder, conf);

			// 添加该题做为相似度判断的题
			createDocument(holder, qbQuestion);
		}
	}

	/**
	 * 相似度检查
	 * 
	 * @param holder
	 * @param conf
	 * @throws SimilarityException
	 */
	private void checkSimilarity(Holder holder, ExcelConf conf)
			throws SimilarityException {
		if (holder.isPrebuilt())
			return;

		if (holder.getSimilarityLimit() <= 0)
			return;

		QbSkill qbSkill = null;
		if (conf.getTags() != null && !conf.getTags().isEmpty()) {
			String skillName = conf.getTags().get(0).toLowerCase();
			qbSkill = qbSkillDao.getByName(skillName, "0", holder.getQbId());
			if (qbSkill == null)
				return;
		}

		// 检查相似度
		List<DocumentHolder> documentHolders = holder.getDocumentsMap().get(
				conf.getType());
		if (documentHolders == null)
			return;

		StringBuilder builder = new StringBuilder();
		builder.append(conf.getTitle());
		if (conf.getOptions() != null) {
			List<String> options = new ArrayList<String>();
			options.addAll(conf.getOptions());
			Collections.sort(options);
			for (String option : options) {
				builder.append(option);
			}
		}
		Document doc = new Document(builder.toString());

		for (DocumentHolder documentHolder : documentHolders) {
			QbQuestion qbQuestion = documentHolder.getQbQuestion();
			List<String> skillIds = documentHolder.getSkillIds();
			if (qbSkill == null) {
				if (skillIds != null)
					continue;
			} else {
				if (skillIds == null
						|| !skillIds.contains(qbSkill.getSkillId()))
					continue;
			}

			Document dstDoc = documentHolder.getDocument();

			double similarity = MathUtils.round(
					Similarity.calculateSimilary(doc, dstDoc), 2);
			if (similarity >= holder.getSimilarityLimit()) {
				QbBase qbBase = qbBaseDao.getEntity(qbQuestion.getQbId());
				LoadRow loadRow = getLoadRow(qbQuestion);

				throw new SimilarityException("相似度达到"
						+ (int) MathUtils.round(similarity * 100, 0) + "%",
						qbBase == null ? null : qbBase.getQbName(),
						qbQuestion.getQuestionId(), loadRow);
			}
		}
	}

	/**
	 * 保存试题
	 * 
	 * @param holder
	 * @param conf
	 * @throws IgnoredException
	 * @return
	 */
	private QbQuestion saveQbQuestion(Holder holder, ExcelConf conf)
			throws IgnoredException {
		boolean update;
		// 维护序列，在异常时，需要清除缓存
		QbQuestion qbQuestion = qbQuestionDao.getEntityWithoutCache(conf
				.getId());
		if (qbQuestion == null) {
			if (conf.getStatus() != 1) // 只有状态为1的记录才需要新增
				throw new IgnoredException();

			qbQuestion = new QbQuestion();
			qbQuestion.setQuestionId(conf.getId());
			qbQuestion.setDegree(conf.getLevel());
			qbQuestion.setAnswerNum(conf.getAnswerNum());
			qbQuestion.setCorrectNum(conf.getAnswerNum()
					* (100 - conf.getFailPercent()) / 100);
			qbQuestion.setCreateDate(new Timestamp(System.currentTimeMillis()));
			qbQuestion.setModifyDate(qbQuestion.getCreateDate());
			update = false;
		} else {
			qbQuestion.setModifyDate(new Timestamp(System.currentTimeMillis()));
			update = true;
		}

		qbQuestion.setQbId(holder.getQbId());
		qbQuestion.setQuestionType(conf.getType());
		qbQuestion.setCategory(conf.getCategory());
		qbQuestion.setQuestionDesc(conf.getTitle());
		qbQuestion.setProgramLanguage(conf.getMode());
		qbQuestion.setIsSample(conf.isSample() ? 1 : 0);

		int questionType = GradeConst.toQuestionTypeInt(conf.getType());

		// 设置分值
		if (conf.getScore() != 0) {
			qbQuestion.setPoint(conf.getScore());
		} else {
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
				if (conf.getCategory() == GradeConst.CATEGORY_TECHNOLOGY)
					qbQuestion.setPoint(2);
				else
					qbQuestion.setPoint(10);
				break;
			case GradeConst.QUESTION_TYPE_VIDEO:
			case GradeConst.QUESTION_TYPE_GROUP:
				qbQuestion.setPoint(0);
				break;
			case GradeConst.QUESTION_TYPE_PROGRAM:
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			case GradeConst.QUESTION_TYPE_ESSAY:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			default:
				qbQuestion.setPoint(10);
				break;
			}
		}

		// 设置建议时间
		if (conf.getSuggestTime() == 0) {
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE: {
				conf.setSuggestTime(getSuggestTime(conf.getTitle(),
						conf.getOptions()));
				break;
			}
			case GradeConst.QUESTION_TYPE_PROGRAM:
				conf.setSuggestTime(900);
				break;
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			case GradeConst.QUESTION_TYPE_ESSAY:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_VIDEO:
				conf.setSuggestTime(600);
				break;
			default:
				break;
			}
		}

		qbQuestion.setSuggestTime(conf.getSuggestTime());
		qbQuestion.setSubAsks(null);
		qbQuestion.setDeriveFlag(conf.getParam() != null ? 1 : 0);
		qbQuestion.setState(conf.getStatus());
		qbQuestion.setCreateBy(holder.getCreateBy());
		qbQuestion.setHtml(conf.isHtml() ? 1 : 0);
		qbQuestion.setPrebuilt(holder.isPrebuilt() ? 1 : 0);
		if (update)
			qbQuestionDao.update(qbQuestion);
		else
			qbQuestionDao.save(qbQuestion);

		return qbQuestion;
	}

	/**
	 * 保存试题详情
	 * 
	 * @param conf
	 * @throws Exception
	 */
	private void saveQbQuestionDetail(ExcelConf conf) throws Exception {
		boolean update;
		QuestionContent questionContent;

		try {
			IGenerator generator = GeneratorFactory.createFactory(conf);
			if (generator == null) {
				if (conf.getStatus() == 1)
					throw new FormatException("不认识的行记录，id=" + conf.getId());

				questionContent = new QuestionContent();
			} else {
				// 生成试题内容
				questionContent = generator.generate();
			}
		} catch (Exception e) {
			if (conf.getStatus() == 1)
				throw new FormatException("不认识的行记录，id=" + conf.getId());

			questionContent = new QuestionContent();
		}

		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao
				.getWithoutCache(conf.getId());
		if (qbQuestionDetail == null) {
			qbQuestionDetail = new QbQuestionDetail();
			qbQuestionDetail.setQuestionId(conf.getId());
			update = false;
		} else {
			update = true;
		}

		String content = gson.toJson(questionContent);
		if (encrypted) {
			content = new String(DESCoder.decrypt(
					content.getBytes(GradeConst.ENCODING), GradeConst.DES_KEY),
					GradeConst.ENCODING);
		}
		qbQuestionDetail.setContent(content);

		if (update)
			qbQuestionDetailDao.update(qbQuestionDetail);
		else
			qbQuestionDetailDao.save(qbQuestionDetail);
	}

	/**
	 * 保存试题技能
	 * 
	 * @param holder
	 * @param conf
	 * @throws Exception
	 */
	private void saveQbQuestionSkill(Holder holder, ExcelConf conf) {
		// 先删除原有标签数据
		qbQuestionSkillDao.deleteByQuestionId(conf.getId());

		// 检查是否有标签
		List<String> tags = conf.getTags();
		if (tags == null || tags.isEmpty())
			return;

		// 保存试题相关技能
		String parentId = "0";
		for (int i = 0; i < tags.size(); i++) {
			QbQuestionSkill qbQuestionSkill = new QbQuestionSkill();
			QbQuestionSkillId qbQuestionSkillId = new QbQuestionSkillId();
			qbQuestionSkill.setSkillSeq(i + 1);

			qbQuestionSkillId.setQuestionId(conf.getId());
			qbQuestionSkill.setId(qbQuestionSkillId);

			String skillName = tags.get(i).toLowerCase();
			QbSkill qbSkill = qbSkillDao.getByName(skillName, parentId,
					holder.getQbId());
			if (qbSkill == null) {
				if (!holder.isPrebuilt()) {
					// 插入技能
					qbSkill = new QbSkill();
					qbSkill.setSkillId(qbSkillDao.getNextSkillId());
					qbSkill.setSkillName(skillName);
					qbSkill.setSkillAlias(null);
					qbSkill.setParentId("0");
					qbSkill.setLevel(1);
					qbSkill.setQbId(holder.getQbId());
					qbSkillDao.save(qbSkill);
				} else {
					if (conf.getStatus() == 1) {
						if (i == 0) {
							logger.error("不认识的一级技能：" + skillName + ", id="
									+ conf.getId() + ", parentId=" + parentId);
						} else {
							logger.info("不认识的次级技能：" + skillName + ", id="
									+ conf.getId() + ", parentId=" + parentId);
						}
					}
					continue;
				}
			}

			qbQuestionSkillId.setSkillId(qbSkill.getSkillId());
			qbQuestionSkillDao.save(qbQuestionSkill);

			parentId = qbSkill.getSkillId();
		}
	}

	/**
	 * 保存组数据
	 * 
	 * @param holder
	 * @param serialNo
	 * @throws Exception
	 */
	private void saveGroup(Holder holder, int serialNo) throws Exception {
		Map<String, List<ExcelRow>> groupMap = holder.getGroupMap();

		LoadResponse response = holder.getResponse();
		int similarityErrors = response.getSimilarityErrors();
		int timeErrors = response.getTimeErrors();
		int formatErrors = response.getFormatErrors();
		List<LoadGroup> groups = response.getGroups();

		for (Entry<String, List<ExcelRow>> entry : groupMap.entrySet()) {
			String group = entry.getKey();
			List<ExcelRow> rows = entry.getValue();
			LoadGroupKey groupKey = new LoadGroupKey();

			// 题组里只要有一道题错误，都认为错误
			boolean hasError = false;
			for (ExcelRow row : rows) {
				switch (row.getErrorType()) {
				case LoadConst.TYPE_SUCCESS:
					break;
				case LoadConst.TYPE_FORMAT:
					hasError = true;
					break;
				default:
					hasError = true;
					logger.error("内部错误：题组的错误类型超出范围");
					break;
				}
			}

			if (hasError)
				groupKey.setErrorType(LoadConst.TYPE_FORMAT);
			else
				groupKey.setErrorType(LoadConst.TYPE_SUCCESS);
			groupKey.setGroup(group);

			List<Long> ids = new ArrayList<Long>();
			if (hasError) {
				LoadGroup loadGroup = new LoadGroup(groupKey);
				groups.add(loadGroup);

				List<LoadRowResponse> loadRowResponses = new ArrayList<LoadRowResponse>();
				loadGroup.setRows(loadRowResponses);

				for (ExcelRow row : rows) {
					switch (row.getErrorType()) {
					case LoadConst.TYPE_SIMILARITY:
						similarityErrors++;
						break;
					case LoadConst.TYPE_TIME:
						timeErrors++;
						break;
					case LoadConst.TYPE_FORMAT:
						formatErrors++;
						break;
					}

					LoadRowResponse loadRowResponse = new LoadRowResponse();
					loadRowResponse.setSheetType(row.getSheetType());
					loadRowResponse.setErrorType(row.getErrorType());

					if (row.getRowNum() > 0 && row.getCause() != null) {
						loadRowResponse.setCause("第" + (row.getRowNum() + 1) + "行："
								+ row.getCause());
					} else {
						loadRowResponse.setCause(row.getCause());
					}

					loadRowResponse.setSuggestTime(row.getSuggsetTime());
					loadRowResponse.setOrigQbName(row.getOrigQbName());
					loadRowResponse.setOrigQuestionId(row.getOrigQuestionId());
					loadRowResponse.setOrigRow(row.getOrigRow());
					setData(loadRowResponse, row.getColumns());
					loadRowResponses.add(loadRowResponse);
				}

				// 添加或更新错误记录
				QbUploadError qbUploadError = new QbUploadError();
				qbUploadError.setSerialNo(serialNo);
				qbUploadError.setQbId(holder.getQbId());
				qbUploadError.setErrorType(groupKey.getErrorType());
				qbUploadError.setGroupName(groupKey.getGroup());

				qbUploadError.setContent(gson.toJson(loadGroup));
				qbUploadErrorDao.saveOrUpdate(qbUploadError);

				// 重新设置序列号
				loadGroup.setSerialNo(qbUploadError.getSerialNo());
			} else {
				// 如果有组数据，则先删除组记录
				if (serialNo != 0) {
					QbUploadError qbUploadError = qbUploadErrorDao.get(serialNo);
					if (qbUploadError != null)
						logger.debug("delete qbUploadError by request " + serialNo);
						qbUploadErrorDao.delete(qbUploadError);
				}

				int suggestTime = 0;
				for (ExcelRow row : rows) {
					try {
						ExcelConf conf = row.getConf();

						// 删除错误记录
						if (row.getSerialNo() > 0) {
							QbUploadError qbUploadError = qbUploadErrorDao.get(row.getSerialNo());
							if (qbUploadError != null)
								logger.debug("delete qbUploadError by row " + row.getSerialNo());
								qbUploadErrorDao.delete(qbUploadError);
						}

						// 保存试题
						saveRow(holder, conf);
						suggestTime += conf.getSuggestTime();

						ids.add(conf.getId());
					} catch (IgnoredException e) {
						if (e.getErrorDesc() != null)
							logger.error(e.getErrorDesc());
					}
				}

				// 添加题组
				QbQuestion qbQuestion = new QbQuestion();
				qbQuestion.setQuestionId(qbQuestionDao.getNextQid());
				qbQuestion.setDegree(0);
				qbQuestion.setAnswerNum(0);
				qbQuestion.setCorrectNum(0);
				qbQuestion.setQbId(holder.getQbId());
				qbQuestion.setQuestionType(GradeConst.QUESTION_TYPE_NAME_GROUP);
				qbQuestion.setCategory(GradeConst.CATEGORY_INTERVIEW);
				qbQuestion.setQuestionDesc(groupKey.getGroup());
				qbQuestion.setProgramLanguage(null);
				qbQuestion.setIsSample(0);
				qbQuestion.setPoint(0);
				qbQuestion.setSuggestTime(suggestTime);
				qbQuestion.setSubAsks(StringUtils.join(ids, ','));
				qbQuestion.setDeriveFlag(0);
				qbQuestion.setState(1);
				qbQuestion.setCreateBy(holder.getCreateBy());
				qbQuestion.setCreateDate(new Timestamp(System
						.currentTimeMillis()));
				qbQuestion.setModifyDate(qbQuestion.getCreateDate());
				qbQuestion.setHtml(0);
				qbQuestion.setPrebuilt(holder.isPrebuilt() ? 1 : 0);
				qbQuestionDao.save(qbQuestion);
			}
		}

		response.setSimilarityErrors(similarityErrors);
		response.setTimeErrors(timeErrors);
		response.setFormatErrors(formatErrors);
	}

	/**
	 * 保存题数据
	 * 
	 * @param holder
	 */
	private void saveQuestions(Holder holder) {
		LoadResponse response = holder.getResponse();
		List<LoadGroup> groups = response.getGroups();

		for (Entry<Integer, List<LoadRowResponse>> entry : holder.getRowsMap()
				.entrySet()) {
			int errorType = entry.getKey();
			List<LoadRowResponse> rows = entry.getValue();

			switch (errorType) {
			case LoadConst.TYPE_SIMILARITY:
				response.setSimilarityErrors(response.getSimilarityErrors()
						+ rows.size());
				break;
			case LoadConst.TYPE_TIME:
				response.setTimeErrors(response.getTimeErrors() + rows.size());
				break;
			case LoadConst.TYPE_FORMAT:
				response.setFormatErrors(response.getFormatErrors()
						+ rows.size());
				break;
			default:
				continue;
			}

			LoadGroup loadGroup = new LoadGroup();
			loadGroup.setErrorType(errorType);
			loadGroup.setRows(rows);
			groups.add(loadGroup);
		}
	}

	/**
	 * 去除前后的回车、换行
	 * 
	 * @param str
	 * @return
	 */
	private static String trim(String str) {
		if (str == null)
			return null;

		int begin = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch != '\n' || ch != '\r')
				break;

			begin++;
		}

		int end = str.length();
		for (int i = str.length() - 1; i >= 0; i--) {
			char ch = str.charAt(i);
			if (ch != '\n' || ch != '\r')
				break;

			end--;
		}

		if (begin != 0 || end != str.length())
			str = str.substring(begin, end);

		if (str.length() >= 2 && str.startsWith("\"") && str.endsWith("\""))
			str = str.substring(1, str.length() - 1);

		return str;
	}

	/**
	 * 分析整形数，异常时返回0
	 * 
	 * @param value
	 *            字符串
	 * @return
	 */
	private static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 临时保存对象
	 * 
	 * @author xugq
	 * 
	 */
	public static class Holder {
		private Long fileBeginId;
		private int createBy;
		private int qbId;
		private long sheetBeginId;
		private double similarityLimit;
		private boolean checkTime;
		private List<Integer> columnIndexes;
		private Set<Long> idSet;
		private Map<String, List<DocumentHolder>> documentsMap;
		private boolean prebuilt;
		private Map<String, List<ExcelRow>> groupMap;
		private Map<Position, Position> positionMap;
		private Map<Integer, List<LoadRowResponse>> rowsMap;
		private LoadResponse response;

		public Holder() {
			columnIndexes = new ArrayList<Integer>();
			idSet = new HashSet<Long>();
			documentsMap = new HashMap<String, List<DocumentHolder>>();
			groupMap = new HashMap<String, List<ExcelRow>>();
			positionMap = new HashMap<Position, Position>();
			rowsMap = new HashMap<Integer, List<LoadRowResponse>>();
			response = new LoadResponse();
		}

		public Long getFileBeginId() {
			return fileBeginId;
		}

		public void setFileBeginId(Long fileBeginId) {
			this.fileBeginId = fileBeginId;
		}

		public int getCreateBy() {
			return createBy;
		}

		public void setCreateBy(int createBy) {
			this.createBy = createBy;
		}

		public int getQbId() {
			return qbId;
		}

		public void setQbId(int qbId) {
			this.qbId = qbId;
		}

		public long getSheetBeginId() {
			return sheetBeginId;
		}

		public void setSheetBeginId(long sheetBeginId) {
			this.sheetBeginId = sheetBeginId;
		}

		public double getSimilarityLimit() {
			return similarityLimit;
		}

		public void setSimilarityLimit(double similarityLimit) {
			this.similarityLimit = similarityLimit;
		}

		public boolean isCheckTime() {
			return checkTime;
		}

		public void setCheckTime(boolean checkTime) {
			this.checkTime = checkTime;
		}

		public List<Integer> getColumnIndexes() {
			return columnIndexes;
		}

		public Set<Long> getIdSet() {
			return idSet;
		}

		public Map<String, List<DocumentHolder>> getDocumentsMap() {
			return documentsMap;
		}

		public void setDocumentsMap(
				Map<String, List<DocumentHolder>> documentsMap) {
			this.documentsMap = documentsMap;
		}

		public boolean isPrebuilt() {
			return prebuilt;
		}

		public void setPrebuilt(boolean prebuilt) {
			this.prebuilt = prebuilt;
		}

		public Map<String, List<ExcelRow>> getGroupMap() {
			return groupMap;
		}

		public void setGroupMap(Map<String, List<ExcelRow>> groupMap) {
			this.groupMap = groupMap;
		}

		public Map<Position, Position> getPositionMap() {
			return positionMap;
		}

		public void setPositionMap(Map<Position, Position> positionMap) {
			this.positionMap = positionMap;
		}

		public Map<Integer, List<LoadRowResponse>> getRowsMap() {
			return rowsMap;
		}

		public void setRowsMap(Map<Integer, List<LoadRowResponse>> rowsMap) {
			this.rowsMap = rowsMap;
		}

		public LoadResponse getResponse() {
			return response;
		}

		public void setResponse(LoadResponse response) {
			this.response = response;
		}
	}

	public static class DocumentHolder {
		private QbQuestion qbQuestion;
		private Document document;
		private List<String> skillIds; // 技能

		public QbQuestion getQbQuestion() {
			return qbQuestion;
		}

		public void setQbQuestion(QbQuestion qbQuestion) {
			this.qbQuestion = qbQuestion;
		}

		public Document getDocument() {
			return document;
		}

		public void setDocument(Document document) {
			this.document = document;
		}

		public List<String> getSkillIds() {
			return skillIds;
		}

		public void setSkillIds(List<String> skillIds) {
			this.skillIds = skillIds;
		}
	}

	public static void setData(LoadRow loadRow, List<ExcelColumn> columns) {
		for (ExcelColumn column : columns) {
			switch (column.getIndex()) {
			case ExcelConf.COLUMN_TEMPLATE_SKILL:
				loadRow.setSkill(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_LEVEL:
				loadRow.setLevel(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_SUGGEST_SECONDS:
				loadRow.setSuggestSeconds(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_SUGGEST_MINUTES:
				loadRow.setSuggestMinutes(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_TITLE:
				loadRow.setTitle(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_OPTION:
				if (loadRow.getOptions() == null)
					loadRow.setOptions(new ArrayList<String>());
				loadRow.getOptions().add(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_CORRECT_OPTIONS:
				loadRow.setCorrectOptions(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_REF_ANSWER:
				loadRow.setRefAnswer(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_EXPLAIN_REQUIRED:
				loadRow.setExplainReqired(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_REF_EXPLAIN:
				loadRow.setRefExplain(column.getValue());
				break;
			case ExcelConf.COLUMN_TEMPLATE_MODE:
				loadRow.setMode(column.getValue());
				break;
			case ExcelConf.COLUMN_HTML:
				if ("1".equals(column.getValue()))
					loadRow.setHtml(true);
				else
					loadRow.setHtml(null);
				break;
			}
		}
	}

	public static List<ExcelColumn> getLoadColumns(LoadRow loadRow) {
		List<ExcelColumn> columns = new ArrayList<ExcelColumn>();

		if (loadRow.getSkill() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_SKILL);
			column.setValue(loadRow.getSkill());
			columns.add(column);
		}

		if (loadRow.getLevel() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_LEVEL);
			column.setValue(loadRow.getLevel());
			columns.add(column);
		}

		if (loadRow.getSuggestSeconds() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_SUGGEST_SECONDS);
			column.setValue(loadRow.getSuggestSeconds());
			columns.add(column);
		}

		if (loadRow.getSuggestMinutes() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_SUGGEST_MINUTES);
			column.setValue(loadRow.getSuggestMinutes());
			columns.add(column);
		}

		if (loadRow.getTitle() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_TITLE);
			column.setValue(loadRow.getTitle());
			columns.add(column);
		}

		if (loadRow.getOptions() != null) {
			for (String option : loadRow.getOptions()) {
				ExcelColumn column = new ExcelColumn();
				column.setIndex(ExcelConf.COLUMN_TEMPLATE_OPTION);
				column.setValue(option);
				columns.add(column);
			}
		}

		if (loadRow.getCorrectOptions() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_CORRECT_OPTIONS);
			column.setValue(loadRow.getCorrectOptions());
			columns.add(column);
		}

		if (loadRow.getRefAnswer() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_REF_ANSWER);
			column.setValue(loadRow.getRefAnswer());
			columns.add(column);
		}

		if (loadRow.getExplainReqired() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_EXPLAIN_REQUIRED);
			column.setValue(loadRow.getExplainReqired());
			columns.add(column);
		}

		if (loadRow.getRefExplain() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_REF_EXPLAIN);
			column.setValue(loadRow.getRefExplain());
			columns.add(column);
		}

		if (loadRow.getMode() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_TEMPLATE_MODE);
			column.setValue(loadRow.getMode());
			columns.add(column);
		}

		if (loadRow.getHtml() != null) {
			ExcelColumn column = new ExcelColumn();
			column.setIndex(ExcelConf.COLUMN_HTML);
			column.setValue("1");
			columns.add(column);
		}

		return columns;
	}

	private LoadRow getLoadRow(QbQuestion qbQuestion) {
		LoadRow loadRow = new LoadRow();
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qbQuestion
				.getQuestionId());
		QuestionContent questionContent = gson.fromJson(
				qbQuestionDetail.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();

		int questionTypeInt = GradeConst.toQuestionTypeInt(qbQuestion
				.getQuestionType());
		switch (questionTypeInt) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
			if (qbQuestion.getCategory() == GradeConst.CATEGORY_INTELLIGENCE) {
				List<String> skillIds = qbQuestionSkillDao
						.getSkillIds(qbQuestion.getQuestionId());
				if (skillIds != null && !skillIds.isEmpty())
					loadRow.setSkill(skillIds.get(0));
				loadRow.setExplainReqired("否");
			}

			loadRow.setSuggestSeconds(qbQuestion.getSuggestTime().toString());
			loadRow.setOptions(questionConf.getOptions());
			loadRow.setCorrectOptions(QuestionUtils.getAnswers(questionConf
					.getAnswer()));
			break;
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			loadRow.setSuggestSeconds(qbQuestion.getSuggestTime().toString());
			loadRow.setOptions(questionConf.getOptions());
			loadRow.setCorrectOptions(QuestionUtils.getAnswers(questionConf
					.getAnswer()));
			loadRow.setExplainReqired("是");
			loadRow.setRefExplain(questionConf.getOptDesc());
			break;
		default:
			loadRow.setSuggestMinutes(Integer.toString(qbQuestion
					.getSuggestTime() / 60));
			FileInfo fileInfo = QuestionUtils.getFileInfo(questionContent);
			loadRow.setRefAnswer(fileInfo.getRefAnswer());
			break;
		}

		loadRow.setLevel(GradeConst.getLevelString(qbQuestion.getDegree()));
		loadRow.setTitle(qbQuestion.getQuestionDesc());
		loadRow.setMode(questionConf.getMode());

		return loadRow;
	}

	private void createDocument(Holder holder, QbQuestion qbQuestion) {
		StringBuilder builder = new StringBuilder();
		builder.append(qbQuestion.getQuestionDesc());

		int questionType = GradeConst.toQuestionTypeInt(qbQuestion
				.getQuestionType());
		switch (questionType) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao
					.get(qbQuestion.getQuestionId());
			if (qbQuestionDetail != null) {
				QuestionContent qbQuestionContent = gson.fromJson(
						qbQuestionDetail.getContent(), QuestionContent.class);
				QuestionConf questionConf = qbQuestionContent.getQuestionConf();

				List<String> options = new ArrayList<String>();
				options.addAll(questionConf.getOptions());
				Collections.sort(options);
				for (String option : options) {
					builder.append(option);
				}
			}
			break;
		}

		Map<String, List<DocumentHolder>> documentsMap = holder
				.getDocumentsMap();
		List<DocumentHolder> documentHolders = documentsMap.get(qbQuestion
				.getQuestionType());
		if (documentHolders == null) {
			documentHolders = new ArrayList<DocumentHolder>();
			documentsMap.put(qbQuestion.getQuestionType(), documentHolders);
		}

		DocumentHolder documentHolder = new DocumentHolder();
		documentHolder.setDocument(new Document(builder.toString()));
		documentHolder.setQbQuestion(qbQuestion);

		List<String> skillIds = qbQuestionSkillDao.getSkillIds(qbQuestion
				.getQuestionId());
		if (skillIds != null && !skillIds.isEmpty())
			documentHolder.setSkillIds(skillIds);

		documentHolders.add(documentHolder);
	}

}
