package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.ILoadService;
import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.QuestionTypeCountInfo;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.PFCountInfo;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.empl.service.IQbBase;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.param.GetQbBasesParam;
import com.ailk.sets.platform.intf.model.param.GetQbGroupsParam;
import com.ailk.sets.platform.intf.model.param.GetQbQuestionsParam;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbBaseInfo;
import com.ailk.sets.platform.intf.model.qb.QbBaseResponse;
import com.ailk.sets.platform.intf.model.qb.QbProLangInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.model.qb.QbSkillInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkillResponse;
import com.ailk.sets.platform.intf.model.qb.QbSkillStatistics;
import com.ailk.sets.platform.intf.model.question.QbGroupInfo;
import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;
import com.ailk.sets.platform.util.PrettyTimeMaker;

@Transactional(rollbackFor = Exception.class)
public class QbBaseImpl implements IQbBase {

	private static final Logger logger = LoggerFactory
			.getLogger(QbBaseImpl.class);

	@Autowired
	private IQbBaseDao qbBaseDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IConfigDao configDao;

	@Autowired
	private ILoadService loadService;
	@Autowired
	private IPaperDao paperDao;

	@Override
	public QbBaseResponse createQbBase(QbBase qbBase) throws PFServiceException {
		QbBaseResponse qbBaseResponse = new QbBaseResponse();
		try {
			qbBase.setQbId(qbBaseDao.getUIDFromBase(Constants.QB_ID).intValue());
			qbBase.setCreateDate(new Timestamp(System.currentTimeMillis()));
			qbBase.setModifyDate(qbBase.getCreateDate());
			qbBase.setPrebuilt(0);
			if (qbBase.getCategory() == null)
				qbBase.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
			qbBaseDao.save(qbBase);

			qbBaseResponse.setCode(FuncBaseResponse.SUCCESS);
			qbBaseResponse.setQbId(qbBase.getQbId());
			return qbBaseResponse;
		} catch (Exception e) {
			logger.error("call createQbBase error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public String getQbName(int qbId) {
		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null)
			return null;

		return qbBase.getQbName();
	}

	@Override
	public List<QbBaseInfo> getQbBases(GetQbBasesParam param)
			throws PFServiceException {
		List<QbBaseInfo> result = new ArrayList<QbBaseInfo>();
		try {
			List<QbBase> list = qbBaseDao.search(param.getEmployerId(),
					param.getQbName(), param.getPage());
			if (list.isEmpty())
				return result;

			List<Integer> qbIds = new ArrayList<Integer>();
			for (QbBase qbBase : list) {
				qbIds.add(qbBase.getQbId());
			}

			Map<Integer, QuestionTypeCountInfo> counts = qbQuestionDao
					.getQuestionTypeCountInfo(qbIds);

			for (QbBase qbBase : list) {
				QbBaseInfo qbBaseInfo = new QbBaseInfo();
				qbBaseInfo.setQbId(qbBase.getQbId());
				qbBaseInfo.setQbName(qbBase.getQbName());
				qbBaseInfo.setCreateDate(qbBase.getCreateDate());
				qbBaseInfo.setModifyDate(qbBase.getModifyDate());
				qbBaseInfo.setCreateDateDesc(PrettyTimeMaker.format(qbBase
						.getCreateDate()));
				qbBaseInfo.setModifyDateDesc(PrettyTimeMaker.format(qbBase
						.getModifyDate()));
				qbBaseInfo.setCategory(qbBase.getCategory());

				QuestionTypeCountInfo qbToCount = counts.get(qbBase.getQbId());
				long totalNum = 0;
				if (qbBase.getCategory() == 1) {
					long choiceNum = 0;
					long subjectNum = 0;
					long essayNum = 0;
					if (qbToCount != null) {
						choiceNum = Long
								.valueOf(qbToCount
										.getCountOfType(Constants.QB_QUESTIONS_SKILL_OBJECT));
						subjectNum = Long
								.valueOf(qbToCount
										.getCountOfType(Constants.QB_QUESTIONS_SKILL_SUBJECT));
						essayNum = Long
								.valueOf(qbToCount
										.getCountOfType(Constants.QB_QUESTIONS_SKILL_ESSAY));
					}
					qbBaseInfo.setChoiceNum(choiceNum);
					qbBaseInfo.setSubjectNum(subjectNum);
					qbBaseInfo.setEssayNum(essayNum);
					totalNum = (choiceNum + subjectNum + essayNum);
				}

				if (qbToCount != null) {
					totalNum += Long
							.valueOf(qbToCount
									.getCountOfType(Constants.QB_QUESTIONS_SKILL_VIDEO));
					totalNum += Long.valueOf(qbToCount
							.getCountOfType(Constants.QB_QUESTIONS_SKILL_INTE));
				}

				qbBaseInfo.setTotalNum(totalNum);
				result.add(qbBaseInfo);
			}
		} catch (Exception e) {
			logger.error("call getQbBases error ", e);
			throw new PFServiceException(e.getMessage());
		}

		return result;
	}

	@Override
	public List<QbGroupInfo> getQbGroups(int employerId, GetQbGroupsParam param)
			throws PFServiceException {
		try {
			logger.debug("getQbGroups  param is {} , employer is {} ", param,employerId);
			return qbQuestionDao.searchGroups(employerId, param);
		} catch (Exception e) {
			logger.error("call getQbGroups error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public LoadResponse editQuestion(int emloyerId, int qbId, long questionId,
			double similarityLimit, boolean checkTime, LoadRequest request)
			throws PFServiceException {
		try {
			Set<Long> skipQids = new HashSet<Long>();
			skipQids.add(questionId);

			LoadResponse response = loadService.loadQuestions(emloyerId, qbId,
					skipQids, similarityLimit, checkTime, request);
			int errors = response.getFormatErrors() + response.getTimeErrors()
					+ response.getSimilarityErrors();
			if (response.getErrorCode() == BaseResponse.SUCCESS && errors == 0)
				qbQuestionDao.updateState(questionId, Constants.QUES_DISCARD);

			return response;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	@Override
	public List<QbQuestionInfo> getQbQuestions(int employerId,
			GetQbQuestionsParam param) throws PFServiceException {
		try {
			logger.debug("search qbQuestions condition is {} ", param.toString());
			return qbQuestionDao.searchQuestions(employerId, param);
		} catch (Exception e) {
			logger.error("call getQbQuestions error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse deleteQuestion(int employerId, int qbId, long questionId)
			throws PFServiceException {
		try {
			PFResponse pfResponse = new PFResponse();
			QbQuestion qbQuestion = qbQuestionDao.getEntity(questionId);
			if(qbQuestion.getCreateBy() != employerId){
				logger.error("no power for questionId {} of employerId {} ", questionId,employerId);
				pfResponse.setCode(FuncBaseResponse.FAILED);
				pfResponse.setMessage("权限错误");
				return pfResponse;
			}
			qbQuestionDao.updateState(questionId, Constants.QUES_DISCARD);

			// 查找题库
			QbBase qbBase = qbBaseDao.getEntity(qbId);
			// 更新题库修改时间
			qbBase.setModifyDate(new Timestamp(System.currentTimeMillis()));
			qbBaseDao.update(qbBase);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	@Override
	public PFResponse updateQbSkill(QbSkillInfo qbSkillInfo)
			throws PFServiceException {
		try {
			PFResponse pfResponse = new PFResponse();
			QbSkill qbSkill = qbSkillDao.getEntity(qbSkillInfo.getSkillId());
			qbSkill.setSkillName(qbSkillInfo.getSkillName());
			qbSkillDao.saveOrUpdate(qbSkill);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("call updateQbSkill error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public QbSkillResponse createQbSkill(QbSkillInfo qbSkillInfo)
			throws PFServiceException {
		try {
			QbSkillResponse qbSkillResponse = new QbSkillResponse();
			QbSkill qbSkill = new QbSkill();
			String skId = qbSkillDao.getUIDFromBase(Constants.SK_ID).toString();
			qbSkill.setSkillId(skId);
			// qbSkill.setCreateBy(qbSkillInfo.getCreateBy());
			qbSkill.setSkillName(qbSkillInfo.getSkillName());
			qbSkill.setLevel(1);
			qbSkill.setParentId("0");
			// qbSkill.setCreateDate(new Timestamp(System.currentTimeMillis()));
			qbSkillDao.saveOrUpdate(qbSkill);
			qbSkillResponse.setSkillId(skId);
			updateModifyDate(qbSkillInfo.getQbId());
			qbSkillResponse.setCode(FuncBaseResponse.SUCCESS);
			return qbSkillResponse;
		} catch (Exception e) {
			logger.error("call createQbSkill error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse deleteQbSkill(String skillId) throws PFServiceException {
		try {
			PFResponse pfResponse = new PFResponse();
			qbSkillDao.delete(skillId);
			qbQuestionDao.discardQuestion(skillId);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("call updateQbSkill error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public List<QbSkill> getQbBaseSkills(int qbId) throws PFServiceException {
		try {
			return qbSkillDao.getList(qbId, "qbId");
		} catch (Exception e) {
			logger.error("call getQbBaseSkills error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public QbSkillStatistics getSkillLevelNums(int qbId)
			throws PFServiceException {
		List<QbQuestion> questions = qbQuestionDao.getChoiceQuestion(qbId);
		List<Long> questionIds = new ArrayList<Long>();
		Map<Long, QbQuestion> questionsMap = new HashMap<Long, QbQuestion>();
		if (questions.size() == 0) {
			logger.debug("not found any question for qbId {} ", qbId);
			return null;
		}
		for (QbQuestion qbQuestion : questions) {
			questionsMap.put(qbQuestion.getQuestionId(), qbQuestion);
			questionIds.add(qbQuestion.getQuestionId());
		}
		List<QbQuestionSkill> questionSkills = qbQuestionSkillDao
				.getQbQuestionSkills(questionIds);
		List<QbSkill> skills = qbSkillDao.getQbSkills(questionIds);
		Map<String, QbSkill> skillsMap = new HashMap<String, QbSkill>();
		QbSkillStatistics qbSkillStatistics = new QbSkillStatistics();
		for (QbSkill qbSkill : skills) {
			qbSkillStatistics.addSkillName(qbSkill.getSkillName());
			skillsMap.put(qbSkill.getSkillId(), qbSkill);
		}
		for (QbQuestionSkill qbQuestionSkill : questionSkills) {
			QbSkill qbSkill = skillsMap.get(qbQuestionSkill.getId()
					.getSkillId());
			QbQuestion qbQuestion = questionsMap.get(qbQuestionSkill.getId()
					.getQuestionId());
			qbSkillStatistics.addStatistics(qbSkill.getSkillName(),
					qbQuestion.getDegree());
		}
		return qbSkillStatistics;
	}

	@Override
	public QbProLangInfo getProgramLevelNums(int qbId)
			throws PFServiceException {
		List<QbQuestion> questions = qbQuestionDao.getProgramQuestion(qbId);
		List<ConfigCodeName> config = configDao
				.getConfigCode(ConfigCodeType.PROGRAM_LANGUAGE);
		Map<String, ConfigCodeName> configMap = new HashMap<String, ConfigCodeName>();
		for (ConfigCodeName ccn : config)
			configMap.put(ccn.getId().getCodeId(), ccn);
		QbProLangInfo qbProLangInfo = new QbProLangInfo();
		for (QbQuestion qbQuestion : questions) {
			if (StringUtils.isEmpty(qbQuestion.getProgramLanguage()))
				continue;
			ConfigCodeName ccn = configMap.get(qbQuestion.getProgramLanguage());
			if (ccn != null) {
				qbProLangInfo.addProgramName(ccn.getCodeName());
				qbProLangInfo.addStatistics(ccn.getCodeName(),
						qbQuestion.getDegree());
			} else {
				logger.error("not found program for language {} ",
						qbQuestion.getProgramLanguage());
			}

		}
		return qbProLangInfo;
	}

	@Override
	public PFResponse updateModifyDate(int qbId) throws PFServiceException {
		try {
			PFResponse pfResponse = new PFResponse();
			QbBase qbBase = qbBaseDao.getEntity(qbId);
			qbBase.setModifyDate(new Timestamp(System.currentTimeMillis()));
			qbBaseDao.saveOrUpdate(qbBase);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("call updateModifyDate error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFCountInfo getQbBasesCount(GetQbBasesParam param) {
		PFCountInfo count = new PFCountInfo();
		count.setCount(qbBaseDao.getQbBasesCount(param.getEmployerId(),
				param.getQbName()));
		count.setCode(FuncBaseResponse.SUCCESS);
		return count;
	}

	@Override
	public PFCountInfo getQbQuestionsCount(int employerId,
			GetQbQuestionsParam param) throws PFServiceException {
		PFCountInfo count = new PFCountInfo();
		count.setCount(qbQuestionDao.getQbQuestionsCount(employerId, param));
		count.setCode(FuncBaseResponse.SUCCESS);
		return count;
	}
	@Override
	public List<PaperQuestionTypeInfo> getPaperQuestionTypeInfoByQbId(int qbId) throws PFServiceException {
		logger.debug("getPaperQuestionTypeInfoByQbId for qbId {} ", qbId);
		Map<String, List<Long>> questionToTypes = paperDao.getPaperQuestionTypeInfoByQbId(qbId);
		List<PaperQuestionTypeInfo> typeInfos = new ArrayList<PaperQuestionTypeInfo>();
		List<ConfigCodeName> configs = configDao
				.getConfigCode(Constants.CONFIG_QUESTION_TYPE_NAME);
		for (ConfigCodeName conf : configs) {
			List<Long> ques = questionToTypes.get(conf.getId().getCodeId());
			if (ques != null && ques.size() > 0) {
				PaperQuestionTypeInfo typeInfo = new PaperQuestionTypeInfo();
				typeInfo.setTypeName(conf.getCodeName());
				typeInfo.setTypeId(conf.getId().getCodeId());
				typeInfo.setQuestionNumber(ques.size());
				// typeInfo.setAvgPoint(statQuestionImpl.getAvgPointByQuestions(ques));
				typeInfos.add(typeInfo);
			}
		}
		return typeInfos;
	}
}
