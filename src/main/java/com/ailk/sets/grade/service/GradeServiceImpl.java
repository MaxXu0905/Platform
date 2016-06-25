package com.ailk.sets.grade.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateExamQuestionDao;
import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.grade.dao.intf.ICandidateInfoTestDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.grade.common.DebugConfig;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.MatrixElement;
import com.ailk.sets.grade.grade.config.MatrixFile;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.IGradeExecutorService;
import com.ailk.sets.grade.grade.execute.IGradeScheduler;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.CommitFile;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse.UserInfo;
import com.ailk.sets.grade.intf.GetInterviewResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse.Matrix;
import com.ailk.sets.grade.intf.GetQInfoResponse.MatrixItem;
import com.ailk.sets.grade.intf.GetQidsResponse;
import com.ailk.sets.grade.intf.GetQidsResponse.QInfo;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.intf.RunTestResponse;
import com.ailk.sets.grade.intf.ScoreResponse;
import com.ailk.sets.grade.intf.report.GetComparedReportsResponse;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.IReportConfig;
import com.ailk.sets.grade.intf.report.Interview;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateExamQuestionPK;
import com.ailk.sets.grade.jdbc.CandidateInfoTest;
import com.ailk.sets.grade.jdbc.CandidateInfoTestPK;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.qb.IExecutor;
import com.ailk.sets.grade.security.DESCoder;
import com.ailk.sets.grade.service.intf.AnswerWrapper;
import com.ailk.sets.grade.service.intf.IComparedReportRetrieveService;
import com.ailk.sets.grade.service.intf.IReportRetrieveService;
import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.grade.service.intf.IReportSummaryRetrieveService;
import com.ailk.sets.grade.utils.Env;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.google.gson.Gson;

public class GradeServiceImpl implements IGradeService {

	private static final Logger logger = Logger
			.getLogger(GradeServiceImpl.class);

	@Autowired
	private ApplicationContext context;
	
	private GradeServiceImpl gradeService;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private ICandidateInfoTestDao candidateInfoTestDao;

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private ICandidateInfoExtDao candidateInfoExtDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	@Autowired
	private ICandidateExamQuestionDao candidateExamQuestionDao;

	@Autowired
	private IPositionInfoExtDao positionInfoExtDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IGradeExecutorService gradeExecutor;

	@Autowired
	private IReportRetrieveService reportRetrieveService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private IComparedReportRetrieveService comparedReportRetrieveService;

	@Autowired
	private IReportSummaryRetrieveService reportSummaryRetrieveService;

	@Autowired
	private IReportConfig reportConfig;

	@Autowired
	private IGradeScheduler gradeScheduler;

	@Value("${grade.user.reference.permission}")
	private String referencePermission;

	@Value("${grade.user.candidate.permission}")
	private String candidatePermission;

	private static final Gson gson = new Gson();
	
	@PostConstruct
	public void init() {
		gradeService = context.getBean(GradeServiceImpl.class);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BaseResponse chechEnv(long testId) {
		BaseResponse response = new BaseResponse();

		// 检查是否有邀请码相关的目录
		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(testId);
		if (candidateTestQuestions == null || candidateTestQuestions.isEmpty()) {
			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("试卷还未生成，testId=" + testId);
			return response;
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BaseResponse genExam(long testId, List<Long> qids) throws Exception {
		logger.info("testId=" + testId + ", qid={"
				+ StringUtils.join(qids, "L,") + "L}");

		BaseResponse response = new BaseResponse();

		List<CandidateExamQuestion> sampleChoices = new ArrayList<CandidateExamQuestion>();
		List<CandidateExamQuestion> sampleProgram = new ArrayList<CandidateExamQuestion>();
		Map<String, List<CandidateExamQuestion>> sChoicesMap = new HashMap<String, List<CandidateExamQuestion>>();
		Map<String, List<CandidateExamQuestion>> mChoicesMap = new HashMap<String, List<CandidateExamQuestion>>();
		List<CandidateExamQuestion> others = new ArrayList<CandidateExamQuestion>();

		for (long qid : qids) {
			// 加载
			QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qid);
			if (qbQuestionDetail == null) {
				response.setErrorCode(BaseResponse.ESYSTEM);
				response.setErrorDesc("找不到试题详情，qid=" + qid);
				return response;
			}

			String content = qbQuestionDetail.getContent();
			if (qbQuestionDetail.isEncrypted()) {
				content = new String(DESCoder.decrypt(
						content.getBytes(GradeConst.ENCODING),
						GradeConst.DES_KEY), GradeConst.ENCODING);
			}
			QuestionContent questionContent = gson.fromJson(content,
					QuestionContent.class);

			// 调用试题实例化接口
			QuestionConf questionConf = questionContent.getQuestionConf();
			String executorClassName = questionConf.getExecutorClass();
			if (executorClassName != null) {
				IExecutor executor = (IExecutor) Class.forName(
						executorClassName).newInstance();
				try {
					executor.instantiate(questionContent);
				} catch (Exception e) {
					logger.error("不能实例化试题，qid=" + qid, e);
					throw e;
				} catch (Throwable e) {
					logger.error("不能实例化试题，qid=" + qid, e);
					throw new Exception(e);
				}
			}

			CandidateExamQuestionPK candidateExamQuestionPK = new CandidateExamQuestionPK();
			candidateExamQuestionPK.setTestId(testId);
			candidateExamQuestionPK.setQuestionId(qid);

			CandidateExamQuestion candidateExamQuestion = new CandidateExamQuestion();
			candidateExamQuestion
					.setCandidateExamQuestionPK(candidateExamQuestionPK);
			candidateExamQuestion.setQuestionType(questionConf.getTypeInt());
			candidateExamQuestion.setContent(gson.toJson(questionContent));
			candidateExamQuestion.setSample(questionConf.isSample());

			// 对题按类型分类
			switch (questionConf.getTypeInt()) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
				if (questionConf.getCategory() != GradeConst.CATEGORY_TECHNOLOGY) {
					others.add(candidateExamQuestion);
				} else {
					if (questionConf.isSample()) {
						sampleChoices.add(candidateExamQuestion);
					} else {
						List<QbQuestionSkill> qbQuestionSkills = qbQuestionSkillDao
								.getSkills(candidateExamQuestion
										.getCandidateExamQuestionPK()
										.getQuestionId());
						String key = "";
						int skillSeq = Integer.MAX_VALUE;
						for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
							if (qbQuestionSkill.getSkillSeq() < skillSeq) {
								key = qbQuestionSkill.getId().getSkillId();
								skillSeq = qbQuestionSkill.getSkillSeq();
							}
						}
						Map<String, List<CandidateExamQuestion>> choicesMap;
						if (questionConf.getTypeInt() == GradeConst.QUESTION_TYPE_S_CHOICE)
							choicesMap = sChoicesMap;
						else
							choicesMap = mChoicesMap;

						List<CandidateExamQuestion> candidateExamQuestions = choicesMap
								.get(key);
						if (candidateExamQuestions == null) {
							candidateExamQuestions = new ArrayList<CandidateExamQuestion>();
							choicesMap.put(key, candidateExamQuestions);
						}

						candidateExamQuestions.add(candidateExamQuestion);
					}
				}
				break;
			case GradeConst.QUESTION_TYPE_PROGRAM:
				if (questionConf.isSample())
					sampleProgram.add(candidateExamQuestion);
				else
					others.add(candidateExamQuestion);
				break;
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			case GradeConst.QUESTION_TYPE_ESSAY:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
				others.add(candidateExamQuestion);
				break;
			}
		}

		if (sampleChoices.size() > 1) {
			StringBuilder builder = new StringBuilder();
			for (CandidateExamQuestion candidateExamQuestion : sampleChoices) {
				if (builder.length() > 0)
					builder.append(",");
				builder.append(candidateExamQuestion
						.getCandidateExamQuestionPK().getQuestionId());
			}

			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("提供了多于一个选择题例子，qids=" + builder.toString());
			return response;
		}

		if (sampleProgram.size() > 1) {
			StringBuilder builder = new StringBuilder();
			for (CandidateExamQuestion candidateExamQuestion : sampleChoices) {
				if (builder.length() > 0)
					builder.append(",");
				builder.append(candidateExamQuestion
						.getCandidateExamQuestionPK().getQuestionId());
			}

			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("提供了多于一个编程题例子，qids=" + builder.toString());
			return response;
		}

		// 打乱选择题的顺序
		List<List<CandidateExamQuestion>> sChoicesList = new ArrayList<List<CandidateExamQuestion>>();
		for (List<CandidateExamQuestion> item : sChoicesMap.values()) {
			Collections.shuffle(item);
			sChoicesList.add(item);
		}

		List<List<CandidateExamQuestion>> mChoicesList = new ArrayList<List<CandidateExamQuestion>>();
		for (List<CandidateExamQuestion> item : mChoicesMap.values()) {
			Collections.shuffle(item);
			mChoicesList.add(item);
		}

		// 打乱技能顺序
		Collections.shuffle(sChoicesList);
		Collections.shuffle(mChoicesList);

		// 组成数组
		List<CandidateExamQuestion> sChoices = new ArrayList<CandidateExamQuestion>();
		for (List<CandidateExamQuestion> item : sChoicesList) {
			sChoices.addAll(item);
		}

		List<CandidateExamQuestion> mChoices = new ArrayList<CandidateExamQuestion>();
		for (List<CandidateExamQuestion> item : mChoicesList) {
			mChoices.addAll(item);
		}

		int serialNo = 0;
		for (List<?> list : new List<?>[] { sampleChoices, sampleProgram,
				sChoices, mChoices, others }) {
			for (CandidateExamQuestion candidateExamQuestion : (List<CandidateExamQuestion>) list) {
				candidateExamQuestion.setSerialNo(serialNo++);
				candidateExamQuestionDao.saveOrUpdate(candidateExamQuestion);
			}
		}

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetQidsResponse getQids(long testId) throws Exception {
		GetQidsResponse response = new GetQidsResponse();
		List<QInfo> choices = new ArrayList<QInfo>();

		List<CandidateExamQuestion> candidateExamQuestions = candidateExamQuestionDao
				.getList(testId);

		for (CandidateExamQuestion candidateExamQuestion : candidateExamQuestions) {
			long qid = candidateExamQuestion.getCandidateExamQuestionPK()
					.getQuestionId();

			if (candidateExamQuestion.isSample())
				continue;

			switch (candidateExamQuestion.getQuestionType()) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE: {
				QbQuestion qbQuestion = qbQuestionDao
						.getEntity(candidateExamQuestion
								.getCandidateExamQuestionPK().getQuestionId());
				if (qbQuestion == null
						|| qbQuestion.getCategory() != GradeConst.CATEGORY_TECHNOLOGY)
					break;

				QInfo qInfo = new QInfo();
				qInfo.setQid(qid);
				qInfo.setAnswered(candidateExamQuestion.getAnswer() != null);
				choices.add(qInfo);
				break;
			}
			default:
				break;
			}
		}

		if (!choices.isEmpty())
			response.setChoices(choices);

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetQInfoResponse getQInfo(long testId, long qid) throws Exception {
		GetQInfoResponse response = new GetQInfoResponse();

		CandidateExamQuestionPK candidateExamQuestionPK = new CandidateExamQuestionPK();
		candidateExamQuestionPK.setTestId(testId);
		candidateExamQuestionPK.setQuestionId(qid);
		CandidateExamQuestion candidateExamQuestion = candidateExamQuestionDao
				.get(candidateExamQuestionPK);
		if (candidateExamQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到记录，testId=" + testId + ", qid=" + qid);
			return response;
		}

		QuestionContent questionContent = gson.fromJson(
				candidateExamQuestion.getContent(), QuestionContent.class);

		QuestionConf questionConf = questionContent.getQuestionConf();

		// 如果是编程题，题库字段改成语言
		if (questionConf.getMatrix() == null) {
			response.setQbName(questionConf.getQbName());
		} else {
			response.setQbName(questionConf.getMatrix().getFiles().get(0)
					.getMode());
		}

		response.setMode(questionConf.getMode());
		response.setTitle(QuestionUtils.escape(questionConf.getTitle(),
				questionConf.isHtml()));
		response.setCategory(questionConf.getCategory());
		response.setType(questionConf.getTypeInt());
		response.setOptions(QuestionUtils.escape(questionConf.getOptions(),
				questionConf.isHtml()));
		response.setEditType(GradeConst.getEditType(questionConf.getTypeInt(),
				questionConf.getMode()));

		AnswerWrapper answerWrapper = null;
		if (candidateExamQuestion.getAnswer() != null) {
			answerWrapper = gson.fromJson(candidateExamQuestion.getAnswer(),
					AnswerWrapper.class);
		}

		switch (questionConf.getTypeInt()) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
			// 读取答案
			if (questionConf.isSample())
				response.setOptAnswers(questionConf.getOptAnswers());
			else if (answerWrapper != null)
				response.setOptAnswers(answerWrapper.getOptAnswer());
			break;
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			if (answerWrapper != null) {
				response.setOptAnswers(answerWrapper.getOptAnswer());
				response.setOptDesc(answerWrapper.getOptDesc());
			}
			break;
		default: {
			MatrixElement matrixElement = questionConf.getMatrix();
			List<MatrixFile> files = matrixElement.getFiles();
			if (files != null && !files.isEmpty()) {
				Matrix matrix = new Matrix();
				response.setMatrix(matrix);

				matrix.setConsole(matrixElement.isConsole());
				matrix.setSamples(questionConf.getSamples());

				List<MatrixItem> items = new ArrayList<MatrixItem>();
				matrix.setItems(items);

				// AnswerWrapper answerWrapper = null;
				boolean changed = candidateExamQuestion.getAnswer() != null;
				if (changed) {
					answerWrapper = gson.fromJson(
							candidateExamQuestion.getAnswer(),
							AnswerWrapper.class);
				}

				for (MatrixFile file : files) {
					MatrixItem item = new MatrixItem();
					item.setMode(file.getMode());
					item.setFilename(file.getFilename());

					// 只有编程题，才需要提供模板
					String candidateName = GradeConst.CANDIDATE_PREFIX
							+ file.getFilename();
					String template = questionContent.getData().get(
							candidateName);

					if (questionConf.getTypeInt() == GradeConst.QUESTION_TYPE_PROGRAM)
						item.setTemplate(template);

					// 如果是编程题，没有变更则不需要提供内容
					if (changed
							|| questionConf.getTypeInt() != GradeConst.QUESTION_TYPE_PROGRAM) {
						String content = null;
						if (answerWrapper != null) {
							content = answerWrapper.getAnswer(file
									.getFilename());
						}

						if (content == null)
							content = template;

						item.setContent(content);
					}

					items.add(item);
				}
			}
			break;
		}
		}

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetQInfoResponse getQInfo(long qid) throws Exception {
		GetQInfoResponse response = new GetQInfoResponse();

		// 加载
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qid);
		if (qbQuestionDetail == null) {
			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("找不到试题详情，qid=" + qid);
			return response;
		}

		String content = qbQuestionDetail.getContent();
		if (qbQuestionDetail.isEncrypted()) {
			content = new String(DESCoder.decrypt(
					content.getBytes(GradeConst.ENCODING), GradeConst.DES_KEY),
					GradeConst.ENCODING);
		}
		QuestionContent questionContent = gson.fromJson(content,
				QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();

		response.setQbName(questionConf.getQbName());
		response.setMode(questionConf.getMode());
		response.setTitle(QuestionUtils.escape(questionConf.getTitle(),
				questionConf.isHtml()));
		response.setCategory(questionConf.getCategory());
		response.setType(questionConf.getTypeInt());
		response.setOptions(QuestionUtils.escape(questionConf.getOptions(),
				questionConf.isHtml()));
		response.setOptAnswers(QuestionUtils.getAnswers(questionConf
				.getAnswer()));
		response.setOptDesc(questionConf.getOptDesc());
		response.setEditType(GradeConst.getEditType(questionConf.getTypeInt(),
				questionConf.getMode()));

		MatrixElement matrixElement = questionConf.getMatrix();
		if (matrixElement != null) {
			List<MatrixFile> files = matrixElement.getFiles();
			if (files != null && !files.isEmpty()) {
				if (files.size() != 1) {
					response.setErrorCode(BaseResponse.ESYSTEM);
					response.setErrorDesc("多于一个可编辑文件，qid=" + qid);
					return response;
				}

				Matrix matrix = new Matrix();
				response.setMatrix(matrix);

				matrix.setConsole(matrixElement.isConsole());
				matrix.setSamples(questionConf.getSamples());

				List<MatrixItem> items = new ArrayList<MatrixItem>();
				matrix.setItems(items);

				MatrixFile file = files.get(0);
				MatrixItem item = new MatrixItem();
				item.setMode(file.getMode());
				item.setFilename(file.getFilename());
				items.add(item);

				String fullname = GradeConst.REFERENCE_PREFIX
						+ file.getFilename();
				String code = questionContent.getData().get(fullname);
				item.setTemplate(code);
			}
		}

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BaseResponse commitFiles(long testId, long qid,
			List<CommitFile> items) throws Exception {
		BaseResponse response = new BaseResponse();
		if (items == null)
			return response;

		CandidateExamQuestionPK candidateExamQuestionPK = new CandidateExamQuestionPK();
		candidateExamQuestionPK.setTestId(testId);
		candidateExamQuestionPK.setQuestionId(qid);
		CandidateExamQuestion candidateExamQuestion = candidateExamQuestionDao
				.get(candidateExamQuestionPK);
		if (candidateExamQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到记录，testId=" + testId + ", qid=" + qid);
			return response;
		}

		AnswerWrapper answerWrapper = new AnswerWrapper();
		Map<String, String> data = new HashMap<String, String>();
		for (CommitFile commitFile : items) {
			data.put(commitFile.getFilename(), commitFile.getContent());

		}
		answerWrapper.setData(data);
		candidateExamQuestion.setAnswer(gson.toJson(answerWrapper));
		candidateExamQuestionDao.update(candidateExamQuestion);
		candidateTestQuestionDao.updateAnswerFlag(testId, qid, 1);
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BaseResponse commitChoice(long testId, long qid, String answer,
			String desc) throws Exception {
		BaseResponse response = new BaseResponse();

		CandidateExamQuestionPK candidateExamQuestionPK = new CandidateExamQuestionPK();
		candidateExamQuestionPK.setTestId(testId);
		candidateExamQuestionPK.setQuestionId(qid);
		CandidateExamQuestion candidateExamQuestion = candidateExamQuestionDao
				.get(candidateExamQuestionPK);
		if (candidateExamQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到记录，testId=" + testId + ", qid=" + qid);
			return response;
		}

		if ((answer == null || answer.isEmpty())
				&& (desc == null || desc.isEmpty())) {
			candidateExamQuestion.setAnswer(null);
		} else {
			AnswerWrapper answerWrapper = new AnswerWrapper();
			answerWrapper.setOptAnswer(answer);
			answerWrapper.setOptDesc(desc);
			candidateExamQuestion.setAnswer(gson.toJson(answerWrapper));
		}

		candidateTestQuestionDao.updateAnswerFlag(testId, qid, 1);
		candidateExamQuestionDao.update(candidateExamQuestion);
		return response;
	}

	@Override
	public RunTestResponse runTest(long testId, long qid,
			List<CommitFile> items, String arg) throws Exception {
		logger.info("runTest: testId=" + testId + ", qid=" + qid + ", arg="
				+ arg);

		List<String> args = new ArrayList<String>();
		args.add("-t");
		args.add("test");
		args.add("-a");
		args.add(arg);

		return runTest(testId, qid, items, args);
	}

	@Override
	public RunTestResponse runTest(long testId, long qid,
			List<CommitFile> items, int sampleId) throws Exception {
		logger.info("runTest: testId=" + testId + ", qid=" + qid
				+ ", sampleId=" + sampleId);

		List<String> args = new ArrayList<String>();
		args.add("-t");
		args.add("testSample");
		args.add("-i");
		args.add(Integer.toString(sampleId));

		return runTest(testId, qid, items, args);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetReportResponse getReport(long reportId) throws Exception {
		return reportRetrieveService.getReport(reportId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetReportResponse getReport(long testId, String reportPassport)
			throws Exception {
		return reportRetrieveService.getReport(testId, reportPassport);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetReportSummaryResponse getReportSummary(long testId)
			throws Exception {
		return reportSummaryRetrieveService.getReportSummary(testId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetReportSummaryResponse gradeReport(long testId) throws Exception {
		gradeScheduler.gradeTest(testId);
		return reportSummaryRetrieveService.getReportSummary(testId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetComparedReportsResponse getComparedReports(List<Long> testIds)
			throws Exception {
		return comparedReportRetrieveService.getComparedReports(testIds);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ScoreResponse saveInterview(long testId, List<InterviewItem> items)
			throws Exception {
		ScoreResponse response = new ScoreResponse();

		int rows = 0;
		double scores = 0;

		for (InterviewItem item : items) {
			CandidateInfoTest candidateInfoTest = new CandidateInfoTest();
			CandidateInfoTestPK candidateInfoTestPK = new CandidateInfoTestPK();

			candidateInfoTestPK.setTestId(testId);
			candidateInfoTestPK.setGroupId(item.getGroupId());
			candidateInfoTestPK.setInfoId(item.getInfoId());

			candidateInfoTest.setCandidateInfoTestPK(candidateInfoTestPK);
			candidateInfoTest.setValue(item.getValue());
			candidateInfoTest.setRealValue(item.getRealValue());

			candidateInfoTestDao.saveOrUpdate(candidateInfoTest);

			if (item.getGroupId().equals(GradeConst.INTERVIEW_ITEM)) {
				rows++;
				scores += Double.parseDouble(item.getRealValue());
			}
		}

		if (rows > 0) {
			response.setEditable(false);
			response.setScore(MathUtils.round(scores / rows, 1));
		} else {
			response.setEditable(true);
			response.setScore(0);
		}
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetInterviewResponse getInterview(int employerId, int testType,
			long testId) throws Exception {
		GetInterviewResponse response = new GetInterviewResponse();

		Interview interview = reportService.getInterview(employerId, testType,
				testId);
		response.setInterview(interview);

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public GetCandidateInfoResponse getCandidateInfo(int candidateId,
			int employerId, int positionId) throws Exception {
		GetCandidateInfoResponse response = new GetCandidateInfoResponse();

		Candidate candidate = candidateDao.getEntity(candidateId);
		if (candidate == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			logger.error("找不到职位记录，candidateId=" + candidateId);
			return response;
		}

		response.setName(candidate.getCandidateName());
		response.setEmail(candidate.getCandidateEmail());

		// 设置候选人通用属性
		List<UserInfo> infos = new ArrayList<UserInfo>();
		response.setInfos(infos);

		// 加载候选人扩展信息
		Map<String, String> candidateInfoExtMap = candidateInfoExtDao
				.getMap(candidateId);

		// 根据招聘人关心的信息，设置用户信息
		List<PositionInfoExt> positionInfoExts = positionInfoExtDao.getList(
				employerId, positionId);
		if (positionInfoExts == null || positionInfoExts.isEmpty())
			positionInfoExts = positionInfoExtDao.getList(employerId, -1);

		for (PositionInfoExt companyInfoExt : positionInfoExts) {
			String infoId = companyInfoExt.getId().getInfoId();
			String realValue = candidateInfoExtMap.get(infoId);
			if (realValue == null) // 找不到候选人信息则忽略
				continue;

			UserInfo userInfo = new UserInfo();
			userInfo.setKey(companyInfoExt.getInfoName());
			userInfo.setValue(realValue);
			infos.add(userInfo);
		}

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ScoreResponse scoreQuestion(int anchor, long testId,
			long questionId, double score) {
		ScoreResponse response = new ScoreResponse();

		int points = 0;
		double scores = 0;
		boolean editable = false;

		CandidateTestQuestionId candidateTestQuestionId = new CandidateTestQuestionId();
		candidateTestQuestionId.setTestId(testId);
		candidateTestQuestionId.setQuestionId(questionId);
		CandidateTestQuestion candidateTestQuestion = candidateTestQuestionDao
				.get(candidateTestQuestionId);
		if (candidateTestQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到测试试题，testId=" + testId + ", questionId="
					+ questionId);
			return response;
		}

		QbQuestion qbQuestion = qbQuestionDao.getEntity(questionId);
		if (qbQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到试题，testId=" + testId + ", questionId="
					+ questionId);
			return response;
		}

		if (qbQuestion.getPoint() <= 0) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("试题未设置分值，testId=" + testId + ", questionId="
					+ questionId);
			return response;
		}

		double standardScore = score / qbQuestion.getPoint()
				* GradeConst.GRADE_INTERNAL_SCORE;
		candidateTestQuestion.setGetScore(standardScore);
		if (standardScore == GradeConst.GRADE_INTERNAL_SCORE)
			candidateTestQuestion.setCorrectFlag(1);
		else
			candidateTestQuestion.setCorrectFlag(0);
		candidateTestQuestionDao.update(candidateTestQuestion);

		if (anchor == IReportConfig.REPORT_COLUMN_UNKNOWN)
			return response;

		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(testId);
		for (CandidateTestQuestion question : candidateTestQuestions) {
			qbQuestion = qbQuestionDao.getEntity(question.getId()
					.getQuestionId());
			int questionType = GradeConst.toQuestionTypeInt(qbQuestion
					.getQuestionType());
			int index = reportConfig.getReportIndex(questionType,
					qbQuestion.getCategory());
			if (index != anchor)
				continue;

			switch (anchor) {
			case IReportConfig.REPORT_COLUMN_TECH_ESSAY:
			case IReportConfig.REPORT_COLUMN_INTELLIGENCE:
			case IReportConfig.REPORT_COLUMN_PROGRAM:
				break;
			default:
				continue;
			}

			points += qbQuestion.getPoint();
			if (question.getGetScore() != null) {
				editable = true;
				scores += question.getGetScore();
			}
		}

		response.setEditable(editable);
		response.setScore(MathUtils.round(scores / points
				* GradeConst.SCORE_SCALE, 1));
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	public CandidateExamQuestion prepareTest(long testId, long qid,
			List<CommitFile> items, RunTestResponse response) throws Exception {
		BaseResponse commitResponse = commitFiles(testId, qid, items);
		if (commitResponse.getErrorCode() != 0) {
			response.setErrorCode(commitResponse.getErrorCode());
			response.setErrorDesc(commitResponse.getErrorDesc());
			return null;
		}

		CandidateExamQuestionPK candidateExamQuestionPK = new CandidateExamQuestionPK();
		candidateExamQuestionPK.setTestId(testId);
		candidateExamQuestionPK.setQuestionId(qid);
		CandidateExamQuestion candidateExamQuestion = candidateExamQuestionDao
				.get(candidateExamQuestionPK);
		if (candidateExamQuestion == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到记录，testId=" + testId + ", qid=" + qid);
			return null;
		}

		return candidateExamQuestion;
	}

	private RunTestResponse runTest(long testId, long qid,
			List<CommitFile> items, List<String> args) throws Exception {
		RunTestResponse response = new RunTestResponse();
		CandidateExamQuestion candidateExamQuestion = gradeService.prepareTest(
				testId, qid, items, response);

		QuestionContent questionContent = gson.fromJson(
				candidateExamQuestion.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();
		int modeInt = GradeConst.toModeInt(questionConf.getMode());
		IExecutor executor = (IExecutor) Class.forName(
				questionConf.getExecutorClass()).newInstance();

		AnswerWrapper answerWrapper = null;
		if (candidateExamQuestion.getAnswer() != null) {
			answerWrapper = gson.fromJson(candidateExamQuestion.getAnswer(),
					AnswerWrapper.class);
		}

		String qRoot = Env.getExamRoot() + File.separator + testId
				+ File.separator + qid;
		try {
			QuestionUtils.save(qRoot, questionContent, answerWrapper,
					referencePermission, candidatePermission);

			ResultHolder holder = executor.compile(qRoot,
					GradeConst.STAGE_CANDIDATE);
			if (holder.getExitValue() == 0) { // 编译成功后，执行代码
				holder.merge(gradeExecutor.run(executor, qRoot,
						GradeConst.STAGE_CANDIDATE, modeInt, args, false));
			}

			response.setExitValue(holder.getExitValue());
			response.setOut(holder.getOut());
			response.setErr(holder.getErr());

			return response;
		} finally {
			if (!DebugConfig.isKeepTempFiles())
				QuestionUtils.delete(qRoot);
		}
	}

}
