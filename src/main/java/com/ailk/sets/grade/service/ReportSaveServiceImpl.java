package com.ailk.sets.grade.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateExamQuestionDao;
import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestScoreDao;
import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.grade.dao.intf.IStatQuestionTestDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.InvalidDataException;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.report.Completion;
import com.ailk.sets.grade.intf.report.IReportConfig;
import com.ailk.sets.grade.intf.report.Interview;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.Part;
import com.ailk.sets.grade.intf.report.PartItem;
import com.ailk.sets.grade.intf.report.Report;
import com.ailk.sets.grade.intf.report.Summary;
import com.ailk.sets.grade.intf.report.UserInfo;
import com.ailk.sets.grade.intf.report.Video;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.StatQuestionTest;
import com.ailk.sets.grade.service.intf.AnswerWrapper;
import com.ailk.sets.grade.service.intf.IReportSaveService;
import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.grade.utils.QuestionUtils.FileInfo;
import com.ailk.sets.grade.utils.Utils;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IPaperSkillDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionLogDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.domain.PaperSkill;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.school.domain.CandidateTestScore;
import com.ailk.sets.platform.intf.school.domain.CandidateTestScoreId;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportSaveServiceImpl implements IReportSaveService {

	private static final Logger logger = Logger
			.getLogger(ReportSaveServiceImpl.class);

	// 预定义的分值范围
	private static final double[] SCORE_RANGES = { 0.0, 4.0, 6.0, 8.0 };
	private static final Gson gson = new Gson();

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private ICandidateReportDao candidateReportDao;

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private ICandidateInfoExtDao candidateInfoExtDao;

	@Autowired
	private IPositionInfoExtDao positionInfoExtDao;

	@Autowired
	private IPositionLogDao positionLogDao;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	@Autowired
	private ICandidateExamQuestionDao candidateExamQuestionDao;

	@Autowired
	private IStatQuestionTestDao statQuestionTestDao;

	@Autowired
	private ICandidateTestScoreDao candidateTestScoreDao;

	@Autowired
	private IPaperSkillDao paperSkillDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IReportService reportService;

	@Autowired
	private IReportConfig reportConfig;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	/**
	 * 生成报告
	 * 
	 * @param candidateTest
	 *            测试对象
	 */
	@Override
	public boolean saveReport(CandidateTest candidateTest) throws Exception {
		Holder holder = new Holder();
		Report report = holder.report;

		try {
			// 加载报告相关表
			holder.candidateTest = candidateTest;
			loadPosition(holder);
			loadCandidate(holder);
			loadTestQuestions(holder, candidateTest.getTestId());
			loadExamQuestions(holder, candidateTest.getTestId());
			loadCandidateInfoExt(holder);
			loadPaperSkill(holder);

			// 设置试卷的技能
			setTestSkills(holder);

			if (candidateTest.getBeginTime() == null)
				report.setBeginTime(0L);
			else
				report.setBeginTime(candidateTest.getBeginTime().getTime());

			if (candidateTest.getEndTime() == null)
				report.setEndTime(0L);
			else
				report.setEndTime(candidateTest.getEndTime().getTime());

			report.setTitle(holder.position.getPositionName());
			report.setLevel(holder.position.getLevel());

			// 设置报告时间
			report.setReportTime(System.currentTimeMillis());

			Summary summary = getSummary(holder);
			report.setSummary(summary);

			Completion completion = getCompletion(holder);
			report.setCompletion(completion);

			List<Video> videos = getVideos(holder);
			report.setVideos(videos);

			Interview interview = getInterview(holder.position.getEmployerId(),
					holder.position.getTestType(),
					holder.candidateTest.getTestId());
			report.setInterview(interview);

			getParts(holder);

			// 设置异常图片
			Page page = new Page();
			page.setPageSize(8);
			List<String> abnormalUrls = candidateTestDao.getTestMonitor(
					candidateTest.getTestId(), 1, page);
			report.setAbnormalUrls(abnormalUrls);

			// 在职位日志中插入记录
			update(holder, true);
			return true;
		} catch (InvalidDataException e) {
			logger.error(TraceManager.getTrace(e));
			report.setErrorCode(e.getErrCode());
			report.setErrorDesc(e.getErrDesc());
			update(holder, false);
			return false;
		} catch (Exception e) {
			String errorDesc = TraceManager.getTrace(e);
			logger.error(errorDesc);
			report.setErrorCode(BaseResponse.EDIAGNOSTIC);
			report.setErrorDesc(errorDesc);
			update(holder, false);
			return false;
		}
	}

	/**
	 * 获取职位
	 * 
	 * @param holder
	 *            临时对象
	 */
	private void loadPosition(Holder holder) throws InvalidDataException {
		holder.position = positionDao.getEntity(holder.candidateTest
				.getPositionId());
		if (holder.position == null) {
			throw new InvalidDataException(BaseResponse.ENOENT,
					"找不到职位，positionId=" + holder.candidateTest.getPositionId());
		}
	}

	/**
	 * 获取候选人信息
	 * 
	 * @param holder
	 *            临时对象
	 */
	private void loadCandidate(Holder holder) throws InvalidDataException {
		holder.candidate = candidateDao.getEntity(holder.candidateTest
				.getCandidateId());
		if (holder.candidate == null) {
			throw new InvalidDataException(BaseResponse.ENOENT,
					"找不到候选人信息，candidateId="
							+ holder.candidateTest.getCandidateId());
		}
	}

	/**
	 * 获取试卷
	 * 
	 * @param holder
	 *            临时对象
	 * @param testId
	 *            试卷实例ID
	 * @throws InvalidDataException
	 */
	private void loadTestQuestions(Holder holder, long testId)
			throws InvalidDataException {
		holder.testQuestions = new ArrayList<TestQuestion>();
		holder.testQuestionMap = new HashMap<Long, TestQuestion>();

		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(testId);
		for (CandidateTestQuestion candidateTestQuestion : candidateTestQuestions) {
			TestQuestion testQuestion = new TestQuestion(candidateTestQuestion);
			if (testQuestion.getQuestionInfo().isSample())
				continue;

			holder.testQuestions.add(testQuestion);
			holder.testQuestionMap.put(candidateTestQuestion.getId()
					.getQuestionId(), testQuestion);
		}
	}

	/**
	 * 获取试卷
	 * 
	 * @param holder
	 *            临时对象
	 * @param testId
	 *            试卷实例ID
	 * @throws InvalidDataException
	 */
	private void loadExamQuestions(Holder holder, long testId)
			throws InvalidDataException {
		holder.examQuestions = new ArrayList<CandidateExamQuestion>();
		List<CandidateExamQuestion> candidateExamQuestions = candidateExamQuestionDao
				.getList(testId);
		for (CandidateExamQuestion candidateExamQuestion : candidateExamQuestions) {
			if (candidateExamQuestion.isSample())
				continue;

			holder.examQuestions.add(candidateExamQuestion);
		}
	}

	/**
	 * 加载候选人扩展信息
	 * 
	 * @param holder
	 *            临时对象
	 */
	private void loadCandidateInfoExt(Holder holder) {
		holder.candidateInfoExtMap = candidateInfoExtDao
				.getMap(holder.candidateTest.getCandidateId());
	}

	/**
	 * 加载试卷技能
	 * 
	 * @param holder
	 *            临时对象
	 */
	private void loadPaperSkill(Holder holder) {
		List<PaperSkill> paperSkills = paperSkillDao
				.getList(holder.candidateTest.getPaperId());

		holder.paperSkillSet = new HashSet<String>();
		for (PaperSkill paperSkill : paperSkills) {
			holder.paperSkillSet.add(paperSkill.getId().getSkillId());
		}

		holder.intelPaperSkillSet = new HashSet<String>();
	}

	/**
	 * 设置概述
	 * 
	 * @param holder
	 *            临时对象
	 * @return
	 * @throws Exception
	 */
	private Summary getSummary(Holder holder) throws Exception {
		// 设置候选人基本信息
		Summary summary = new Summary();
		summary.setName(holder.candidate.getCandidateName());
		summary.setEmail(holder.candidate.getCandidateEmail());
		summary.setPortrait(holder.candidateTest.getCandidatePic());

		// 设置候选人通用属性
		List<UserInfo> infos = getUserInfos(holder);
		summary.setInfos(infos);

		// 获取技能分值统计
		getScoreMap(holder);
		getSkills(holder);

		// 设置技能名称
		List<String> skillNames = getSkillNames(holder);
		if (!skillNames.isEmpty())
			summary.setSkills(skillNames);

		List<String> intelSkillNames = getIntelSkillNames(holder);
		if (!intelSkillNames.isEmpty())
			summary.setIntelSkills(intelSkillNames);

		// 设置得分描述对应的分值
		summary.setScoreRanges(SCORE_RANGES);
		summary.setIntelScoreRanges(SCORE_RANGES);

		// 设置技能相关的分值
		if (!holder.scoreMap.isEmpty()) {
			List<Double> skillScores = new ArrayList<Double>();
			List<Double> avgScores = new ArrayList<Double>();

			for (String skill : holder.skills) {
				SkillScore skillScore = holder.scoreMap.get(skill);
				skillScores.add(skillScore.getScores());
				avgScores.add(GradeConst.AVG_SCALE);
			}

			summary.setSkillScores(skillScores);
			summary.setAvgScores(avgScores);
		}

		if (!holder.intelScoreMap.isEmpty()) {
			List<Double> skillScores = new ArrayList<Double>();
			List<Double> avgScores = new ArrayList<Double>();

			for (String skill : holder.intelSkills) {
				SkillScore skillScore = holder.intelScoreMap.get(skill);
				skillScores.add(skillScore.getScores());
				avgScores.add(GradeConst.AVG_SCALE);
			}

			summary.setIntelSkillScores(skillScores);
			summary.setIntelAvgScores(avgScores);
		}

		// 设置得分
		Double standardBasicScore = null;
		Double standardSysBasicScore = null;
		Double standardUserBasicScore = null;
		Double standardProgramScore = null;
		double score = 0.0;
		int count = 0;

		if (holder.basicPoint > 1e-6) {
			standardBasicScore = MathUtils.round(holder.basicScore
					* GradeConst.SCORE_SCALE / holder.basicPoint, 1);
			score += standardBasicScore;
			count++;
		}

		if (holder.sysBasicPoint > 1e-6) {
			standardSysBasicScore = MathUtils.round(holder.sysBasicScore
					* GradeConst.SCORE_SCALE / holder.sysBasicPoint, 1);
		}

		if (holder.userBasicPoint > 1e-6) {
			standardUserBasicScore = MathUtils.round(holder.userBasicScore
					* GradeConst.SCORE_SCALE / holder.userBasicPoint, 1);
		}

		if (holder.programPoint > 1e-6) {
			standardProgramScore = MathUtils.round(holder.programScore
					* GradeConst.SCORE_SCALE / holder.programPoint, 1);
			score += standardProgramScore;
			count++;
		}

		// 精确到小数点后一位
		if (count > 0)
			summary.setScore(MathUtils.round(score / count, 1));
		summary.setBasicScore(standardBasicScore);
		summary.setSysBasicScore(standardSysBasicScore);
		summary.setUserBasicScore(standardUserBasicScore);
		summary.setBreakTimes(holder.candidateTest.getBreakTimes());
		summary.setSwitchTimes(holder.candidateTest.getSwitchTimes());
		return summary;
	}

	/**
	 * 设置候选人通用属性
	 * 
	 * @param holder
	 *            临时对象
	 * @return
	 */
	private List<UserInfo> getUserInfos(Holder holder) {
		List<UserInfo> infos = new ArrayList<UserInfo>();

		// 根据招聘人关心的信息，设置用户信息
		List<PositionInfoExt> positionInfoExts = positionInfoExtDao.getList(
				holder.position.getEmployerId(),
				holder.position.getPositionId());
		if (positionInfoExts == null || positionInfoExts.isEmpty()) {
			positionInfoExts = positionInfoExtDao.getList(
					holder.position.getEmployerId(), -1);
		}

		for (PositionInfoExt positionInfoExt : positionInfoExts) {
			String infoId = positionInfoExt.getId().getInfoId();
			String realValue = holder.candidateInfoExtMap.get(infoId);
			if (realValue == null) // 找不到候选人信息则忽略
				continue;

			UserInfo userInfo = new UserInfo();
			userInfo.setKey(positionInfoExt.getInfoName());
			userInfo.setValue(realValue);
			infos.add(userInfo);
		}

		if (infos.isEmpty())
			return null;

		return infos;
	}

	/**
	 * 获取技能列表
	 * 
	 * @param holder
	 *            临时对象
	 * 
	 * @return
	 */
	private void getSkills(Holder holder) {
		holder.skills = new ArrayList<String>();
		holder.intelSkills = new ArrayList<String>();

		Iterator<String> iter = holder.paperSkillSet.iterator();
		while (iter.hasNext()) {
			String skillId = iter.next();
			// 检查该技能是否有题
			if (!holder.scoreMap.containsKey(skillId))
				continue;

			// 设置标签
			holder.skills.add(skillId);
		}

		iter = holder.intelPaperSkillSet.iterator();
		while (iter.hasNext()) {
			String skillId = iter.next();
			// 检查该技能是否有题
			if (!holder.intelScoreMap.containsKey(skillId))
				continue;

			// 设置标签
			holder.intelSkills.add(skillId);
		}
	}

	/**
	 * 删除不关心的技能
	 * 
	 * @param holder
	 *            临时对象
	 * 
	 * @param skillSet
	 *            技能集合
	 */
	private void setTestSkills(Holder holder) {
		for (TestQuestion testQuestion : holder.testQuestions) {
			List<QbQuestionSkill> qbQuestionSkills = qbQuestionSkillDao
					.getSkills(testQuestion.getQuestion().getId()
							.getQuestionId());
			if (qbQuestionSkills == null) {
				testQuestion.setTestSkills(null);
				continue;
			}

			List<String> testSkills = new ArrayList<String>();
			if (testQuestion.getQuestionInfo().getCategory() == GradeConst.CATEGORY_INTELLIGENCE) {
				for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
					String skillId = qbQuestionSkill.getId().getSkillId();
					testSkills.add(skillId);
					holder.intelPaperSkillSet.add(skillId);
				}
			} else {
				for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
					// 如果是百一题库，则需要判断该技能用户是否关心
					if (testQuestion.getQuestionInfo().isPrebuilt()
							&& !holder.paperSkillSet.contains(qbQuestionSkill
									.getId().getSkillId()))
						continue;

					String skillId = qbQuestionSkill.getId().getSkillId();
					testSkills.add(skillId);
					holder.paperSkillSet.add(skillId);
				}
			}

			if (testSkills.isEmpty())
				testQuestion.setTestSkills(null);
			else
				testQuestion.setTestSkills(testSkills);
		}
	}

	/**
	 * 获取技能的名称
	 * 
	 * @param holder
	 *            临时对象
	 * @return
	 */
	private List<String> getSkillNames(Holder holder) {
		List<String> skillNames = new ArrayList<String>();

		for (String skill : holder.skills) {
			QbSkill qbSkill = qbSkillDao.getEntity(skill);
			if (qbSkill == null)
				skillNames.add(skill);
			else
				skillNames.add(qbSkill.getSkillName());
		}

		return skillNames;
	}

	/**
	 * 获取技能的名称
	 * 
	 * @param holder
	 *            临时对象
	 * @return
	 */
	private List<String> getIntelSkillNames(Holder holder) {
		List<String> skillNames = new ArrayList<String>();

		for (String skill : holder.intelSkills) {
			QbSkill qbSkill = qbSkillDao.getEntity(skill);
			if (qbSkill == null)
				skillNames.add(skill);
			else
				skillNames.add(qbSkill.getSkillName());
		}

		return skillNames;
	}

	/**
	 * 获取技能分值统计(副作用：设置总分、得分)
	 * 
	 * @param holder
	 *            临时对象
	 * 
	 * @return
	 * @throws InvalidDataException
	 */
	private void getScoreMap(Holder holder) throws InvalidDataException {
		holder.scoreMap = new HashMap<String, SkillScore>();
		holder.intelScoreMap = new HashMap<String, SkillScore>();

		for (TestQuestion testQuestion : holder.testQuestions) {
			QuestionInfo questionInfo = testQuestion.getQuestionInfo();
			CandidateTestQuestion question = testQuestion.getQuestion();

			if (questionInfo.getCategory() == GradeConst.CATEGORY_INTELLIGENCE) {
				holder.intelPoint += questionInfo.getPoint();
				holder.intelSpentTime += question.getAnswerTime();
			} else if (questionInfo.getCategory() == GradeConst.CATEGORY_TECHNOLOGY) {
				switch (questionInfo.getQuestionType()) {
				case GradeConst.QUESTION_TYPE_S_CHOICE:
				case GradeConst.QUESTION_TYPE_M_CHOICE:
					if (questionInfo.isPrebuilt()) {
						if (question.getGetScore() != null)
							holder.sysBasicScore += question.getGetScore();
						holder.sysBasicPoint += questionInfo.getPoint();
					} else {
						if (question.getGetScore() != null)
							holder.userBasicScore += question.getGetScore();
						holder.userBasicPoint += questionInfo.getPoint();
					}
					holder.choiceSpentTime += question.getAnswerTime();
					break;
				case GradeConst.QUESTION_TYPE_PROGRAM:
					if (question.getGetScore() != null)
						holder.programScore += question.getGetScore();
					holder.programPoint += questionInfo.getPoint();
					break;
				default:
					continue;
				}
			} else {
				continue;
			}

			List<String> testSkills = testQuestion.getTestSkills();
			if (testSkills == null)
				continue;

			double point = (double) questionInfo.getPoint() / testSkills.size();
			double score;
			if (question.getGetScore() == null)
				score = 0.0;
			else
				score = (double) question.getGetScore() / testSkills.size();
			double avgScore;
			StatQuestionTest statQuestionTest = statQuestionTestDao.getById(
					question.getId().getQuestionId(),
					holder.position.getLevel());
			if (statQuestionTest == null) {
				avgScore = point / 2.0; // 假定平均分为一半
			} else {
				avgScore = statQuestionTest.getTotalScore()
						/ statQuestionTest.getTotalNum();
				if (avgScore > point)
					avgScore = point;
			}
			avgScore /= testSkills.size();

			Map<String, SkillScore> scoreMap;
			if (questionInfo.getCategory() == GradeConst.CATEGORY_INTELLIGENCE)
				scoreMap = holder.intelScoreMap;
			else
				scoreMap = holder.scoreMap;

			for (String skill : testSkills) {
				SkillScore skillScore = scoreMap.get(skill);
				if (skillScore == null) {
					skillScore = new SkillScore();
					scoreMap.put(skill, skillScore);

					skillScore.setPoints(point);
					skillScore.setScores(score);
					skillScore.setAvgScores(avgScore);
				} else {
					skillScore.setPoints(skillScore.getPoints() + point);
					skillScore.setScores(skillScore.getScores() + score);
					skillScore.setAvgScores(skillScore.getAvgScores()
							+ avgScore);
				}
			}
		}

		holder.basicScore = holder.sysBasicScore + holder.userBasicScore;
		holder.basicPoint = holder.sysBasicPoint + holder.userBasicPoint;

		if (holder.basicPoint > 1e-6) {
			// 按分值排序
			TreeSet<OrderedSkill> orderedSet = new TreeSet<OrderedSkill>();

			// 对分值标准化
			for (Entry<String, SkillScore> entry : holder.scoreMap.entrySet()) {
				SkillScore skillScore = entry.getValue();
				double rate = GradeConst.SCORE_SCALE / skillScore.getPoints();
				double scores = skillScore.getScores() * rate;
				double avgScores = skillScore.getAvgScores() * rate;
				double points = GradeConst.SCORE_SCALE;

				skillScore.setScores(MathUtils.round(scores, 1));
				skillScore.setAvgScores(MathUtils.round(avgScores, 1));
				skillScore.setPoints(points);

				OrderedSkill orderedSkill = new OrderedSkill();
				orderedSkill.setScore(skillScore.getScores());
				orderedSkill.setSkillId(entry.getKey());
				orderedSet.add(orderedSkill);
			}

			// 保存技能得分
			saveSkillScores(holder, holder.scoreMap);
		}

		if (holder.intelPoint > 1e-6) {
			// 按分值排序
			TreeSet<OrderedSkill> orderedSet = new TreeSet<OrderedSkill>();

			// 对分值标准化
			for (Entry<String, SkillScore> entry : holder.intelScoreMap
					.entrySet()) {
				SkillScore skillScore = entry.getValue();
				double rate = GradeConst.SCORE_SCALE / skillScore.getPoints();
				double scores = skillScore.getScores() * rate;
				double avgScores = skillScore.getAvgScores() * rate;
				double points = GradeConst.SCORE_SCALE;

				skillScore.setScores(MathUtils.round(scores, 1));
				skillScore.setAvgScores(MathUtils.round(avgScores, 1));
				skillScore.setPoints(points);

				OrderedSkill orderedSkill = new OrderedSkill();
				orderedSkill.setScore(skillScore.getScores());
				orderedSkill.setSkillId(entry.getKey());
				orderedSet.add(orderedSkill);
			}

			// 保存技能得分
			saveSkillScores(holder, holder.intelScoreMap);
		}
	}

	/**
	 * 获取完整度
	 * 
	 * @param holder
	 *            临时对象
	 * 
	 * @return
	 */
	private Completion getCompletion(Holder holder) {
		Completion completion = new Completion();
		List<Double> skillRatios = new ArrayList<Double>();
		List<Integer> skillTimes = new ArrayList<Integer>();
		List<Integer> avgSkillTimes = new ArrayList<Integer>();
		List<Double> intelSkillRatios = new ArrayList<Double>();
		List<Integer> intelSkillTimes = new ArrayList<Integer>();
		List<Integer> intelAvgSkillTimes = new ArrayList<Integer>();
		Map<String, CompletionStat> completionMap = new HashMap<String, CompletionStat>();

		for (TestQuestion testQuestion : holder.testQuestions) {
			List<String> testSkills = testQuestion.getTestSkills();
			if (testSkills == null) // 该题没有技能
				continue;

			QuestionInfo questionInfo = testQuestion.getQuestionInfo();

			// 只有技术题、智力题才进行统计
			if (questionInfo.getCategory() != GradeConst.CATEGORY_TECHNOLOGY
					&& questionInfo.getCategory() != GradeConst.CATEGORY_INTELLIGENCE)
				continue;

			// 只有选择题才进行统计
			int questionType = questionInfo.getQuestionType();
			if (questionType != GradeConst.QUESTION_TYPE_S_CHOICE
					&& questionType != GradeConst.QUESTION_TYPE_M_CHOICE
					&& questionType != GradeConst.QUESTION_TYPE_S_CHOICE_PLUS
					&& questionType != GradeConst.QUESTION_TYPE_M_CHOICE_PLUS)
				continue;

			CandidateTestQuestion question = testQuestion.getQuestion();
			double point = (double) questionInfo.getPoint() / testSkills.size();
			StatQuestionTest statQuestionTest = statQuestionTestDao.getById(
					question.getId().getQuestionId(),
					holder.position.getLevel());

			for (String skill : testSkills) {
				CompletionStat completionStat = completionMap.get(skill);
				if (completionStat == null) {
					completionStat = new CompletionStat();
					completionMap.put(skill, completionStat);
				}

				completionStat.setQuestions(completionStat.getQuestions() + 1);
				completionStat.setSkillScore(completionStat.getSkillScore()
						+ point);
				completionStat.setSkillTime(completionStat.getSkillTime()
						+ question.getAnswerTime());
				if (statQuestionTest == null) {
					completionStat.setAvgSkillTime(completionStat
							.getAvgSkillTime() + question.getAnswerTime());
				} else {
					completionStat.setAvgSkillTime(completionStat
							.getAvgSkillTime()
							+ (double) statQuestionTest.getTotalSpendTime()
							/ statQuestionTest.getTotalNum());
				}
			}
		}

		// 设置值
		for (int i = 0; i < holder.skills.size(); i++) {
			String skill = holder.skills.get(i);
			CompletionStat completionStat = completionMap.get(skill);
			double skillRatio;
			int skillTime;
			int avgSkillTime;

			if (completionStat == null) {
				skillRatio = 0;
				skillTime = 0;
				avgSkillTime = 0;
			} else {
				skillRatio = MathUtils.round(completionStat.getSkillScore()
						/ holder.basicPoint, 3);
				skillTime = (int) (completionStat.getSkillTime()
						/ completionStat.getQuestions() + 0.5);
				avgSkillTime = (int) (completionStat.getAvgSkillTime()
						/ completionStat.getQuestions() + 0.5);
			}

			skillRatios.add(skillRatio);
			skillTimes.add(skillTime);
			avgSkillTimes.add(avgSkillTime);
		}

		// 设置值
		for (int i = 0; i < holder.intelSkills.size(); i++) {
			String skill = holder.intelSkills.get(i);
			CompletionStat completionStat = completionMap.get(skill);
			double skillRatio;
			int skillTime;
			int avgSkillTime;

			if (completionStat == null) {
				skillRatio = 0;
				skillTime = 0;
				avgSkillTime = 0;
			} else {
				skillRatio = MathUtils.round(completionStat.getSkillScore()
						/ holder.intelPoint, 3);
				skillTime = (int) (completionStat.getSkillTime()
						/ completionStat.getQuestions() + 0.5);
				avgSkillTime = (int) (completionStat.getAvgSkillTime()
						/ completionStat.getQuestions() + 0.5);
			}

			intelSkillRatios.add(skillRatio);
			intelSkillTimes.add(skillTime);
			intelAvgSkillTimes.add(avgSkillTime);
		}

		if (!skillRatios.isEmpty())
			completion.setSkillRatios(skillRatios);
		if (!skillTimes.isEmpty())
			completion.setSkillTimes(skillTimes);
		if (!avgSkillTimes.isEmpty())
			completion.setAvgSkillTimes(avgSkillTimes);
		if (!intelSkillRatios.isEmpty())
			completion.setIntelSkillRatios(intelSkillRatios);
		if (!intelSkillTimes.isEmpty())
			completion.setIntelSkillTimes(intelSkillTimes);
		if (!intelAvgSkillTimes.isEmpty())
			completion.setIntelAvgSkillTimes(intelAvgSkillTimes);
		completion.setSpentTime(holder.choiceSpentTime);
		completion.setIntelSpentTime(holder.intelSpentTime);

		return completion;
	}

	/**
	 * 设置面试题
	 * 
	 * @param holder
	 *            临时对象
	 * @return
	 */
	public List<Video> getVideos(Holder holder) {
		List<Video> videos = new ArrayList<Video>();

		for (TestQuestion testQuestion : holder.testQuestions) {
			QuestionInfo questionInfo = testQuestion.getQuestionInfo();
			if (questionInfo.getCategory() != GradeConst.CATEGORY_INTERVIEW) // 不是面试题，忽略
				continue;

			Video video = new Video();
			videos.add(video);

			CandidateTestQuestion question = testQuestion.getQuestion();
			QbQuestion qbQuestion = qbQuestionDao.getEntity(question.getId()
					.getQuestionId());
			video.setTitle(qbQuestion.getQuestionDesc());
			video.setUrl(question.getVideoFile());
		}

		if (videos.isEmpty())
			return null;

		return videos;
	}

	/**
	 * 获取面试相关信息
	 * 
	 * @param employerId
	 *            招聘人ID
	 * @param testType
	 *            测试类型
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public Interview getInterview(int employerId, int testType, long testId)
			throws Exception {
		Interview interview = new Interview();

		InterviewInfo interviewInfo = reportService.getInterviewInfo(
				employerId, testType);
		interview.setInterviewInfo(interviewInfo);

		return interview;
	}

	/**
	 * 获取附加题
	 * 
	 * @param holder
	 *            临时对象
	 * 
	 * @throws Exception
	 */
	private void getParts(Holder holder) throws Exception {
		Part[] parts = new Part[IReportConfig.ITEM_NAMES.length];
		Map<Long, Integer> reportIndexMap = new HashMap<Long, Integer>();
		holder.report.setReportIndexMap(reportIndexMap);

		for (CandidateExamQuestion examQuestion : holder.examQuestions) {
			// 获取配置信息
			QuestionContent questionContent = gson.fromJson(
					examQuestion.getContent(), QuestionContent.class);
			QuestionConf questionConf = questionContent.getQuestionConf();

			int index = reportConfig.getReportIndex(
					examQuestion.getQuestionType(), questionConf.getCategory());
			reportIndexMap.put(examQuestion.getCandidateExamQuestionPK()
					.getQuestionId(), index);
			boolean prebuilt = false;

			QbQuestion qbQuestion = qbQuestionDao.getEntity(examQuestion
					.getCandidateExamQuestionPK().getQuestionId());
			switch (index) {
			case IReportConfig.REPORT_COLUMN_BASIC:
				if (qbQuestion.getPrebuilt() == 1
						&& holder.position.getSample() != 1)
					continue;
				break;
			case IReportConfig.REPORT_COLUMN_QUALITY:
			case IReportConfig.REPORT_COLUMN_UNKNOWN:
				continue;
			default:
				prebuilt = (qbQuestion.getPrebuilt() == 1);
				break;
			}

			Part part = parts[index];
			if (part == null) {
				part = new Part(index);
				parts[index] = part;
			}

			PartItem partItem = new PartItem();
			part.getPartItems().add(partItem);

			// 获取答题时间
			long qid = examQuestion.getCandidateExamQuestionPK()
					.getQuestionId();
			CandidateTestQuestion question = holder.testQuestionMap.get(qid)
					.getQuestion();
			partItem.setAnswerTime(question.getAnswerTime());

			// 设置基本信息
			partItem.setQuestionId(qid);
			partItem.setQuestionType(examQuestion.getQuestionType());
			partItem.setReadonly(question.getAnswerFlag() == 0);
			partItem.setHtml(qbQuestion.getHtml() == 1);
			if (questionConf.getSummary() != null)
				partItem.setTitle(questionConf.getSummary());
			else
				partItem.setTitle(questionConf.getTitle());
			partItem.setMode(questionConf.getMode());

			// 获取平均时间
			StatQuestionTest statQuestionTest = statQuestionTestDao.getById(
					question.getId().getQuestionId(),
					holder.position.getLevel());
			if (statQuestionTest == null) {
				partItem.setAvgAnswerTime(question.getAnswerTime());
			} else {
				partItem.setAvgAnswerTime(statQuestionTest.getTotalSpendTime()
						/ statQuestionTest.getTotalNum());
			}

			partItem.setPrebuilt(prebuilt);

			// 设置得分
			if (question.getGetScore() != null)
				partItem.setScore(question.getGetScore());

			// 设置答案与参考答案
			AnswerWrapper answerWrapper = null;
			if (examQuestion.getAnswer() != null) {
				answerWrapper = gson.fromJson(examQuestion.getAnswer(),
						AnswerWrapper.class);
			}

			switch (questionConf.getTypeInt()) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
				partItem.setOptions(questionConf.getOptions());
				partItem.setOptRefAnswer(questionConf.getOptAnswers());
				partItem.setRefAnswer(questionConf.getOptDesc());
				if (answerWrapper != null) {
					partItem.setOptAnswer(answerWrapper.getOptAnswer());
					partItem.setAnswer(answerWrapper.getOptDesc());
				}
				break;
			case GradeConst.QUESTION_TYPE_PROGRAM:
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			case GradeConst.QUESTION_TYPE_ESSAY:
				FileInfo fileInfo = QuestionUtils.getFileInfo(questionContent);
				if (fileInfo != null) {
					partItem.setRefAnswer(fileInfo.getRefAnswer());
					partItem.setAnswer(fileInfo.getAnswer());
				}

				if (answerWrapper != null) {
					logger.debug("answerWrapper.getAnswer(fileInfo.getFilename()) is " + answerWrapper.getAnswer(fileInfo.getFilename()));
					if(StringUtils.isEmpty(answerWrapper.getAnswer(fileInfo
							.getFilename()))){
						partItem.setAnswer(answerWrapper.getOptDesc());
					}else{
						partItem.setAnswer(answerWrapper.getAnswer(fileInfo
							.getFilename()));
					}
					
				}
				break;
			}

			if (questionConf.getCategory() == GradeConst.CATEGORY_TECHNOLOGY
					|| questionConf.getCategory() == GradeConst.CATEGORY_INTELLIGENCE) {
				List<QbQuestionSkill> qbQuestionSkills = qbQuestionSkillDao
						.getSkills(qid);
				if (qbQuestionSkills != null && !qbQuestionSkills.isEmpty()) {
					for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
						if (qbQuestionSkill.getSkillSeq() != 1)
							continue;

						QbSkill qbSkill = qbSkillDao.getEntity(qbQuestionSkill
								.getId().getSkillId());
						if (qbSkill != null)
							partItem.setSkillName(qbSkill.getSkillName());
						break;
					}
				}
			}
		}

		List<Part> partList = new ArrayList<Part>();
		for (Part part : parts) {
			if (part == null)
				continue;

			part.sort();
			partList.add(part);
		}

		if (!partList.isEmpty())
			holder.report.setParts(partList);
	}

	/**
	 * 保存技能得分
	 * 
	 * @param holder
	 *            临时对象
	 */
	private void saveSkillScores(Holder holder, Map<String, SkillScore> scoreMap) {
		for (Entry<String, SkillScore> entry : scoreMap.entrySet()) {
			SkillScore skillScore = entry.getValue();

			CandidateTestScore candidateTestScore = new CandidateTestScore();
			CandidateTestScoreId candidateTestScoreId = new CandidateTestScoreId();

			candidateTestScoreId.setTestId(holder.candidateTest.getTestId());
			candidateTestScoreId.setSkillId(entry.getKey());
			candidateTestScore.setId(candidateTestScoreId);
			candidateTestScore.setCandidateId(holder.candidateTest
					.getCandidateId());
			candidateTestScore.setEmployerId(holder.position.getEmployerId());
			candidateTestScore.setScore(skillScore.getScores());
			candidateTestScoreDao.saveOrUpdate(candidateTestScore);
		}
	}

	/**
	 * 更新数据库
	 * 
	 * @param holder
	 *            临时对象
	 * @param success
	 * @throws UnsupportedEncodingException
	 */
	private void update(Holder holder, boolean success)
			throws UnsupportedEncodingException {
		CandidateReport candidateReport = new CandidateReport();
		candidateReport.setCandidateId(holder.candidateTest.getCandidateId());
		candidateReport.setReportPassport(Utils.getPassport());
		candidateReport.setTestId(holder.candidateTest.getTestId());
		candidateReport.setEmployerId(holder.position.getEmployerId());
		candidateReport.setReportDate(new Timestamp(holder.report
				.getReportTime()));
		candidateReport.setReportFile(null);
		candidateReport.setReportState(0);

		Summary summary = holder.report.getSummary();
		if (summary == null)
			summary = new Summary();

		candidateReport.setGetScore(summary.getScore());
		candidateReport
				.setReportDate(new Timestamp(System.currentTimeMillis()));
		candidateReport.setContent(gson.toJson(holder.report));
		candidateReport.setNotified(0);
		candidateReportDao.saveOrUpdate(candidateReport);

		// 在职位日志中插入记录
		if (success) {
			PositionLog positionLog = new PositionLog();
			positionLog.setPositionId(holder.position.getPositionId());
			positionLog.setStateId(candidateReport.getTestId());
			positionLog.setPositionState(2);
			positionLog.setEmployerId(holder.position.getEmployerId());
			positionLog.setLogTime(new Timestamp(System.currentTimeMillis()));
			positionLogDao.save(positionLog);
		}
	}

	private static class SkillScore {
		private double scores;
		private double avgScores;
		private double points;

		public double getScores() {
			return scores;
		}

		public void setScores(double scores) {
			this.scores = scores;
		}

		public double getAvgScores() {
			return avgScores;
		}

		public void setAvgScores(double avgScores) {
			this.avgScores = avgScores;
		}

		public double getPoints() {
			return points;
		}

		public void setPoints(double points) {
			this.points = points;
		}
	}

	private static class QuestionInfo {
		private boolean sample;
		private int questionType;
		private int category;
		private int point;
		private boolean prebuilt;

		public boolean isSample() {
			return sample;
		}

		public void setSample(boolean sample) {
			this.sample = sample;
		}

		public int getQuestionType() {
			return questionType;
		}

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public int getCategory() {
			return category;
		}

		public void setCategory(int category) {
			this.category = category;
		}

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
		}

		public boolean isPrebuilt() {
			return prebuilt;
		}

		public void setPrebuilt(boolean prebuilt) {
			this.prebuilt = prebuilt;
		}
	}

	private class TestQuestion {
		private CandidateTestQuestion question;
		private QuestionInfo questionInfo;
		private List<String> testSkills;

		public TestQuestion(CandidateTestQuestion question)
				throws InvalidDataException {
			this.question = question;

			long qid = question.getId().getQuestionId();
			QbQuestion qbQuestion = qbQuestionDao.getEntity(qid);
			if (qbQuestion == null) {
				throw new InvalidDataException(BaseResponse.ENOENT,
						"找不到试卷对应的详细信息，qid=" + qid);
			}

			questionInfo = new QuestionInfo();
			questionInfo.setSample(qbQuestion.getIsSample() == 1);
			questionInfo.setQuestionType(GradeConst
					.toQuestionTypeInt(qbQuestion.getQuestionType()));
			questionInfo.setCategory(qbQuestion.getCategory());
			questionInfo.setPoint(qbQuestion.getPoint());
			questionInfo.setPrebuilt(qbQuestion.getPrebuilt() == 1);
		}

		public CandidateTestQuestion getQuestion() {
			return question;
		}

		public QuestionInfo getQuestionInfo() {
			return questionInfo;
		}

		public List<String> getTestSkills() {
			return testSkills;
		}

		public void setTestSkills(List<String> testSkills) {
			this.testSkills = testSkills;
		}
	}

	private static class CompletionStat {
		private int questions;
		private double skillScore;
		private double skillTime;
		private double avgSkillTime;

		public CompletionStat() {
			questions = 0;
			skillScore = 0;
			skillTime = 0;
			avgSkillTime = 0;
		}

		public int getQuestions() {
			return questions;
		}

		public void setQuestions(int questions) {
			this.questions = questions;
		}

		public double getSkillScore() {
			return skillScore;
		}

		public void setSkillScore(double skillScore) {
			this.skillScore = skillScore;
		}

		public double getSkillTime() {
			return skillTime;
		}

		public void setSkillTime(double skillTime) {
			this.skillTime = skillTime;
		}

		public double getAvgSkillTime() {
			return avgSkillTime;
		}

		public void setAvgSkillTime(double avgSkillTime) {
			this.avgSkillTime = avgSkillTime;
		}
	}

	public static class OrderedSkill implements Comparable<OrderedSkill> {
		private double score;
		private String skillId;

		public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}

		public String getSkillId() {
			return skillId;
		}

		public void setSkillId(String skillId) {
			this.skillId = skillId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(score);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((skillId == null) ? 0 : skillId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderedSkill other = (OrderedSkill) obj;
			if (Double.doubleToLongBits(score) != Double
					.doubleToLongBits(other.score))
				return false;
			if (skillId == null) {
				if (other.skillId != null)
					return false;
			} else if (!skillId.equals(other.skillId))
				return false;
			return true;
		}

		@Override
		public int compareTo(OrderedSkill o) {
			if (score < o.score)
				return -1;
			else if (score > o.score)
				return 1;

			return skillId.compareTo(o.skillId);
		}
	}

	private static class Holder {
		public Report report; // 报告
		public Position position; // 职位
		public CandidateTest candidateTest; // 候选人测试
		public Candidate candidate; // 候选人
		public List<TestQuestion> testQuestions; // 试卷
		public Map<Long, TestQuestion> testQuestionMap; // 试卷映射
		public List<CandidateExamQuestion> examQuestions; // 判卷相关试卷
		public Map<String, String> candidateInfoExtMap; // 候选人扩展信息
		public Set<String> paperSkillSet; // 试卷关系的技能映射
		public Map<String, SkillScore> scoreMap; // 技能与分值映射
		public List<String> skills; // 技能列表
		public Set<String> intelPaperSkillSet; // 试卷关系的技能映射
		public List<String> intelSkills; // 技能列表
		public Map<String, SkillScore> intelScoreMap; // 技能与分值映射
		public double sysBasicScore; // 百一基础得分
		public double sysBasicPoint; // 百一基础总分
		public double userBasicScore; // 用户基础得分
		public double userBasicPoint; // 用户基础总分
		public double basicScore; // 基础得分
		public double basicPoint; // 基础总分
		public double programScore; // 编程题得分
		public double programPoint; // 编程题总分
		public double intelPoint; // 基础总分
		public int choiceSpentTime; // 选择题花费时间
		public int intelSpentTime; // 智力题花费时间

		public Holder() {
			report = new Report();
		}
	}

}
