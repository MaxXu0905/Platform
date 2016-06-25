package com.ailk.sets.grade.excel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.dao.intf.IQbUploadErrorDao;
import com.ailk.sets.grade.excel.intf.IExportExcel;
import com.ailk.sets.grade.excel.intf.PositionRow;
import com.ailk.sets.grade.excel.intf.ScorePair;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.InvalidDataException;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.ExportPositionResponse;
import com.ailk.sets.grade.intf.GetGroupResponse;
import com.ailk.sets.grade.intf.GetQuestionResponse;
import com.ailk.sets.grade.intf.LoadConst;
import com.ailk.sets.grade.intf.LoadGroup;
import com.ailk.sets.grade.intf.LoadGroupKey;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.grade.intf.LoadRow;
import com.ailk.sets.grade.intf.LoadRowResponse;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.IReportConfig;
import com.ailk.sets.grade.intf.report.Interview;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.Part;
import com.ailk.sets.grade.intf.report.PartItem;
import com.ailk.sets.grade.intf.report.Summary;
import com.ailk.sets.grade.intf.report.UserInfo;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.jdbc.QbUploadError;
import com.ailk.sets.grade.service.intf.IReportRetrieveService;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolReportDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionGroupRow;
import com.ailk.sets.platform.intf.empl.domain.PositionGrpChildRow;
import com.ailk.sets.platform.intf.empl.domain.PositionToConfigInfo;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;

@Transactional(rollbackFor = Exception.class)
@Service
public class ExportExcel implements IExportExcel {

	private Logger logger = LoggerFactory.getLogger(ExportExcel.class);
	private static final Gson gson = new Gson();

	private static final int TOTAL_SHEETS = 6;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IQbBaseDao qbBaseDao;

	@Autowired
	private IQbUploadErrorDao qbUploadErrorDao;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IReportRetrieveService reportRetrieveService;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private IPositionInfoExtDao positionInfoExtDao;
	@Autowired
	private IPositionRelationDao positionRelationDao;
	
	@Autowired
	private ISchoolReportDao schoolReportDao;
	@Autowired
	private ICandidateInfoExtDao candidateInfoExtDao;

	private static final int TEMPLATE_INDEX_MIN = 0;
	private static final int TEMPLATE_INDEX_NAME = 0;
	private static final int TEMPLATE_INDEX_EMAIL = 1;
	private static final int TEMPLATE_INDEX_PHONE = 2;
	private static final int TEMPLATE_INDEX_STATE = 3;
	private static final int TEMPLATE_INDEX_INTERVIEW_RESULT = 4;
	private static final int TEMPLATE_INDEX_SCORE = 5;
	private static final int TEMPLATE_INDEX_RANK = 6;
	private static final int TEMPLATE_INDEX_CHOICE = 7;
	private static final int TEMPLATE_INDEX_PROGRAM = 8;
	private static final int TEMPLATE_INDEX_TECH_ESSAY = 9;
	private static final int TEMPLATE_INDEX_INTEL_SCORE = 10;
	private static final int TEMPLATE_INDEX_INTEL_RANK = 11;
	private static final int TEMPLATE_INDEX_INTEL_CHOICE = 12;
	private static final int TEMPLATE_INDEX_INTEL_ESSAY = 13;
	private static final int TEMPLATE_INDEX_INTERVIEW_SCORE = 14;
	private static final int TEMPLATE_INDEX_INTERVIEW_RANK = 15;
	private static final int TEMPLATE_INDEX_INTERVIEW_ITEM = 16;
	private static final int TEMPLATE_INDEX_EXT_INFO = 17;
	private static final int TEMPLATE_INDEX_DURATION = 18;
	private static final int TEMPLATE_INDEX_BEGIN_TIME = 19;
	private static final int TEMPLATE_INDEX_END_TIME = 20;
	private static final int TEMPLATE_INDEX_MAX = 20;
	
	
	    private static final int GROUP_TEMPLATE_INDEX_MIN = 0;
		private static final int GROUP_TEMPLATE_INDEX_NAME = 0;
		private static final int GROUP_TEMPLATE_INDEX_EMAIL = 1;
		private static final int GROUP_TEMPLATE_INDEX_PHONE = 2;
		private static final int GROUP_TEMPLATE_INDEX_EXT_INFO = 3;
//		private static final int GROUP_TEMPLATE_INDEX_INTERVIEW_RESULT = 4;
		private static final int GROUP_TEMPLATE_INDEX_SCORE = 4;
		private static final int GROUP_TEMPLATE_INDEX_RANK = 5;
		private static final int GROUP_TEMPLATE_INDEX_CHOICE = 6;
		private static final int GROUP_TEMPLATE_INDEX_PROGRAM = 7;
		private static final int GROUP_TEMPLATE_INDEX_TECH_ESSAY = 8;
		private static final int GROUP_TEMPLATE_INDEX_INTEL_SCORE = 9;
		private static final int GROUP_TEMPLATE_INDEX_INTEL_RANK = 10;
		private static final int GROUP_TEMPLATE_INDEX_INTEL_CHOICE = 11;
		private static final int GROUP_TEMPLATE_INDEX_INTEL_ESSAY = 12;
//		private static final int GROUP_TEMPLATE_INDEX_INTERVIEW_SCORE = 14;
//		private static final int GROUP_TEMPLATE_INDEX_INTERVIEW_RANK = 15;
//		private static final int GROUP_TEMPLATE_INDEX_INTERVIEW_ITEM = 16;
//		private static final int GROUP_TEMPLATE_INDEX_EXT_INFO = 17;
		private static final int GROUP_TEMPLATE_INDEX_DURATION = 13;
		private static final int GROUP_TEMPLATE_INDEX_BEGIN_TIME = 14;
		private static final int GROUP_TEMPLATE_INDEX_END_TIME = 15;
		private static final int GROUP_TEMPLATE_INDEX_MAX = 15;

	private static class Holder {
		private List<Object> objs;
		private int sheetId;

		public List<Object> getObjs() {
			return objs;
		}

		public void setObjs(List<Object> objs) {
			this.objs = objs;
		}

		public int getSheetId() {
			return sheetId;
		}

		public void setSheetId(int sheetId) {
			this.sheetId = sheetId;
		}
	}

	@Override
	public byte[] exportExcel(int createBy, Integer qbId, int category,
			boolean isXSSF) throws Exception {
		InputStream in = null;

		try {
			List<QbQuestion> qbQuestions = qbQuestionDao.getListByParams(
					createBy, qbId, category);
			if (qbQuestions == null)
				return null;

			Workbook workBook;
			if (isXSSF) {
				in = ExportExcel.class.getResourceAsStream("/load.xlsx");
				workBook = new XSSFWorkbook(in);
			} else {
				in = ExportExcel.class.getResourceAsStream("/load.xls");
				workBook = new HSSFWorkbook(in);
			}

			export(workBook, qbQuestions, true);
			removeSheets(category, workBook);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);
			return out.toByteArray();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public byte[] exportPaper(int paperId, boolean isXSSF) throws Exception {
		InputStream in = null;

		try {
			List<QbQuestion> qbQuestions = qbQuestionDao
					.getListByPaperId(paperId);
			if (qbQuestions == null)
				return null;

			Workbook workBook;
			if (isXSSF) {
				in = ExportExcel.class.getResourceAsStream("/load.xlsx");
				workBook = new XSSFWorkbook(in);
			} else {
				in = ExportExcel.class.getResourceAsStream("/load.xls");
				workBook = new HSSFWorkbook(in);
			}

			export(workBook, qbQuestions, false);

			for (int i = TOTAL_SHEETS - 1; i >= 0; i--) {
				Sheet sheet = workBook.getSheetAt(i);
				if (sheet.getLastRowNum() < 1)
					workBook.removeSheetAt(i);
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);
			return out.toByteArray();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean isEmpty(int qbId) {
		return qbUploadErrorDao.isEmpty(qbId);
	}

	public LoadResponse getErrorQb(int createBy, int qbId) throws Exception {
		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null)
			throw new InvalidDataException(BaseResponse.ENOENT, "题库不不存在");
		if (qbBase.getPrebuilt() == 1)
			throw new InvalidDataException(BaseResponse.EPERM,
					"预定义题库不允许导出，qbId=" + qbId);
		if (qbBase.getCreateBy() != createBy)
			throw new InvalidDataException(BaseResponse.EPERM, "题库的创建者不正确");

		LoadResponse response = new LoadResponse();
		List<QbUploadError> qbUploadErrors = qbUploadErrorDao.getList(qbId);
		if (qbUploadErrors == null)
			return response;

		int similarityErrors = 0;
		int timeErrors = 0;
		int formatErrors = 0;
		Map<LoadGroupKey, LoadGroup> groupMap = new HashMap<LoadGroupKey, LoadGroup>();
		for (QbUploadError qbUploadError : qbUploadErrors) {
			if (qbUploadError.getGroupName() == null) {
				// 单题模式，需要修正错误序列号
				LoadRowResponse loadRowResponse = gson.fromJson(
						qbUploadError.getContent(), LoadRowResponse.class);
				loadRowResponse.setSerialNo(qbUploadError.getSerialNo());

				switch (loadRowResponse.getErrorType()) {
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

				LoadGroupKey loadGroupKey = new LoadGroupKey();
				loadGroupKey.setErrorType(qbUploadError.getErrorType());
				loadGroupKey.setGroup(qbUploadError.getGroupName());

				LoadGroup loadGroup = groupMap.get(loadGroupKey);
				if (loadGroup == null) {
					loadGroup = new LoadGroup(loadGroupKey);
					groupMap.put(loadGroupKey, loadGroup);
				}

				loadGroup.getRows().add(loadRowResponse);
			} else {
				// 组模式，需要修正错误序列号
				LoadGroup loadGroup = gson.fromJson(qbUploadError.getContent(),
						LoadGroup.class);
				loadGroup.setSerialNo(qbUploadError.getSerialNo());

				for (LoadRowResponse row : loadGroup.getRows()) {
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
				}

				groupMap.put(loadGroup, loadGroup);
			}
		}

		List<LoadGroup> groups = new ArrayList<LoadGroup>();
		for (LoadGroup loadGroup : groupMap.values()) {
			groups.add(loadGroup);
		}

		response.setSimilarityErrors(similarityErrors);
		response.setFormatErrors(formatErrors);
		response.setTimeErrors(timeErrors);
		response.setGroups(groups);
		return response;
	}

	@Override
	public byte[] exportErrorExcel(int createBy, int qbId, boolean isXSSF)
			throws Exception {
		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null)
			throw new InvalidDataException(BaseResponse.ENOENT, "题库不不存在");
		if (qbBase.getCreateBy() != createBy)
			throw new InvalidDataException(BaseResponse.EPERM, "题库的创建者不正确");

		InputStream in = null;

		try {
			List<QbUploadError> qbUploadErrors = qbUploadErrorDao
					.getFormatErrorList(qbId);
			if (qbUploadErrors == null)
				return null;

			Workbook workBook;
			if (isXSSF) {
				in = ExportExcel.class.getResourceAsStream("/error.xlsx");
				workBook = new XSSFWorkbook(in);
			} else {
				in = ExportExcel.class.getResourceAsStream("/error.xls");
				workBook = new HSSFWorkbook(in);
			}

			Sheet[] sheets = new Sheet[TOTAL_SHEETS];
			for (int i = 0; i < TOTAL_SHEETS; i++) {
				Sheet sheet = workBook.getSheetAt(i);
				sheets[i] = sheet;
				for (int j = sheet.getLastRowNum(); j > 0; j--) {
					Row row = sheet.getRow(j);
					sheet.removeRow(row);
				}
			}

			for (QbUploadError qbUploadError : qbUploadErrors) {
				if (qbUploadError.getGroupName() == null) {
					LoadRowResponse loadRowResponse = gson.fromJson(
							qbUploadError.getContent(), LoadRowResponse.class);
					List<Object> objs = loadRowResponse.getObjectColumns(
							loadRowResponse.getSheetType(),
							qbUploadError.getGroupName());
					createRow(sheets[loadRowResponse.getSheetType()
							- LoadConst.SHEET_TYPE_EXTERNAL_MIN], objs, null);
				} else {
					CellStyle regionStyle = workBook.createCellStyle();
					regionStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					regionStyle.setAlignment(CellStyle.ALIGN_CENTER);
					regionStyle.setWrapText(true);

					Sheet sheet = sheets[LoadConst.SHEET_TYPE_INTERVIEW
							- LoadConst.SHEET_TYPE_EXTERNAL_MIN];
					LoadGroup loadGroup = gson.fromJson(
							qbUploadError.getContent(), LoadGroup.class);
					List<LoadRowResponse> rows = loadGroup.getRows();
					for (LoadRowResponse loadRowResponse : rows) {
						List<Object> objs = loadRowResponse.getObjectColumns(
								loadRowResponse.getSheetType(),
								qbUploadError.getGroupName());
						createRow(sheet, objs, regionStyle);
					}

					CellRangeAddress region = new CellRangeAddress(
							sheet.getLastRowNum() - rows.size() + 1,
							sheet.getLastRowNum(), 0, 0);
					sheet.addMergedRegion(region);
				}
			}
            removeSheets(qbBase.getCategory(), workBook);
            	

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);
			return out.toByteArray();
		}
		finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public GetQuestionResponse getQuestion(long questionId) {
		GetQuestionResponse response = new GetQuestionResponse();

		QbQuestion qbQuestion = qbQuestionDao.getEntity(questionId);
		if (qbQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到对应的题目，questionId=" + questionId);
			return response;
		}

		// 预定义的题
		if (qbQuestion.getPrebuilt() == 1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("内部题目不允许修改，questionId=" + questionId);
			return response;
		}

		Holder holder = createList(qbQuestion, null);
		if (holder.getSheetId() == -1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("题目类型不正确，questionId=" + questionId);
			return response;
		}

		LoadRow row = new LoadRow();
		if (qbQuestion.getHtml() != null && qbQuestion.getHtml() == 1)
			row.setHtml(true);
		int sheetType = LoadConst.SHEET_TYPE_EXTERNAL_MIN + holder.getSheetId();

		List<Object> objs = holder.getObjs();
		switch (sheetType) {
		case LoadConst.SHEET_TYPE_TECH_CHOICE: {
			row.setSkill((String) objs.get(0));
			row.setTitle((String) objs.get(1));
			List<String> options = new ArrayList<String>();
			for (int i = 2; i < 2 + 5; i++)
				options.add((String) objs.get(i));
			row.setOptions(options);
			row.setCorrectOptions((String) objs.get(7));
			row.setSuggestSeconds(Integer.toString((Integer) objs.get(8)));
			row.setLevel((String) objs.get(9));
			break;
		}
		case LoadConst.SHEET_TYPE_PROGRAM: {
			row.setTitle((String) objs.get(0));
			row.setRefAnswer((String) objs.get(1));
			row.setSuggestMinutes(Integer.toString((Integer) objs.get(2)));
			row.setMode((String) objs.get(3));
			row.setLevel((String) objs.get(4));
			break;
		}
		case LoadConst.SHEET_TYPE_TECH_ESSAY:
		case LoadConst.SHEET_TYPE_INTEL_ESSAY: {
			row.setTitle((String) objs.get(0));
			row.setRefAnswer((String) objs.get(1));
			row.setSuggestMinutes(Integer.toString((Integer) objs.get(2)));
			row.setLevel((String) objs.get(3));
			break;
		}
		case LoadConst.SHEET_TYPE_INTEL_CHOICE:
			row.setTitle((String) objs.get(0));
			List<String> options = new ArrayList<String>();
			for (int i = 1; i < 1 + 5; i++)
				options.add((String) objs.get(i));
			row.setOptions(options);
			row.setCorrectOptions((String) objs.get(6));
			row.setExplainReqired((String) objs.get(7));
			row.setRefExplain((String) objs.get(8));
			row.setSuggestSeconds(Integer.toString((Integer) objs.get(9)));
			row.setLevel((String) objs.get(10));
			break;
		case LoadConst.SHEET_TYPE_INTERVIEW:
			row.setTitle((String) objs.get(1));
			row.setSuggestMinutes(Integer.toString((Integer) objs.get(2)));
			break;
		default:
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("题目类型不正确，questionId=" + questionId);
			return response;
		}

		response.setSheetType(sheetType);
		response.setRow(row);
		return response;
	}

	@Override
	public GetGroupResponse getGroup(long groupId) {
		GetGroupResponse response = new GetGroupResponse();
		List<LoadRowResponse> rows = new ArrayList<LoadRowResponse>();

		QbQuestion qbGroup = qbQuestionDao.getEntity(groupId);
		if (qbGroup == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到对应的题组，groupId=" + groupId);
			return response;
		}

		if (qbGroup.getSubAsks() == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("题组中没有题，groupId=" + groupId);
			return response;
		}

		String[] fields = qbGroup.getSubAsks().split(",");
		if (fields == null || fields.length == 0) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("题组中没有题，groupId=" + groupId);
			return response;
		}

		LoadGroup loadGroup = new LoadGroup(LoadConst.TYPE_SUCCESS,
				qbGroup.getQuestionDesc());
		List<QbQuestionInfo> questionList = new ArrayList<QbQuestionInfo>();
		for (String field : fields) {
			long questionId = Long.parseLong(field);

			QbQuestion qbQuestion = qbQuestionDao.getEntity(questionId);
			if (qbQuestion == null) {
				response.setErrorCode(BaseResponse.ENOENT);
				response.setErrorDesc("找不到对应的题组，questionId=" + questionId);
				return response;
			}

			// 预定义的题
			if (qbQuestion.getPrebuilt() == 1) {
				response.setErrorCode(BaseResponse.EPERM);
				response.setErrorDesc("内部题组不允许读取，questionId=" + questionId);
				return response;
			}

			Holder holder = createList(qbQuestion, null);
			if (holder.getSheetId() == -1) {
				response.setErrorCode(BaseResponse.EPERM);
				response.setErrorDesc("题组类型不正确，questionId=" + questionId);
				return response;
			}

			if (LoadConst.SHEET_TYPE_EXTERNAL_MIN + holder.getSheetId() != LoadConst.SHEET_TYPE_INTERVIEW) {
				response.setErrorCode(BaseResponse.EPERM);
				response.setErrorDesc("题组类型不正确，questionId=" + questionId);
				return response;
			}

			LoadRowResponse row = new LoadRowResponse();
			row.setSheetType(LoadConst.SHEET_TYPE_INTERVIEW);
			row.setErrorType(LoadConst.TYPE_SUCCESS);
			if (qbQuestion.getHtml() != null && qbQuestion.getHtml() == 1)
				row.setHtml(true);

			List<Object> objs = holder.getObjs();
			row.setTitle((String) objs.get(1));
			row.setSuggestMinutes(Integer.toString((Integer) objs.get(2)));

			rows.add(row);
			QbQuestionInfo qbQuestionInfo = new QbQuestionInfo();
			qbQuestionInfo = qbQuestionDao.getQbQuestionInfo(qbQuestionInfo,
					qbQuestion, true);
			questionList.add(qbQuestionInfo);
		}

		loadGroup.setRows(rows);
		loadGroup.setQuestionList(questionList);
		response.setGroup(loadGroup);
		return response;
	}

	private List<Object> createChoice(QbQuestion qbQuestion) {
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qbQuestion
				.getQuestionId());
		QuestionContent questionContent = gson.fromJson(
				qbQuestionDetail.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();
		List<Object> values = new ArrayList<Object>();

		List<String> skills = qbQuestionSkillDao.getSkillIds(qbQuestion
				.getQuestionId());
		if (skills == null || skills.isEmpty()) {
			values.add(null);
		} else {
			values.add(qbSkillDao.getEntity(skills.get(0)).getSkillName());
		}

		values.add(questionConf.getTitle());

		for (String option : questionConf.getOptions())
			values.add(option);
		for (int i = questionConf.getOptions().size(); i < 5; i++)
			values.add(null);

		values.add(QuestionUtils.getAnswers(questionConf.getAnswer()));
		values.add(qbQuestion.getSuggestTime());

		values.add(GradeConst.getLevelString(qbQuestion.getDegree()));

		return values;
	}

	private List<Object> createProgram(QbQuestion qbQuestion) {
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qbQuestion
				.getQuestionId());
		QuestionContent questionContent = gson.fromJson(
				qbQuestionDetail.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();

		List<Object> values = new ArrayList<Object>();

		values.add(questionConf.getTitle());

		String key = GradeConst.REFERENCE_PREFIX
				+ questionConf.getMatrix().getFiles().get(0).getFilename();
		values.add(questionContent.getData().get(key));

		values.add(qbQuestion.getSuggestTime() / 60);
		values.add(qbQuestion.getProgramLanguage());
		values.add(GradeConst.getLevelString(qbQuestion.getDegree()));

		return values;
	}

	private List<Object> createIntelligenceChoice(QbQuestion qbQuestion) {
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qbQuestion
				.getQuestionId());
		QuestionContent questionContent = gson.fromJson(
				qbQuestionDetail.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();
		List<Object> values = new ArrayList<Object>();

		values.add(questionConf.getTitle());

		for (String option : questionConf.getOptions())
			values.add(option);
		for (int i = questionConf.getOptions().size(); i < 5; i++)
			values.add(null);

		values.add(QuestionUtils.getAnswers(questionConf.getAnswer()));

		switch (questionConf.getTypeInt()) {
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			values.add("是");
			break;
		default:
			values.add("否");
			break;
		}
		values.add(questionConf.getOptDesc());
		values.add(qbQuestion.getSuggestTime());
		values.add(GradeConst.getLevelString(qbQuestion.getDegree()));

		return values;
	}

	private List<Object> createIntelligenceEssay(QbQuestion qbQuestion) {
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qbQuestion
				.getQuestionId());
		QuestionContent questionContent = gson.fromJson(
				qbQuestionDetail.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();

		List<Object> values = new ArrayList<Object>();

		values.add(questionConf.getTitle());

		String key = GradeConst.REFERENCE_PREFIX
				+ questionConf.getMatrix().getFiles().get(0).getFilename();
		values.add(questionContent.getData().get(key));

		values.add(qbQuestion.getSuggestTime() / 60);
		values.add(GradeConst.getLevelString(qbQuestion.getDegree()));

		return values;
	}

	private List<Object> createInterview(String groupName, QbQuestion qbQuestion) {
		List<Object> values = new ArrayList<Object>();

		values.add(groupName);
		values.add(qbQuestion.getQuestionDesc());
		values.add(qbQuestion.getSuggestTime() / 60);

		return values;
	}

	private Holder createList(QbQuestion qbQuestion, String groupName) {
		int questionType = GradeConst.toQuestionTypeInt(qbQuestion
				.getQuestionType());

		List<Object> objs = null;
		int sheetId = -1;
		switch (qbQuestion.getCategory()) {
		case GradeConst.CATEGORY_TECHNOLOGY:
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE: // 技能类（选择题）
				objs = createChoice(qbQuestion);
				sheetId = LoadConst.SHEET_TYPE_TECH_CHOICE
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
				break;
			case GradeConst.QUESTION_TYPE_PROGRAM: // 编程题
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM: // 编程题
				objs = createProgram(qbQuestion);
				sheetId = LoadConst.SHEET_TYPE_PROGRAM
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
				break;
			case GradeConst.QUESTION_TYPE_ESSAY: // 技能类（问答题）
				objs = createIntelligenceEssay(qbQuestion);
				sheetId = LoadConst.SHEET_TYPE_TECH_ESSAY
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
				break;
			}
			break;
		case GradeConst.CATEGORY_INTELLIGENCE:
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS: // 选择题（考智力）
				objs = createIntelligenceChoice(qbQuestion);
				sheetId = LoadConst.SHEET_TYPE_INTEL_CHOICE
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
				break;
			case GradeConst.QUESTION_TYPE_ESSAY: // 技能类（问答题）
				objs = createIntelligenceEssay(qbQuestion);
				sheetId = LoadConst.SHEET_TYPE_INTEL_ESSAY
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
				break;
			}
			break;
		case GradeConst.CATEGORY_INTERVIEW: // 面试题
			if (questionType == GradeConst.QUESTION_TYPE_GROUP)
				break;

			objs = createInterview(groupName, qbQuestion);
			sheetId = LoadConst.SHEET_TYPE_INTERVIEW
					- LoadConst.SHEET_TYPE_EXTERNAL_MIN;
			break;
		}

		Holder holder = new Holder();
		holder.setObjs(objs);
		holder.setSheetId(sheetId);
		return holder;
	}

	private void createRow(Sheet sheet, List<Object> values, CellStyle cellStyle) {
		Row row = sheet.createRow(sheet.getLastRowNum() + 1);
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			Cell cell = row.createCell(i);
			if (cellStyle != null)
				cell.setCellStyle(cellStyle);
			else
				cell.getCellStyle().setWrapText(true);

			if (value instanceof String)
				cell.setCellValue((String) value);
			else if (value instanceof Double)
				cell.setCellValue((Double) value);
			else if (value instanceof Integer)
				cell.setCellValue((Integer) value);
		}
	}

	private void removeSheets(int category, Workbook workBook) {
		try{
		switch (category) {
		case GradeConst.CATEGORY_TECHNOLOGY:
			workBook.removeSheetAt(5);
			workBook.removeSheetAt(4);
			workBook.removeSheetAt(3);
			break;
		case GradeConst.CATEGORY_INTELLIGENCE:
			workBook.removeSheetAt(5);
			workBook.removeSheetAt(2);
			workBook.removeSheetAt(1);
			workBook.removeSheetAt(0);
			break;
		case GradeConst.CATEGORY_INTERVIEW:
			workBook.removeSheetAt(4);
			workBook.removeSheetAt(3);
			workBook.removeSheetAt(2);
			workBook.removeSheetAt(1);
			workBook.removeSheetAt(0);
			break;
		}

		workBook.setFirstVisibleTab(0);
		}
		catch(Exception e){
			logger.error("error remove sheet for category {} , e {} ", category, e.getMessage());
		}
	}

	@Override
	public ExportPositionResponse exportPosition(GetReportParam param)
			throws Exception {
		Position position = positionDao.getEntity(param.getPositionId());
		if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			return exportPositionGroup(param);
		}
		ExportPositionResponse response = new ExportPositionResponse();
//		List<CandidateTest> candidateTests = candidateTestDao
//				.getByPositionId(positionId);
		// add by lipan 2014年10月24日 根据条件导出excel
		List<CandidateTest> candidateTests = candidateTestDao.getCandidateTestList(param);
		List<GetReportResponse> reports = new ArrayList<GetReportResponse>();
		List<PositionRow> positionRows = new ArrayList<PositionRow>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 排名相关的统计映射
		TreeMap<Double, Integer> rankMap = new TreeMap<Double, Integer>();
		TreeMap<Double, Integer> intelRankMap = new TreeMap<Double, Integer>();
		TreeMap<Double, Integer> interviewRankMap = new TreeMap<Double, Integer>();

		boolean gottonInterviewMeta = false; // 是否获取了面试的元数据
		List<String> extInfos = new ArrayList<String>(); // 用户扩展信息，按照职位扩展信息中的顺序排序
		List<String> interviewResults = new ArrayList<String>(); // 面试结果，按照面试信息中的顺序排序
		List<String> interviewScores = new ArrayList<String>(); // 技能问答题分项，按照面试信息中的顺序排序
		Set<String> choiceSet = new TreeSet<String>(); // 选择题技能，按照字母顺序排序
		Set<String> programSet = new TreeSet<String>(); // 编程题分项，按照字母顺序排序
		Set<String> techEssaySet = new TreeSet<String>(); // 选择题技能，按照字母顺序排序
		Map<String, String> interviewMap = new HashMap<String, String>(); // 面试项信息映射（infoId
																			// =>
																			// infoName）

		// 加载职位相关的扩展信息
//		Position position = positionDao.getEntity(param.getPositionId());
		List<PositionInfoExt> positionInfoExts = positionInfoExtDao.getList(
				position.getEmployerId(), param.getPositionId());
		if (positionInfoExts == null || positionInfoExts.isEmpty()) {
			positionInfoExts = positionInfoExtDao.getList(
					position.getEmployerId(), -1);
		}

		for (PositionInfoExt positionInfoExt : positionInfoExts) {
			String infoId = positionInfoExt.getId().getInfoId();
			if (infoId.equals("FULL_NAME") || infoId.equals("EMAIL")
					|| infoId.equals("PHONE"))
				continue;

			extInfos.add(positionInfoExt.getInfoName());
		}

		for (CandidateTest candidateTest : candidateTests) {
			GetReportResponse report = reportRetrieveService
					.getReport(candidateTest.getTestId());

			reports.add(report);

			Summary summary = report.getSummary();
			if (summary.getSkills() != null)
				choiceSet.addAll(report.getSummary().getSkills());
		}

		for (int i = 0; i < candidateTests.size(); i++) {
			CandidateTest candidateTest = candidateTests.get(i);
			GetReportResponse report = reports.get(i);
			PositionRow positionRow = new PositionRow();
			positionRows.add(positionRow);

			// 设置用户常规信息
			Summary summary = report.getSummary();
			positionRow.setName(summary.getName());
			positionRow.setEmail(summary.getEmail());

			// 设置用户扩展信息
			List<UserInfo> userInfos = summary.getInfos();
			if (userInfos != null) {
				// 设置用户扩展信息
				for (UserInfo userInfo : userInfos) {
					String infoName = userInfo.getKey();
					if (infoName.equals("手机号码")) {
						positionRow.setPhone(userInfo.getValue());
						continue;
					}

					positionRow.getExtInfoMap().put(infoName,
							userInfo.getValue());
				}
			}

			switch (candidateTest.getTestResult()) {
			case 0: // 待处理
				positionRow.setStateForSort(PositionRow.STATE_PENDING);
				positionRow.setState("待处理");
				break;
			case 1: // 已推荐
				positionRow.setStateForSort(PositionRow.STATE_PASS);
				positionRow.setState("已推荐");
				break;
			case 2: // 已淘汰
				positionRow.setStateForSort(PositionRow.STATE_REJECT);
				positionRow.setState("已淘汰");
				break;
			}

			// 设置得分
			positionRow.setScore(summary.getScore());

			// 设置总分排名统计
			saveRank(rankMap, positionRow.getScore());

			// 设置选择题技能得分项
			if (summary.getSkills() != null) {
				Map<String, Double> choiceMap = positionRow.getChoiceMap();
				for (int j = 0; j < summary.getSkills().size(); j++) {
					choiceMap.put(summary.getSkills().get(j), summary
							.getSkillScores().get(j));
				}
			}

			// 设置编程题、问答题、智力题等的得分
			Map<String, ScorePair> programMap = new HashMap<String, ScorePair>();
			Map<String, ScorePair> techEssayMap = new HashMap<String, ScorePair>();
			double intelScore = 0.0;
			int intelCount = 0;
			double intelChoiceScore = 0.0;
			int intelChoiceCount = 0;
			double intelEssayScore = 0.0;
			int intelEssayCount = 0;

			List<Part> parts = report.getParts();
			if (parts != null) {
				for (Part part : parts) {
					List<PartItem> partItems = part.getPartItems();

					for (PartItem partItem : partItems) {
						switch (part.getAnchor()) {
						case IReportConfig.REPORT_COLUMN_PROGRAM:
							handlePartItem(programMap, programSet, partItem);
							break;
						case IReportConfig.REPORT_COLUMN_TECH_ESSAY:
							handlePartItem(techEssayMap, techEssaySet, partItem);
							break;
						case IReportConfig.REPORT_COLUMN_INTELLIGENCE:
							if (partItem.getScore() == null)
								break;

							intelScore += partItem.getScore();
							intelCount++;

							switch (partItem.getQuestionType()) {
							case GradeConst.QUESTION_TYPE_S_CHOICE:
							case GradeConst.QUESTION_TYPE_M_CHOICE:
							case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
							case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
								intelChoiceScore += partItem.getScore();
								intelChoiceCount++;
								break;
							default:
								intelEssayScore += partItem.getScore();
								intelEssayCount++;
								break;
							}
							break;
						}
					}
				}
			}

			// 设置编程分数
			calcScore(positionRow.getProgramMap(), programMap);

			// 设置技术问答分数
			calcScore(positionRow.getTechEssayMap(), techEssayMap);

			// 智力总得分
			if (intelCount > 0) {
				positionRow.setIntelScore(MathUtils.round(intelScore
						/ intelCount, 1));
			}

			// 智力排名
			saveRank(intelRankMap, positionRow.getIntelScore());

			// 智力选择题得分
			if (intelChoiceCount > 0) {
				positionRow.setIntelChoiceScore(MathUtils.round(
						intelChoiceScore / intelChoiceCount, 1));
			}

			// 智力问答题得分
			if (intelEssayCount > 0) {
				positionRow.setIntelEssayScore(MathUtils.round(intelEssayScore
						/ intelEssayCount, 1));
			}

			// 面试结果
			Map<String, String> interviewResultMap = positionRow
					.getInterviewResultMap();
			Map<String, Double> interviewScoreMap = positionRow
					.getInterviewScoreMap();

			double interviewScore = 0.0;
			int interviewCount = 0;
			Interview interview = report.getInterview();
			if (interview != null) {
				if (!gottonInterviewMeta) {
					gottonInterviewMeta = true;

					InterviewInfo interviewInfo = interview.getInterviewInfo();
					for (InterviewInfo.Group group : interviewInfo.getGroups()) {
						if (group.getId().equals(GradeConst.INTERVIEW_ITEM)) {
							for (InterviewInfo.Item item : group.getItems()) {
								interviewScores.add(item.getName());
								interviewMap.put(item.getId(), item.getName());
							}
						} else {
							for (InterviewInfo.Item item : group.getItems())
								interviewResults.add(item.getName());
						}
					}
				}

				List<InterviewItem> items = interview.getItems();
				if (items != null) {
					for (InterviewItem item : items) {
						if (item.getRealValue() == null)
							continue;

						String infoName = interviewMap.get(item.getInfoId());
						if (infoName == null)
							continue;

						if (item.getGroupId().equals(GradeConst.INTERVIEW_ITEM)) {
							double value = Double.parseDouble(item
									.getRealValue());
							interviewScoreMap.put(infoName, value);
							interviewScore += value;
							interviewCount++;
						} else {
							interviewResultMap.put(infoName,
									item.getRealValue());
						}
					}
				}
			}

			// 面试得分
			if (interviewCount > 0) {
				positionRow.setInterviewScore(MathUtils.round(interviewScore
						/ interviewCount, 1));
			}

			// 面试排名
			saveRank(interviewRankMap, positionRow.getInterviewScore());

			// 设置常规信息
			long duration = (report.getEndTime() - report.getBeginTime()) / 1000;
			positionRow.setDuration((duration / 60) + "分" + (duration % 60)
					+ "秒");
			positionRow
					.setBeginTime(sdf.format(new Date(report.getBeginTime())));
			positionRow.setEndTime(sdf.format(new Date(report.getEndTime())));
		}

		// 设置排名
		setRank(positionRows, rankMap, intelRankMap, interviewRankMap);

		// 排序
		Collections.sort(positionRows);

		// 输出Excel
		String title = position.getPositionName().replace("/", "-");
		if (title.length() > 29)
			title = title.substring(0, 29) + "报告";
		else
			title += "报告";
		InputStream in = null;
		try {
			Workbook workBook;
			in = ExportExcel.class.getResourceAsStream("/测评模板.xls");
			workBook = new HSSFWorkbook(in);
			Sheet template = workBook.getSheetAt(0);
			Sheet sheet = workBook.createSheet(title);
			int rownum = 2;

			boolean containsIntel = false; // 是否有智力题
			boolean containsInterviewResult = false; // 是否有面试结果
			boolean containsInterviewScore = false; // 是否有面试得分
			boolean containsExtInfo = false; // 是否有扩展信息

			for (PositionRow positionRow : positionRows) {
				if (positionRow.getIntelScore() != null)
					containsIntel = true;
				else if (!positionRow.getInterviewResultMap().isEmpty())
					containsInterviewResult = true;
				else if (positionRow.getInterviewScore() != null)
					containsInterviewScore = true;
				if (!positionRow.getExtInfoMap().isEmpty())
					containsExtInfo = true;
			}

			Row templateRow1 = template.getRow(0);
			Row templateRow2 = template.getRow(1);
			Row row1 = sheet.createRow(0);
			Row row2 = sheet.createRow(1);
			row1.setHeight(templateRow1.getHeight());
			row2.setHeight(templateRow2.getHeight());
			if (templateRow1.getRowStyle() != null)
				row1.setRowStyle(templateRow1.getRowStyle());
			if (templateRow2.getRowStyle() != null)
				row2.setRowStyle(templateRow2.getRowStyle());

			int columnIdx = 0;
			for (int i = TEMPLATE_INDEX_MIN; i <= TEMPLATE_INDEX_MAX; i++) {
				switch (i) {
				case TEMPLATE_INDEX_NAME:
				case TEMPLATE_INDEX_EMAIL:
				case TEMPLATE_INDEX_PHONE:
				case TEMPLATE_INDEX_STATE:
				case TEMPLATE_INDEX_SCORE:
				case TEMPLATE_INDEX_RANK:
				case TEMPLATE_INDEX_DURATION:
				case TEMPLATE_INDEX_BEGIN_TIME:
				case TEMPLATE_INDEX_END_TIME: {
					sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
					copyCell(templateRow1, row1, i, columnIdx);
					copyCell(templateRow2, row2, i, columnIdx);
					CellRangeAddress region = new CellRangeAddress(0, 1,
							columnIdx, columnIdx);
					sheet.addMergedRegion(region);
					columnIdx++;
					break;
				}
				case TEMPLATE_INDEX_INTERVIEW_RESULT:
					if (!containsInterviewResult)
						break;

					columnIdx = addExtensibleHeader(
							interviewResults.iterator(), template, sheet,
							templateRow1, templateRow2, row1, row2, i,
							columnIdx);
					break;
				case TEMPLATE_INDEX_CHOICE:
					columnIdx = addExtensibleHeader(choiceSet.iterator(),
							template, sheet, templateRow1, templateRow2, row1,
							row2, i, columnIdx);
					break;
				case TEMPLATE_INDEX_PROGRAM:
					columnIdx = addExtensibleHeader(programSet.iterator(),
							template, sheet, templateRow1, templateRow2, row1,
							row2, i, columnIdx);
					break;
				case TEMPLATE_INDEX_TECH_ESSAY:
					columnIdx = addExtensibleHeader(techEssaySet.iterator(),
							template, sheet, templateRow1, templateRow2, row1,
							row2, i, columnIdx);
					break;
				case TEMPLATE_INDEX_INTEL_SCORE:
				case TEMPLATE_INDEX_INTEL_RANK:
				case TEMPLATE_INDEX_INTEL_CHOICE:
				case TEMPLATE_INDEX_INTEL_ESSAY: {
					if (!containsIntel)
						break;

					sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
					copyCell(templateRow1, row1, i, columnIdx);
					copyCell(templateRow2, row2, i, columnIdx);
					CellRangeAddress region = new CellRangeAddress(0, 1,
							columnIdx, columnIdx);
					sheet.addMergedRegion(region);
					columnIdx++;
					break;
				}
				case TEMPLATE_INDEX_INTERVIEW_SCORE:
				case TEMPLATE_INDEX_INTERVIEW_RANK: {
					if (!containsInterviewScore)
						break;

					sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
					copyCell(templateRow1, row1, i, columnIdx);
					copyCell(templateRow2, row2, i, columnIdx);
					CellRangeAddress region = new CellRangeAddress(0, 1,
							columnIdx, columnIdx);
					sheet.addMergedRegion(region);
					columnIdx++;
					break;
				}
				case TEMPLATE_INDEX_INTERVIEW_ITEM:
					if (!containsInterviewScore)
						break;

					columnIdx = addExtensibleHeader(interviewScores.iterator(),
							template, sheet, templateRow1, templateRow2, row1,
							row2, i, columnIdx);
					break;
				case TEMPLATE_INDEX_EXT_INFO:
					if (!containsExtInfo)
						break;

					columnIdx = addExtensibleHeader(extInfos.iterator(),
							template, sheet, templateRow1, templateRow2, row1,
							row2, i, columnIdx);
					break;
				default:
					break;
				}
			}

			for (PositionRow positionRow : positionRows) {
				Row row = sheet.createRow(rownum++);
				List<Object> values = new ArrayList<Object>();

				values.add(positionRow.getName());
				values.add(positionRow.getEmail());
				values.add(positionRow.getPhone());
				values.add(positionRow.getState());

				if (containsInterviewResult) {
					for (String key : interviewResults) {
						values.add(positionRow.getInterviewResultMap().get(key));
					}
				}

				values.add(positionRow.getScore() * 100
						/ GradeConst.SCORE_SCALE);
				values.add(positionRow.getRank());

				if (!choiceSet.isEmpty()) {
					for (String key : choiceSet) {
						Double value = positionRow.getChoiceMap().get(key);
						if (value != null)
							values.add(value * 100 / GradeConst.SCORE_SCALE);
						else
							values.add(null);
					}
				}

				if (!programSet.isEmpty()) {
					for (String key : programSet) {
						Double value = positionRow.getProgramMap().get(key);
						if (value != null)
							values.add(value * 100 / GradeConst.SCORE_SCALE);
						else
							values.add(null);
					}
				}

				if (!techEssaySet.isEmpty()) {
					for (String key : techEssaySet) {
						Double value = positionRow.getTechEssayMap().get(key);
						if (value != null)
							values.add(value * 100 / GradeConst.SCORE_SCALE);
						else
							values.add(null);
					}
				}

				if (containsIntel) {
					if (positionRow.getInterviewScore() != null) {
						values.add(positionRow.getIntelScore() * 100
								/ GradeConst.SCORE_SCALE);
					} else {
						values.add(null);
					}
					values.add(positionRow.getIntelRank());
					if (positionRow.getIntelChoiceScore() != null) {
						values.add(positionRow.getIntelChoiceScore() * 100
								/ GradeConst.SCORE_SCALE);
					} else {
						values.add(null);
					}
					if (positionRow.getIntelEssayScore() != null) {
						values.add(positionRow.getIntelEssayScore() * 100
								/ GradeConst.SCORE_SCALE);
					} else {
						values.add(null);
					}
				}

				if (containsInterviewScore) {
					values.add(positionRow.getInterviewScore());
					values.add(positionRow.getInterviewRank());

					for (String key : interviewScores) {
						values.add(positionRow.getInterviewScoreMap().get(key));
					}
				}

				if (containsExtInfo) {
					for (String key : extInfos) {
						values.add(positionRow.getExtInfoMap().get(key));
					}
				}

				values.add(positionRow.getDuration());
				values.add(positionRow.getBeginTime());
				values.add(positionRow.getEndTime());

				for (int i = 0; i < values.size(); i++) {
					Object value = values.get(i);
					Cell cell = row.createCell(i);
					cell.getCellStyle().setWrapText(true);

					if (value instanceof String) {
						try {
							cell.setCellValue(Double
									.parseDouble((String) value));
						} catch (NumberFormatException e) {
							cell.setCellValue((String) value);
						}
					} else if (value instanceof Double) {
						cell.setCellValue((Double) value);
					} else if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					}
				}
			}

			// 删除模板
			workBook.removeSheetAt(0);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);

			response.setTitle(title);
			response.setData(out.toByteArray());
			return response;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}
	public ExportPositionResponse exportPositionGroup(GetReportParam param) throws Exception{
		int positionId = param.getPositionId();
		Map<String,Map<Integer,PositionGroupRow>> posIdsToGroupRows = new HashMap<String,Map<Integer,PositionGroupRow>>();//测评ids对应的报告信息
		initPosIdsToGroupRowsMap(posIdsToGroupRows, positionId); //初始化必考选考组合
		List<SchoolCandidateReport> reports = schoolReportDao.getAllSchoolReportList(param.getEmployerId(),param.getPositionId(),null,
				null);
		List<Integer> hasRankPositionIds = new ArrayList<Integer>();//已经处理了排序的测评id
		Map<Integer,PositionToConfigInfo> positionToConfigs = new HashMap<Integer,PositionToConfigInfo>();//每一个测评的配置信息
		Map<Long,PositionGrpChildRow>  testIdToChildRows = new HashMap<Long, PositionGrpChildRow>();//面试题报告
		PositionToConfigInfo interviewConfig = new PositionToConfigInfo();//面试题配置信息
		Set<String> keys = posIdsToGroupRows.keySet();
			for(String key : keys){
				for(SchoolCandidateReport candidateReport : reports){
			   String realPositionId = candidateReport.getPositionId() +"";
				List<String> arr = Arrays.asList(key.split("_"));
				if(arr.contains(realPositionId)){
					Map<Integer,PositionGroupRow> candidateToGroupRows = posIdsToGroupRows.get(key);
					PositionGroupRow  groupRow = candidateToGroupRows.get(candidateReport.getCandidateId());
					if(groupRow == null){
					    groupRow = new PositionGroupRow();
						groupRow.setEmail(candidateReport.getCandidateEmail());
						groupRow.setName(candidateReport.getCandidateName());
						Map<String, String> infos = candidateInfoExtDao.getMap(candidateReport.getCandidateId());
						groupRow.setPhone(infos.get(Constants.PHONE));
						candidateToGroupRows.put(candidateReport.getCandidateId(), groupRow);
					}
					
					PositionGrpChildRow interViewRow = testIdToChildRows.get(candidateReport.getTestId());
					if(interViewRow == null){
						 interViewRow  =new PositionGrpChildRow();  
						 interViewRow.setPositionId(Integer.valueOf(realPositionId));
						 interViewRow.setTestId(candidateReport.getTestId());
						 interViewRow = processPositionGrpChildRowForInterview(interViewRow,candidateReport,interviewConfig);
						 if(interViewRow != null){
							 interViewRow.setEmail(candidateReport.getCandidateEmail());
							 interViewRow.setName(candidateReport.getCandidateName());
							 Map<String, String> infos = candidateInfoExtDao.getMap(candidateReport.getCandidateId());
							 interViewRow.setPhone(infos.get(Constants.PHONE));
							 testIdToChildRows.put(candidateReport.getTestId(), interViewRow);
						 }
						
					}
					
					Map<Integer, PositionGrpChildRow> posIdToGrpCildRows = groupRow.getPositionToRows();
					PositionGrpChildRow positionGrpChildRow = posIdToGrpCildRows.get(Integer.valueOf(realPositionId));
					if(positionGrpChildRow == null){
						positionGrpChildRow = new PositionGrpChildRow(); 
						positionGrpChildRow.setPositionId(Integer.valueOf(realPositionId));
						positionGrpChildRow.setTestId(candidateReport.getTestId());
						posIdToGrpCildRows.put(Integer.valueOf(realPositionId), positionGrpChildRow);
					}else{
						if(positionGrpChildRow.getScore() > candidateReport.getGetScore()){
							logger.debug("have the another report for positionId {} , candidateId {} ", realPositionId,candidateReport.getCandidateId());
							continue;
						}/*else{
							PositionToConfigInfo positionToConfig = positionToConfigs.get(Integer.valueOf(realPositionId));
							removeRankGroup(positionToConfig.rankMap,positionGrpChildRow.getScore());
							removeRankGroup(positionToConfig.intelRankMap,positionGrpChildRow.getScore());
							removeRankGroup(positionToConfig.interviewRankMap,positionGrpChildRow.getScore());

						}*/
					}
					processPositionGrpChildRow(groupRow,positionGrpChildRow, candidateReport,positionToConfigs,hasRankPositionIds);//处理某一个测评id的报告数据
				}
			}
				for(String strPosId : key.split("_")){
					if(!hasRankPositionIds.contains(Integer.valueOf(strPosId)))
					hasRankPositionIds.add(Integer.valueOf(strPosId));
				}
		}
		
		// 设置排名
		setRankGroup(posIdsToGroupRows,positionToConfigs);

		// 排序
		/*Collections.sort(positionRows);*/

		return writeToExcel(positionId,posIdsToGroupRows,positionToConfigs,testIdToChildRows,interviewConfig);
	}
	
	private void setRankGroup(Map<String,Map<Integer,PositionGroupRow>> posIdsToGroupRows,Map<Integer,PositionToConfigInfo> positionToConfigs){

		for(PositionToConfigInfo config : positionToConfigs.values()){
			for (TreeMap<Double, Integer> map : new TreeMap[] { config.rankMap,
					config.intelRankMap }) {
				int rank = 1;
				for (Entry<Double, Integer> entry : map.descendingMap().entrySet()) {
					int value = entry.getValue();
					entry.setValue(rank);
					rank += value;
				}
			}
		}
		
		Set<String> keys = posIdsToGroupRows.keySet();
		for(String key : keys){
			List<String> arr = Arrays.asList(key.split("_"));
			   Map<Integer,PositionGroupRow> candidateToGroupRows = posIdsToGroupRows.get(key);
				for(PositionGroupRow  grpRow : candidateToGroupRows.values()){
					for(PositionGrpChildRow row : grpRow.getPositionToRows().values()){
						row.setRank(positionToConfigs.get(row.getPositionId()).rankMap.get(row.getScore()));
						if (row.getIntelScore() != null) {
							row.setIntelRank(positionToConfigs.get(row.getPositionId()).intelRankMap.get(row.getIntelScore()));
						}
					}
				}
		}
		
	}

	private ExportPositionResponse writeToExcel(int positionId,
			Map<String, Map<Integer, PositionGroupRow>> posIdsToGroupRows,
			Map<Integer, PositionToConfigInfo> positionToConfigs, Map<Long, PositionGrpChildRow> testIdToChildRows,PositionToConfigInfo interviewConfig)
			throws Exception {
		ExportPositionResponse response = new ExportPositionResponse();
		Position position = positionDao.getEntity(positionId);
		String title = position.getPositionName().replace("/", "-");
		if (title.length() > 29)
			title = title.substring(0, 29) + "报告";
		else
			title += "报告";
		InputStream in = null;
		try {
			List<String> extInfos = new ArrayList<String>(); // 用户扩展信息，按照职位扩展信息中的顺序排序
			List<PositionInfoExt> positionInfoExts = positionInfoExtDao.getList(
					position.getEmployerId(), position.getPositionId());
			if (positionInfoExts == null || positionInfoExts.isEmpty()) {
				positionInfoExts = positionInfoExtDao.getList(
						position.getEmployerId(), -1);
			}

			for (PositionInfoExt positionInfoExt : positionInfoExts) {
				String infoId = positionInfoExt.getId().getInfoId();
				if (infoId.equals("FULL_NAME") || infoId.equals("EMAIL")
						|| infoId.equals("PHONE"))
					continue;

				extInfos.add(positionInfoExt.getInfoName());
			}
			boolean containsExtInfo = false; // 是否有扩展信息
			
			
			Workbook workBook;
			in = ExportExcel.class.getResourceAsStream("/测评组报告模板.xls");
			workBook = new HSSFWorkbook(in);
			List<String> sheetNames = new ArrayList<String>();
			int sheetNum = 0;
			for(String key : posIdsToGroupRows.keySet()){
				sheetNum++;
				Sheet template = workBook.getSheetAt(0);
				
				int rownum = 3;
				Integer sortPositionId = null;
				StringBuffer sb = new StringBuffer();
				for(String posIdStr : key.split("_")){
					if(sortPositionId == null){
						sortPositionId = Integer.valueOf(posIdStr);
					}
					Position pos = positionDao.getEntity(Integer.valueOf(posIdStr));
					sb.append(pos.getPositionName().replace("/", "-")).append(",");
				}
				sb.deleteCharAt(sb.length() -1);
				String sheetName = sb.toString();
				if (sheetName.length() > 29)
					sheetName = sheetName.substring(0, 29) + "报告";
				else
					sheetName += "报告";
				if(sheetNames.contains(sheetName)){
					if(sheetName.length() == 29){
					    sheetName = sheetName.substring(0, sheetName.length() - 2) + sheetNum;
					}else{
						sheetName = sheetName + sheetNum;
					}
				}
				sheetNames.add(sheetName);
				Sheet sheet = workBook.createSheet(sheetName);
				Map<Integer,PositionGroupRow> candidateToGroupRows = posIdsToGroupRows.get(key);
				
				for(PositionGroupRow grpRow : candidateToGroupRows.values()){
					Map<Integer, PositionGrpChildRow>  positionToRows =	grpRow.getPositionToRows();
					  for(String posIdKey : key.split("_")){
						  PositionGrpChildRow positionGrpChildRow = positionToRows.get(Integer.valueOf(posIdKey));
						  if(positionGrpChildRow != null){
							  PositionToConfigInfo positionToConfig = positionToConfigs.get(Integer.valueOf(posIdKey));
							  if (positionGrpChildRow.getIntelScore() != null)
								  positionToConfig.containsIntel = true;
								else if (!positionGrpChildRow.getInterviewResultMap().isEmpty())
									positionToConfig.containsInterviewResult = true;
								else if (positionGrpChildRow.getInterviewScore() != null)
									positionToConfig.containsInterviewScore = true;
								if (!positionGrpChildRow.getExtInfoMap().isEmpty()){
									containsExtInfo = true;
									positionToConfig.containsExtInfo = true;
								}
						  }
					  }
				}

					Row templateRow1 = template.getRow(0);
					Row templateRow2 = template.getRow(1);
					Row templateRow3 = template.getRow(2);
					Row row1 = sheet.createRow(0);
					Row row2 = sheet.createRow(1);
					Row row3 = sheet.createRow(2);
					row1.setHeight(templateRow1.getHeight());
					row2.setHeight(templateRow2.getHeight());
					row3.setHeight(templateRow3.getHeight());
					if (templateRow1.getRowStyle() != null)
						row1.setRowStyle(templateRow1.getRowStyle());
					if (templateRow2.getRowStyle() != null)
						row2.setRowStyle(templateRow2.getRowStyle());
					if (templateRow3.getRowStyle() != null)
						row2.setRowStyle(templateRow3.getRowStyle());

					int columnIdx = 0;
					for (int i = GROUP_TEMPLATE_INDEX_MIN; i <= GROUP_TEMPLATE_INDEX_EXT_INFO; i++) {
						switch (i) {
						case GROUP_TEMPLATE_INDEX_NAME:
						case GROUP_TEMPLATE_INDEX_EMAIL:
						case GROUP_TEMPLATE_INDEX_PHONE:
						 {
							sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
							copyCell(templateRow1, row1, i, columnIdx);
							copyCell(templateRow2, row2, i, columnIdx);
							copyCell(templateRow2, row3, i, columnIdx);
							CellRangeAddress region = new CellRangeAddress(0, 2,
									columnIdx, columnIdx);
							sheet.addMergedRegion(region);
							columnIdx++;
							break;
						}
						case GROUP_TEMPLATE_INDEX_EXT_INFO:
							if (!containsExtInfo)
								break;

							columnIdx = addExtensibleHeaderGroupNoPaper(extInfos.iterator(),
									template, sheet, templateRow1, templateRow2,templateRow3, row1,
									row2,row3, i, columnIdx);//前两行合并
							break;
						default:
							break;
						}
					}

					for(String posIdKey : key.split("_")){
						  PositionToConfigInfo positionToConfig = positionToConfigs.get(Integer.valueOf(posIdKey));
						 /* if(positionToConfig == null){
							  logger.debug("the position has not report {} ", posIdKey);
							  continue;
						  }*/
						  Position realPosition = positionDao.getEntity(Integer.valueOf(posIdKey));
						  int  positionStartColumn = columnIdx;
						for (int i = GROUP_TEMPLATE_INDEX_SCORE; i <= GROUP_TEMPLATE_INDEX_MAX; i++) {
							switch (i) {
							case GROUP_TEMPLATE_INDEX_SCORE:
							case GROUP_TEMPLATE_INDEX_RANK:
							case GROUP_TEMPLATE_INDEX_DURATION:
							case GROUP_TEMPLATE_INDEX_BEGIN_TIME:
							case GROUP_TEMPLATE_INDEX_END_TIME: {
								sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
								copyCell(templateRow1, row1, i, columnIdx,realPosition.getPositionName());
								copyCell(templateRow1, row2, i, columnIdx);
								copyCell(templateRow2, row3, i, columnIdx);
								CellRangeAddress region = new CellRangeAddress(1, 2,
										columnIdx, columnIdx);
								sheet.addMergedRegion(region);
								columnIdx++;
								break;
							}
							case GROUP_TEMPLATE_INDEX_CHOICE:
								if(positionToConfig != null)
								columnIdx = addExtensibleHeaderGroup(positionToConfig.choiceSet.iterator(),
										template, sheet, templateRow1, templateRow2,templateRow3, row1,
										row2,row3, i, columnIdx,realPosition.getPositionName());
								break;
							case GROUP_TEMPLATE_INDEX_PROGRAM:
								if(positionToConfig != null)
								columnIdx = addExtensibleHeaderGroup(positionToConfig.programSet.iterator(),
										template, sheet, templateRow1, templateRow2,templateRow3,  row1,
										row2,row3, i, columnIdx,realPosition.getPositionName());
								break;
							case GROUP_TEMPLATE_INDEX_TECH_ESSAY:
								if(positionToConfig != null)
								columnIdx = addExtensibleHeaderGroup(positionToConfig.techEssaySet.iterator(),
										template, sheet, templateRow1, templateRow2, templateRow3,row1,
										row2, row3,i, columnIdx,realPosition.getPositionName());
								break;
							case GROUP_TEMPLATE_INDEX_INTEL_SCORE:
							case GROUP_TEMPLATE_INDEX_INTEL_RANK:
							case GROUP_TEMPLATE_INDEX_INTEL_CHOICE:
							case GROUP_TEMPLATE_INDEX_INTEL_ESSAY: {
								if(positionToConfig == null){
									break;
								}
								if (!positionToConfig.containsIntel)
									break;

								sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
								copyCell(templateRow1, row1, i, columnIdx,realPosition.getPositionName());
								copyCell(templateRow1, row2, i, columnIdx);
								copyCell(templateRow2, row3, i, columnIdx);
								CellRangeAddress region = new CellRangeAddress(1, 2,
										columnIdx, columnIdx);
								sheet.addMergedRegion(region);
								columnIdx++;
								break;
							}
							default:
								break;
							}
						}
						
						CellRangeAddress region = new CellRangeAddress(0, 0,
								positionStartColumn, columnIdx - 1);
						sheet.addMergedRegion(region);
					}

				    List<PositionGroupRow> grpRows = new ArrayList<PositionGroupRow>();
				    grpRows.addAll(candidateToGroupRows.values());
				    final int sortId = sortPositionId;
				    Collections.sort(grpRows, new Comparator<PositionGroupRow>() {

						@Override
						public int compare(PositionGroupRow o1, PositionGroupRow o2) {
							 Map<Integer, PositionGrpChildRow>  map1 = o1.getPositionToRows();
							 Map<Integer, PositionGrpChildRow>  map2 = o2.getPositionToRows();
							 PositionGrpChildRow  row1 = map1.get(sortId);
							 PositionGrpChildRow  row2 = map2.get(sortId);
							 if(row1 == null){
								 return 1;
							 }
							 if(row2 == null){
								 return -1;
							 }
							 return Double.valueOf(row2.getScore()).compareTo(Double.valueOf(row1.getScore()));
						}
					});
					for(PositionGroupRow grpRow : grpRows) {
						Row row = sheet.createRow(rownum++);
						List<Object> values = new ArrayList<Object>();

						values.add(grpRow.getName());
						values.add(grpRow.getEmail());
						values.add(grpRow.getPhone());
//						values.add(grpRow.getState());
						
						if (containsExtInfo) {
							for (String keyTemp : extInfos) {
								values.add(grpRow.getExtInfoMap().get(keyTemp));//TODO
							}
						}

						for(String posIdKey : key.split("_")){
							 Map<Integer, PositionGrpChildRow>  positionToRows =	grpRow.getPositionToRows();
							  PositionGrpChildRow positionGrpChildRow = positionToRows.get(Integer.valueOf(posIdKey));
							  if(positionGrpChildRow == null){
								  logger.debug("not fond any report for posIdKey {} ", posIdKey);
								  values.add(null);
								  values.add(null);
							  }
							  else{
								  values.add(positionGrpChildRow.getScore() * 100
											/ GradeConst.SCORE_SCALE);
								  values.add(positionGrpChildRow.getRank());  
							  }
							 
								PositionToConfigInfo positionToConfig = positionToConfigs.get(Integer.valueOf(posIdKey));
								if (positionToConfig != null && !positionToConfig.choiceSet.isEmpty()) {
									for (String key1 : positionToConfig.choiceSet) {
										if(positionGrpChildRow != null){
											Double value = positionGrpChildRow.getChoiceMap().get(key1);
											if (value != null)
												values.add(value * 100 / GradeConst.SCORE_SCALE);
											else
												values.add(null);
										}else{
											values.add(null);
										}
										
									}
								}

								if (positionToConfig != null && !positionToConfig.programSet.isEmpty()) {
									for (String key1 : positionToConfig.programSet) {
										if(positionGrpChildRow != null){
											Double value = positionGrpChildRow.getProgramMap().get(key1);
											if (value != null)
												values.add(value * 100 / GradeConst.SCORE_SCALE);
											else
												values.add(null);
										}else{
											values.add(null);
										}
										
									}
								}

								if (positionToConfig != null && !positionToConfig.techEssaySet.isEmpty()) {
									for (String key1 : positionToConfig.techEssaySet) {
										if(positionGrpChildRow != null){
											Double value = positionGrpChildRow.getTechEssayMap().get(key1);
											if (value != null)
												values.add(value * 100 / GradeConst.SCORE_SCALE);
											else
												values.add(null);
										}else{
											values.add(null);
										}
										
									}
								}

								if (positionToConfig != null && positionToConfig.containsIntel) {
									if (positionGrpChildRow != null && positionGrpChildRow.getIntelScore()  != null) {
										values.add(positionGrpChildRow.getIntelScore() * 100
												/ GradeConst.SCORE_SCALE);
									} else {
										values.add(null);
									}
									if(positionGrpChildRow != null)
									    values.add(positionGrpChildRow.getIntelRank());
									else
										values.add(null);
									if (positionGrpChildRow != null && positionGrpChildRow.getIntelChoiceScore() != null) {
										values.add(positionGrpChildRow.getIntelChoiceScore() * 100
												/ GradeConst.SCORE_SCALE);
									} else {
										values.add(null);
									}
									if (positionGrpChildRow != null && positionGrpChildRow.getIntelEssayScore() != null) {
										values.add(positionGrpChildRow.getIntelEssayScore() * 100
												/ GradeConst.SCORE_SCALE);
									} else {
										values.add(null);
									}
								}


								 if(positionGrpChildRow == null){
									    values.add(null);
										values.add(null);
										values.add(null);
								 }else{
									   values.add(positionGrpChildRow.getDuration());
								 		values.add(positionGrpChildRow.getBeginTime());
										values.add(positionGrpChildRow.getEndTime()); 
								 }
								

								for (int i = 0; i < values.size(); i++) {
									Object value = values.get(i);
									Cell cell = row.createCell(i);
									cell.getCellStyle().setWrapText(true);

									if (value instanceof String) {
										try {
											cell.setCellValue(Double
													.parseDouble((String) value));
										} catch (NumberFormatException e) {
											cell.setCellValue((String) value);
										}
									} else if (value instanceof Double) {
										cell.setCellValue((Double) value);
									} else if (value instanceof Integer) {
										cell.setCellValue((Integer) value);
									}
								}
							}
							
							
						}
			}
			
			if(testIdToChildRows.size() > 0)
             writeInterview(interviewConfig, workBook, testIdToChildRows, positionId, in);
			// 删除模板
			workBook.removeSheetAt(0);
			workBook.removeSheetAt(0);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);

			response.setTitle(title);
			response.setData(out.toByteArray());
			return response;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}
	
	private void writeInterview(PositionToConfigInfo interviewConfig,Workbook workBook,Map<Long,PositionGrpChildRow>  testIdsToChildRows, Integer positionId,InputStream in)
	{
		Position position = positionDao.getEntity(positionId);
		try {
			List<String> extInfos = new ArrayList<String>(); // 用户扩展信息，按照职位扩展信息中的顺序排序
			List<PositionInfoExt> positionInfoExts = positionInfoExtDao.getList(
					position.getEmployerId(), position.getPositionId());
			if (positionInfoExts == null || positionInfoExts.isEmpty()) {
				positionInfoExts = positionInfoExtDao.getList(
						position.getEmployerId(), -1);
			}

			for (PositionInfoExt positionInfoExt : positionInfoExts) {
				String infoId = positionInfoExt.getId().getInfoId();
				if (infoId.equals("FULL_NAME") || infoId.equals("EMAIL")
						|| infoId.equals("PHONE"))
					continue;

				extInfos.add(positionInfoExt.getInfoName());
			}
			boolean containsExtInfo = false; // 是否有扩展信息
			boolean containsInterviewResult = false; // 是否有面试结果
			boolean containsInterviewScore = false; // 是否有面试得分
			Map<Integer,Integer> positionIdToColumn = new HashMap<Integer, Integer>();
			List<PositionRelation> positionRelations = positionRelationDao.getPositionRelationByPositionGroupId(positionId);

				Sheet template = workBook.getSheetAt(1);
				int rownum = 2;
				String sheetName = template.getSheetName();
				workBook.setSheetName(1, "tmp1234123");
				Sheet sheet = workBook.createSheet(sheetName);
				
				for(PositionGrpChildRow positionGrpChildRow : testIdsToChildRows.values()){
						  if(positionGrpChildRow!=null){
								if (!positionGrpChildRow.getInterviewResultMap().isEmpty())
									containsInterviewResult = true;
								if (positionGrpChildRow.getInterviewScore() != null)
									containsInterviewScore = true;
								if (!positionGrpChildRow.getExtInfoMap().isEmpty()){
									containsExtInfo = true;
								}
						  }
				}

					Row templateRow1 = template.getRow(0);
					Row templateRow2 = template.getRow(1);
					Row row1 = sheet.createRow(0);
					Row row2 = sheet.createRow(1);
					row1.setHeight(templateRow1.getHeight());
					row2.setHeight(templateRow2.getHeight());
					if (templateRow1.getRowStyle() != null)
						row1.setRowStyle(templateRow1.getRowStyle());
					if (templateRow2.getRowStyle() != null)
						row2.setRowStyle(templateRow2.getRowStyle());

					int columnIdx = 0;
					for (int i = GROUP_TEMPLATE_INDEX_MIN; i <= GROUP_TEMPLATE_INDEX_EXT_INFO; i++) {
						switch (i) {
						case GROUP_TEMPLATE_INDEX_NAME:
						case GROUP_TEMPLATE_INDEX_EMAIL:
						case GROUP_TEMPLATE_INDEX_PHONE:
						 {
							sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
							copyCell(templateRow1, row1, i, columnIdx);
							copyCell(templateRow2, row2, i, columnIdx);
							CellRangeAddress region = new CellRangeAddress(0, 1,
									columnIdx, columnIdx);
							sheet.addMergedRegion(region);
							columnIdx++;
							break;
						}
						case GROUP_TEMPLATE_INDEX_EXT_INFO:
							if (!containsExtInfo)
								break;

							columnIdx = addExtensibleHeader(extInfos.iterator(),
									template, sheet, templateRow1, templateRow2, row1,
									row2, i, columnIdx);
							break;
						default:
							break;
						}
					}
                    for(int j = 0; j < positionRelations.size(); j++){ //第5列开始试卷名称
                    	Position pr = positionDao.getEntity(positionRelations.get(j).getId().getPositionId());
                    	sheet.setColumnWidth(columnIdx, template.getColumnWidth(4));
						copyCell(templateRow1, row1, 4, columnIdx,pr.getPositionName());
						copyCell(templateRow2, row2, 4, columnIdx,pr.getPositionName());
						CellRangeAddress region = new CellRangeAddress(0, 1,
								columnIdx, columnIdx);
						sheet.addMergedRegion(region);
						positionIdToColumn.put(pr.getPositionId(), columnIdx);
						columnIdx++;
//						break;
                    }
                    for (int i = 5; i <= 7; i++) {
						switch (i) {
							case 5:
							case 6: {
								if (!containsInterviewScore)
									break;
					
								sheet.setColumnWidth(columnIdx, template.getColumnWidth(i));
								copyCell(templateRow1, row1, i, columnIdx);
								copyCell(templateRow2, row2, i, columnIdx);
								CellRangeAddress region = new CellRangeAddress(0, 1,
										columnIdx, columnIdx);
								sheet.addMergedRegion(region);
								columnIdx++;
								break;
							}
							case 7:
								if (!containsInterviewScore)
									break;
					
								columnIdx = addExtensibleHeader(interviewConfig.interviewScores.iterator(),
										template, sheet, templateRow1, templateRow2, row1,
										row2, i, columnIdx);
								break;
							default:
								break;
						}
                    }
								 
					for(PositionGrpChildRow positionGrpChildRow : testIdsToChildRows.values()) {
						Row row = sheet.createRow(rownum++);
						List<Object> values = new ArrayList<Object>();

						
						values.add(positionGrpChildRow.getName());
						values.add(positionGrpChildRow.getEmail());
						values.add(positionGrpChildRow.getPhone());
//						values.add(grpRow.getState());
						int dataColumn = 3;
						if (containsExtInfo) {
							for (String keyTemp : extInfos) {
								values.add(positionGrpChildRow.getExtInfoMap().get(keyTemp));//TODO
								dataColumn++;
							}
						}
						
						
						int columnIndex = positionIdToColumn.get(positionGrpChildRow.getPositionId());
						for(int i = 0; i < positionIdToColumn.size() ; i++){
							if(dataColumn == columnIndex){
								values.add(positionGrpChildRow.getScore());
							}else{
								values.add(null);
							}
							dataColumn++;
						}
						//写入各个试卷的分数   只有一个有 其他为0
						if (containsInterviewScore) {
							values.add(positionGrpChildRow.getInterviewScore());
							values.add(positionGrpChildRow.getInterviewRank());

							for (String key : interviewConfig.interviewScores) {
								values.add(positionGrpChildRow.getInterviewScoreMap().get(key));
							}
						}

							for (int i = 0; i < values.size(); i++) {
								Object value = values.get(i);
								Cell cell = row.createCell(i);
								cell.getCellStyle().setWrapText(true);
			
								if (value instanceof String) {
									try {
										cell.setCellValue(Double.parseDouble((String) value));
									} catch (NumberFormatException e) {
										cell.setCellValue((String) value);
									}
								} else if (value instanceof Double) {
									cell.setCellValue((Double) value);
								} else if (value instanceof Integer) {
									cell.setCellValue((Integer) value);
								}
							}
							
							
						}
		

		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	
	}
	private void processPositionGrpChildRow(PositionGroupRow grpRow,PositionGrpChildRow positionGrpChildRow, SchoolCandidateReport candidateReport,Map<Integer,PositionToConfigInfo> positionToConfigs,List<Integer> hasRankPositionIds) throws Exception{

		CandidateTest candidateTest = candidateTestDao.getEntity(candidateReport.getTestId());
		PositionToConfigInfo positionToConfig = positionToConfigs.get(candidateReport.getPositionId()); //配置信息
		if(positionToConfig == null){
			positionToConfig = new PositionToConfigInfo();
			positionToConfigs.put(candidateReport.getPositionId(), positionToConfig);
		}
		GetReportResponse report  = reportRetrieveService
				.getReport(candidateReport.getTestId());
//		PositionRow positionRow = new PositionRow();
//		positionRows.add(positionRow);

		// 设置用户常规信息
		Summary summary = report.getSummary();
//		positionRow.setName(summary.getName());
//		positionRow.setEmail(summary.getEmail());

		if (summary.getSkills() != null)
			positionToConfig.choiceSet.addAll(report.getSummary().getSkills());
		
		// 设置用户扩展信息
		List<UserInfo> userInfos = summary.getInfos();
		if (userInfos != null) {
			// 设置用户扩展信息
			for (UserInfo userInfo : userInfos) {
				String infoName = userInfo.getKey();
				if (infoName.equals("手机号码")) {
//					positionRow.setPhone(userInfo.getValue());
					continue;
				}

				grpRow.getExtInfoMap().put(infoName,
						userInfo.getValue());
				positionGrpChildRow.getExtInfoMap().put(infoName,
						userInfo.getValue());
			}
		}

		switch (candidateTest.getTestResult()) {
		case 0: // 待处理
			positionGrpChildRow.setStateForSort(PositionRow.STATE_PENDING);
			positionGrpChildRow.setState("待处理");
			break;
		case 1: // 已推荐
			positionGrpChildRow.setStateForSort(PositionRow.STATE_PASS);
			positionGrpChildRow.setState("已推荐");
			break;
		case 2: // 已淘汰
			positionGrpChildRow.setStateForSort(PositionRow.STATE_REJECT);
			positionGrpChildRow.setState("已淘汰");
			break;
		}

		// 设置得分
		positionGrpChildRow.setScore(summary.getScore());

		// 设置总分排名统计
		if(!hasRankPositionIds.contains(candidateReport.getPositionId()))
		    saveRank(positionToConfig.rankMap, positionGrpChildRow.getScore());

		// 设置选择题技能得分项
		if (summary.getSkills() != null) {
			Map<String, Double> choiceMap = positionGrpChildRow.getChoiceMap();
			for (int j = 0; j < summary.getSkills().size(); j++) {
				choiceMap.put(summary.getSkills().get(j), summary
						.getSkillScores().get(j));
			}
		}

		// 设置编程题、问答题、智力题等的得分
		Map<String, ScorePair> programMap = new HashMap<String, ScorePair>();
		Map<String, ScorePair> techEssayMap = new HashMap<String, ScorePair>();
		double intelScore = 0.0;
		int intelCount = 0;
		double intelChoiceScore = 0.0;
		int intelChoiceCount = 0;
		double intelEssayScore = 0.0;
		int intelEssayCount = 0;

		List<Part> parts = report.getParts();
		if (parts != null) {
			for (Part part : parts) {
				List<PartItem> partItems = part.getPartItems();

				for (PartItem partItem : partItems) {
					switch (part.getAnchor()) {
					case IReportConfig.REPORT_COLUMN_PROGRAM:
						handlePartItem(programMap, positionToConfig.programSet, partItem);
						break;
					case IReportConfig.REPORT_COLUMN_TECH_ESSAY:
						handlePartItem(techEssayMap, positionToConfig.techEssaySet, partItem);
						break;
					case IReportConfig.REPORT_COLUMN_INTELLIGENCE:
						if (partItem.getScore() == null)
							break;

						intelScore += partItem.getScore();
						intelCount++;

						switch (partItem.getQuestionType()) {
						case GradeConst.QUESTION_TYPE_S_CHOICE:
						case GradeConst.QUESTION_TYPE_M_CHOICE:
						case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
						case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
							intelChoiceScore += partItem.getScore();
							intelChoiceCount++;
							break;
						default:
							intelEssayScore += partItem.getScore();
							intelEssayCount++;
							break;
						}
						break;
					}
				}
			}
		}

		// 设置编程分数
		calcScore(positionGrpChildRow.getProgramMap(), programMap);

		// 设置技术问答分数
		calcScore(positionGrpChildRow.getTechEssayMap(), techEssayMap);

		// 智力总得分
		if (intelCount > 0) {
			positionGrpChildRow.setIntelScore(MathUtils.round(intelScore
					/ intelCount, 1));
		}

		// 智力排名
		saveRank(positionToConfig.intelRankMap, positionGrpChildRow.getIntelScore());

		// 智力选择题得分
		if (intelChoiceCount > 0) {
			positionGrpChildRow.setIntelChoiceScore(MathUtils.round(
					intelChoiceScore / intelChoiceCount, 1));
		}

		// 智力问答题得分
		if (intelEssayCount > 0) {
			positionGrpChildRow.setIntelEssayScore(MathUtils.round(intelEssayScore
					/ intelEssayCount, 1));
		}

		/*// 面试结果
		Map<String, String> interviewResultMap = positionGrpChildRow
				.getInterviewResultMap();
		Map<String, Double> interviewScoreMap = positionGrpChildRow
				.getInterviewScoreMap();
		double interviewScore = 0.0;
		int interviewCount = 0;
		Interview interview = report.getInterview();
		if (interview != null) {
			if (!positionToConfig.gottonInterviewMeta) {
				positionToConfig.gottonInterviewMeta = true;

				InterviewInfo interviewInfo = interview.getInterviewInfo();
				for (InterviewInfo.Group group : interviewInfo.getGroups()) {
					if (group.getId().equals(GradeConst.INTERVIEW_ITEM)) {
						for (InterviewInfo.Item item : group.getItems()) {
							positionToConfig.interviewScores.add(item.getName());
							positionToConfig.interviewMap.put(item.getId(), item.getName());
						}
					} else {
						for (InterviewInfo.Item item : group.getItems())
							positionToConfig.interviewResults.add(item.getName());
					}
				}
			}

			List<InterviewItem> items = interview.getItems();
			if (items != null) {
				for (InterviewItem item : items) {
					if (item.getRealValue() == null)
						continue;

					String infoName = positionToConfig.interviewMap.get(item.getInfoId());
					if (infoName == null)
						continue;

					if (item.getGroupId().equals(GradeConst.INTERVIEW_ITEM)) {
						double value = Double.parseDouble(item
								.getRealValue());
						interviewScoreMap.put(infoName, value);
						interviewScore += value;
						interviewCount++;
					} else {
						interviewResultMap.put(infoName,
								item.getRealValue());
					}
				}
			}
		}

		// 面试得分
		if (interviewCount > 0) {
			positionGrpChildRow.setInterviewScore(MathUtils.round(interviewScore
					/ interviewCount, 1));
		}

		// 面试排名
		saveRank(positionToConfig.interviewRankMap, positionGrpChildRow.getInterviewScore());
*/
		// 设置常规信息
		long duration = (report.getEndTime() - report.getBeginTime()) / 1000;
		positionGrpChildRow.setDuration((duration / 60) + "分" + (duration % 60)
				+ "秒");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		positionGrpChildRow
				.setBeginTime(sdf.format(new Date(report.getBeginTime())));
		positionGrpChildRow.setEndTime(sdf.format(new Date(report.getEndTime())));
	
	}
	
	
	private PositionGrpChildRow processPositionGrpChildRowForInterview(PositionGrpChildRow positionGrpChildRow, SchoolCandidateReport candidateReport,PositionToConfigInfo positionToConfig) throws Exception{

		CandidateTest candidateTest = candidateTestDao.getEntity(candidateReport.getTestId());
		GetReportResponse report  = reportRetrieveService
				.getReport(candidateReport.getTestId());
		if(report.getInterview() == null || (report.getInterview() != null && CollectionUtils.isEmpty(report.getInterview().getItems()) )){
			return null;
		}
//		PositionRow positionRow = new PositionRow();
//		positionRows.add(positionRow);

		// 设置用户常规信息
		Summary summary = report.getSummary();
//		positionRow.setName(summary.getName());
//		positionRow.setEmail(summary.getEmail());

		// 设置用户扩展信息
		List<UserInfo> userInfos = summary.getInfos();
		if (userInfos != null) {
			// 设置用户扩展信息
			for (UserInfo userInfo : userInfos) {
				String infoName = userInfo.getKey();
				if (infoName.equals("手机号码")) {
//					positionRow.setPhone(userInfo.getValue());
					continue;
				}

				positionGrpChildRow.getExtInfoMap().put(infoName,
						userInfo.getValue());
			}
		}

		switch (candidateTest.getTestResult()) {
		case 0: // 待处理
			positionGrpChildRow.setStateForSort(PositionRow.STATE_PENDING);
			positionGrpChildRow.setState("待处理");
			break;
		case 1: // 已推荐
			positionGrpChildRow.setStateForSort(PositionRow.STATE_PASS);
			positionGrpChildRow.setState("已推荐");
			break;
		case 2: // 已淘汰
			positionGrpChildRow.setStateForSort(PositionRow.STATE_REJECT);
			positionGrpChildRow.setState("已淘汰");
			break;
		}

		// 设置得分
		positionGrpChildRow.setScore(summary.getScore());


		// 面试结果
		Map<String, String> interviewResultMap = positionGrpChildRow
				.getInterviewResultMap();
		Map<String, Double> interviewScoreMap = positionGrpChildRow
				.getInterviewScoreMap();
		double interviewScore = 0.0;
		int interviewCount = 0;
		Interview interview = report.getInterview();
		if (interview != null) {
			if (!positionToConfig.gottonInterviewMeta) {
				positionToConfig.gottonInterviewMeta = true;

				InterviewInfo interviewInfo = interview.getInterviewInfo();
				for (InterviewInfo.Group group : interviewInfo.getGroups()) {
					if (group.getId().equals(GradeConst.INTERVIEW_ITEM)) {
						for (InterviewInfo.Item item : group.getItems()) {
							positionToConfig.interviewScores.add(item.getName());
							positionToConfig.interviewMap.put(item.getId(), item.getName());
						}
					} else {
						for (InterviewInfo.Item item : group.getItems())
							positionToConfig.interviewResults.add(item.getName());
					}
				}
			}

			List<InterviewItem> items = interview.getItems();
			if (items != null) {
				for (InterviewItem item : items) {
					if (item.getRealValue() == null)
						continue;

					String infoName = positionToConfig.interviewMap.get(item.getInfoId());
					if (infoName == null)
						continue;

					if (item.getGroupId().equals(GradeConst.INTERVIEW_ITEM)) {
						double value = Double.parseDouble(item
								.getRealValue());
						interviewScoreMap.put(infoName, value);
						interviewScore += value;
						interviewCount++;
					} else {
						interviewResultMap.put(infoName,
								item.getRealValue());
					}
				}
			}
		}

		// 面试得分
		if (interviewCount > 0) {
			positionGrpChildRow.setInterviewScore(MathUtils.round(interviewScore
					/ interviewCount, 1));
		}

		// 面试排名
		saveRank(positionToConfig.interviewRankMap, positionGrpChildRow.getInterviewScore());

		// 设置常规信息
		long duration = (report.getEndTime() - report.getBeginTime()) / 1000;
		positionGrpChildRow.setDuration((duration / 60) + "分" + (duration % 60)
				+ "秒");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		positionGrpChildRow
				.setBeginTime(sdf.format(new Date(report.getBeginTime())));
		positionGrpChildRow.setEndTime(sdf.format(new Date(report.getEndTime())));
		return positionGrpChildRow;
	
	}
	
	private void initPosIdsToGroupRowsMap(Map<String,Map<Integer,PositionGroupRow>>  posIdsToGroupRows, int positionId ) throws PFServiceException{
		List<PositionRelation> positionRelations = positionRelationDao.getPositionRelationByPositionGroupId(positionId);
		List<Integer> mustPositionIds = new ArrayList<Integer>();
		List<Integer> optionalPositionIds = new ArrayList<Integer>();
		for(PositionRelation pr : positionRelations){
			if(pr.getRelation() == Constants.POSITION_RELATION_MUST){
				mustPositionIds.add(pr.getId().getPositionId());
			}else if(pr.getRelation() == Constants.POSITION_RELATION_CHOOSE){
				optionalPositionIds.add(pr.getId().getPositionId());
			}else{
				logger.error("not support relation " + pr.getRelation());
				throw new PFServiceException("not support relation " + pr.getRelation());
			}
		}
		
		List<String> posIdsKeys = new ArrayList<String>();
		
		StringBuffer mustKey = new StringBuffer();
		for(Integer mustPositionId : mustPositionIds){				
			mustKey.append(mustPositionId).append("_");
        }
		if(mustKey.length() > 0){
			mustKey.deleteCharAt(mustKey.length() - 1);
		}
		
		if(optionalPositionIds.size() > 0){
			for(Integer optPositionId : optionalPositionIds){
				StringBuffer key = new StringBuffer();
				key.append(optPositionId).append("_");
                key.append(mustKey);
                if(mustKey.length() == 0){ //没有必考
                	 key.deleteCharAt(key.length() - 1);
                }
                posIdsToGroupRows.put(key.toString(), new HashMap<Integer, PositionGroupRow>());
			}
		}else{//只有必考
            posIdsToGroupRows.put(mustKey.toString(),  new HashMap<Integer, PositionGroupRow>());
		}
		
	}
	private void export(Workbook workBook, List<QbQuestion> qbQuestions,
			boolean ignorePrebuilt) throws Exception {
		Sheet[] sheets = new Sheet[TOTAL_SHEETS];
		for (int i = 0; i < TOTAL_SHEETS; i++) {
			Sheet sheet = workBook.getSheetAt(i);
			sheets[i] = sheet;

			for (int j = sheet.getLastRowNum(); j > 0; j--) {
				Row row = sheet.getRow(j);
				sheet.removeRow(row);
			}
		}

		int groupId = 0;
		for (QbQuestion qbQuestion : qbQuestions) {
			// 预定义的题不能导出，保护资产
			if (ignorePrebuilt && qbQuestion.getPrebuilt() == 1)
				continue;

			int questionType = GradeConst.toQuestionTypeInt(qbQuestion
					.getQuestionType());
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_GROUP: {
				CellStyle regionStyle = workBook.createCellStyle();
				regionStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				regionStyle.setAlignment(CellStyle.ALIGN_CENTER);
				regionStyle.setWrapText(true);

				Sheet sheet = sheets[LoadConst.SHEET_TYPE_INTERVIEW
						- LoadConst.SHEET_TYPE_EXTERNAL_MIN];
				String groupName = "第" + (++groupId) + "组";
				String[] subAsks = qbQuestion.getSubAsks().split(",");
				for (String subAsk : subAsks) {
					QbQuestion subQbQuestion = qbQuestionDao.getEntity(Long
							.parseLong(subAsk));

					Holder holder = createList(subQbQuestion, groupName);
					createRow(sheet, holder.getObjs(), regionStyle);
				}

				CellRangeAddress region = new CellRangeAddress(
						sheet.getLastRowNum() - subAsks.length + 1,
						sheet.getLastRowNum(), 0, 0);
				sheet.addMergedRegion(region);
				break;
			}
			case GradeConst.QUESTION_TYPE_VIDEO:
				// 面试题以组的方式提供
				break;
			default: {
				Holder holder = createList(qbQuestion, null);
				createRow(sheets[holder.getSheetId()], holder.getObjs(), null);
				break;
			}
			}
		}
	}

	private static void copyCell(Row from, Row to, int fromIdx, int toIdx) {
		Cell fromCell = from.getCell(fromIdx);
		Cell toCell = to.createCell(toIdx);

		toCell.setCellStyle(fromCell.getCellStyle());
		toCell.setCellValue(fromCell.getStringCellValue());
	}

	private static void copyCell(Row from, Row to, int fromIdx, int toIdx,
			String value) {
		Cell fromCell = from.getCell(fromIdx);
		Cell toCell = to.createCell(toIdx);

		toCell.setCellStyle(fromCell.getCellStyle());
		toCell.setCellValue(value);
	}

	@SuppressWarnings("unchecked")
	private static void setRank(List<PositionRow> positionRows,
			TreeMap<Double, Integer> rankMap,
			TreeMap<Double, Integer> intelRankMap,
			TreeMap<Double, Integer> interviewRankMap) {
		for (TreeMap<Double, Integer> map : new TreeMap[] { rankMap,
				intelRankMap, interviewRankMap }) {
			int rank = 1;
			for (Entry<Double, Integer> entry : map.descendingMap().entrySet()) {
				int value = entry.getValue();
				entry.setValue(rank);
				rank += value;
			}
		}

		for (PositionRow positionRow : positionRows) {
			// 总分排名
			positionRow.setRank(rankMap.get(positionRow.getScore()));
			// 智力排名
			if (positionRow.getIntelScore() != null) {
				positionRow.setIntelRank(intelRankMap.get(positionRow
						.getIntelScore()));
			}
			// 面试排名
			if (positionRow.getInterviewScore() != null) {
				positionRow.setInterviewRank(interviewRankMap.get(positionRow
						.getInterviewScore()));
			}
		}
	}

	private static void saveRank(Map<Double, Integer> map, Double key) {
		if (key == null)
			return;

		Integer count = map.get(key);
		if (count == null)
			map.put(key, 1);
		else
			map.put(key, count + 1);
	}

	private static void handlePartItem(Map<String, ScorePair> map,
			Set<String> set, PartItem partItem) {
		set.add(partItem.getSkillName() == null ? "" : partItem.getSkillName());

		if (partItem.getScore() == null)
			return;

		ScorePair scorePair = map.get(partItem.getSkillName());
		if (scorePair == null) {
			scorePair = new ScorePair();
			scorePair.setScores(partItem.getScore());
			scorePair.setCount(1);
			map.put(partItem.getSkillName(), scorePair);
		} else {
			scorePair.setScores(scorePair.getScores() + partItem.getScore());
			scorePair.setCount(scorePair.getCount() + 1);
		}
	}

	private static void calcScore(Map<String, Double> scoreMap,
			Map<String, ScorePair> map) {
		for (Entry<String, ScorePair> entry : map.entrySet()) {
			ScorePair scorePair = entry.getValue();
			if (scorePair.getCount() <= 0)
				continue;

			scoreMap.put(
					entry.getKey(),
					MathUtils.round(
							scorePair.getScores() / scorePair.getCount(), 1));
		}
	}

	private static int addExtensibleHeader(Iterator<String> iter,
			Sheet template, Sheet sheet, Row templateRow1, Row templateRow2,
			Row row1, Row row2, int templateColumnIdx, int firstColumn) {
		int columnIdx = firstColumn;

		while (iter.hasNext()) {
			String title = iter.next();
			sheet.setColumnWidth(columnIdx,
					template.getColumnWidth(templateColumnIdx));
			copyCell(templateRow1, row1, templateColumnIdx, columnIdx);
			copyCell(templateRow2, row2, templateColumnIdx, columnIdx, title);
			columnIdx++;
		}

		if (firstColumn + 1 < columnIdx) {
			CellRangeAddress region = new CellRangeAddress(0, 0, firstColumn,
					columnIdx - 1);
			sheet.addMergedRegion(region);
		}

		return columnIdx;
	}
    
	private static int addExtensibleHeaderGroup(Iterator<String> iter,
			Sheet template, Sheet sheet, Row templateRow1, Row templateRow2,Row templateRow3,
			Row row1, Row row2,Row row3, int templateColumnIdx, int firstColumn,String positionName) {
		int columnIdx = firstColumn;

		while (iter.hasNext()) {
			String title = iter.next();
			sheet.setColumnWidth(columnIdx,
					template.getColumnWidth(templateColumnIdx));
			copyCell(templateRow1, row1, templateColumnIdx, columnIdx,positionName);
			copyCell(templateRow1, row2, templateColumnIdx, columnIdx);
			copyCell(templateRow2, row3, templateColumnIdx, columnIdx, title);
			columnIdx++;
		}

		if (firstColumn + 1 < columnIdx) { 
			CellRangeAddress region = new CellRangeAddress(1, 1, firstColumn,//第二行合并
					columnIdx - 1);
			sheet.addMergedRegion(region);
		}

		return columnIdx;
	}
	/**
	 * 前两行合并
	 * @param iter
	 * @param template
	 * @param sheet
	 * @param templateRow1
	 * @param templateRow2
	 * @param templateRow3
	 * @param row1
	 * @param row2
	 * @param row3
	 * @param templateColumnIdx
	 * @param firstColumn
	 * @return
	 */
	private static int addExtensibleHeaderGroupNoPaper(Iterator<String> iter,
			Sheet template, Sheet sheet, Row templateRow1, Row templateRow2,Row templateRow3,
			Row row1, Row row2,Row row3, int templateColumnIdx, int firstColumn) {
		int columnIdx = firstColumn;

		while (iter.hasNext()) {
			String title = iter.next();
			sheet.setColumnWidth(columnIdx,
					template.getColumnWidth(templateColumnIdx));
			copyCell(templateRow1, row1, templateColumnIdx, columnIdx);
			copyCell(templateRow1, row2, templateColumnIdx, columnIdx);
			copyCell(templateRow2, row3, templateColumnIdx, columnIdx, title);
			columnIdx++;
		}

		if (firstColumn + 1 <= columnIdx) { 
			CellRangeAddress region = new CellRangeAddress(0, 1, firstColumn,//前两行
					columnIdx - 1);
			sheet.addMergedRegion(region);
		}

		return columnIdx;
	}
}
