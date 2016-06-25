package com.ailk.sets.grade.word;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.excel.ExcelConf;
import com.ailk.sets.grade.excel.FormatException;
import com.ailk.sets.grade.excel.GeneratorFactory;
import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.QbQuestionSkillId;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.google.gson.Gson;

@Service
@Transactional(rollbackFor = Exception.class)
public class WordGenerator implements IWordGenerator {

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

	private static final Gson gson = new Gson();

	@Override
	public int generate(PaperWord paperWord, int createdBy)
			throws Exception {
		long currentTimeMillis = System.currentTimeMillis();

		// 保存题库
		int qbId = saveQbBase(paperWord, createdBy);

		for (PaperWord.Part part : paperWord.getParts()) {
			List<Long> questionIds = new ArrayList<Long>();

			for (PaperWord.Skill skill : part.getSkills()) {
				for (PaperWord.Question question : skill.getQuestions()) {
					// 保存题目
					long questionId = saveQbQuestion(question, qbId,
							part.getCategory(), createdBy, currentTimeMillis);
					questionIds.add(questionId);

					// 保存题目技能
					saveQbQuestionSkill(question, qbId, questionId);

					// 保存题目详情
					ExcelConf conf = new ExcelConf();
					conf.setId(questionId);
					conf.setMode(GradeConst.toModeStr(question
							.getProgramLanguage()));
					conf.setHtml(true);
					conf.setType(GradeConst.toQuestionTypeString(question
							.getQuestionType()));
					conf.setTitle(question.getTitle());
					conf.setSummary(null);
					conf.setCode(question.getTextAnswer());
					conf.setLevel(question.getDifficulty());
					conf.setParam(null);
					conf.setData(null);

					List<String> tags = new ArrayList<String>();
					tags.add(question.getSkillName());
					conf.setTags(tags);

					if (question.getOptAnswer() == null) {
						conf.setAnswer(0);
						conf.setOptions(null);
					} else {
						conf.setAnswer(question.getOptAnswer().length());
						List<String> matchList = new ArrayList<String>();
						List<String> unmatchList = new ArrayList<String>();
						List<String> options = new ArrayList<String>();
						for (int i = 0; i < question.getOptions().size(); i++) {
							char ch = (char) ('A' + i);
							if (question.getOptAnswer().indexOf(ch) != -1)
								matchList.add(question.getOptions().get(i));
							else
								unmatchList.add(question.getOptions().get(i));
						}
						
						for (String option : matchList)
							options.add(option);
						
						for (String option : unmatchList)
							options.add(option);
						
						conf.setOptions(options);
					}

					conf.setInclude(null);
					conf.setExclude(null);
					conf.setSample(question.isSample());
					conf.setStatus(1);
					conf.setAnswerNum(0);
					conf.setFailPercent(0);
					conf.setSuggestTime(question.getTimeLimit());
					conf.setScore(question.getPoint());
					conf.setCategory(part.getCategory());
					conf.setSamples(0);
					conf.setCorrectOptions(question.getOptAnswer());
					conf.setExplainRequired(question.getTextAnswer() != null);
					conf.setGroup(null);

					if (part.getPartId() != PaperWord.PART_ID_INTERVIEW)
						saveQbQuestionDetail(conf);
				}
			}

			if (part.getPartId() == PaperWord.PART_ID_INTERVIEW)
				saveGroup(qbId, part.getTimeLimit(), questionIds, createdBy);
		}
		
		return qbId;
	}

	/**
	 * 保存题库实体
	 * 
	 * @param paperWord
	 *            试卷结构
	 * @param createdBy
	 *            创建者
	 * @return 题库id
	 */
	private int saveQbBase(PaperWord paperWord, int createdBy) {
		// 创建题库
		QbBase qbBase = new QbBase();
		qbBase.setQbId(qbBaseDao.getUIDFromBase(Constants.QB_ID).intValue());
		qbBase.setQbName(paperWord.getPaperName());
		qbBase.setQbDesc(null);
		qbBase.setCategory(GradeConst.CATEGORY_PAPER);
		qbBase.setCreateBy(createdBy);
		qbBase.setCreateDate(new Timestamp(System.currentTimeMillis()));
		qbBase.setModifyDate(qbBase.getCreateDate());
		qbBase.setPrebuilt(0);
		qbBaseDao.save(qbBase);

		return qbBase.getQbId();
	}

	/**
	 * 保存题目实体
	 * 
	 * @param question
	 *            题目结构
	 * @param qbId
	 *            题库id
	 * @param category
	 *            分类
	 * @param createdBy
	 *            创建者
	 * @param currentTimeMillis
	 *            当前时间
	 * @return 题目id
	 */
	private long saveQbQuestion(PaperWord.Question question, int qbId,
			int category, int createdBy, long currentTimeMillis) {
		QbQuestion qbQuestion = new QbQuestion();
		qbQuestion.setQuestionId(qbQuestionDao.getNextQid());
		qbQuestion.setDegree(question.getDifficulty());
		qbQuestion.setAnswerNum(0);
		qbQuestion.setCorrectNum(0);
		qbQuestion.setCreateDate(new Timestamp(currentTimeMillis));
		qbQuestion.setModifyDate(qbQuestion.getCreateDate());
		qbQuestion.setQbId(qbId);
		qbQuestion.setQuestionType(GradeConst.toQuestionTypeString(question
				.getQuestionType()));
		qbQuestion.setCategory(category);
		qbQuestion.setQuestionDesc(question.getTitle());
		qbQuestion.setProgramLanguage(GradeConst.toModeStr(question
				.getProgramLanguage()));
		qbQuestion.setIsSample(question.isSample() ? 1 : 0);

		qbQuestion.setPoint(question.getPoint());
		qbQuestion.setSuggestTime(question.getTimeLimit());
		qbQuestion.setSubAsks(null);
		qbQuestion.setDeriveFlag(0);
		qbQuestion.setState(1);
		qbQuestion.setCreateBy(createdBy);
		qbQuestion.setHtml(1);
		qbQuestion.setPrebuilt(0);
		qbQuestionDao.save(qbQuestion);

		return qbQuestion.getQuestionId();
	}

	/**
	 * 保存试题详情
	 * 
	 * @param conf
	 * @throws Exception
	 */
	private void saveQbQuestionDetail(ExcelConf conf) throws Exception {
		QuestionContent questionContent;

		try {
			IGenerator generator = GeneratorFactory.createFactory(conf);
			if (generator == null) {
				throw new FormatException("不认识的行记录，id=" + conf.getId());
			} else {
				// 生成试题内容
				questionContent = generator.generate();
			}
		} catch (Exception e) {
			throw new FormatException("不认识的行记录，id=" + conf.getId());
		}

		QbQuestionDetail qbQuestionDetail = new QbQuestionDetail();
		qbQuestionDetail.setQuestionId(conf.getId());

		String content = gson.toJson(questionContent);
		qbQuestionDetail.setContent(content);

		qbQuestionDetailDao.save(qbQuestionDetail);
	}

	/**
	 * 保存试题技能
	 * 
	 * @param question
	 *            题目结构
	 * @param qbId
	 *            题库id
	 * @param questionId
	 *            题目id
	 */
	private void saveQbQuestionSkill(PaperWord.Question question, int qbId,
			long questionId) {
		// 检查是否有技能
		if (question.getSkillName() == null
				|| question.getSkillName().isEmpty())
			return;

		// 保存试题相关技能
		String parentId = "0";
		QbQuestionSkill qbQuestionSkill = new QbQuestionSkill();
		QbQuestionSkillId qbQuestionSkillId = new QbQuestionSkillId();
		qbQuestionSkill.setSkillSeq(1);

		qbQuestionSkillId.setQuestionId(questionId);
		qbQuestionSkill.setId(qbQuestionSkillId);

		String skillName = question.getSkillName().toLowerCase();
		QbSkill qbSkill = qbSkillDao.getByName(skillName, parentId, qbId);
		if (qbSkill == null) {
			// 插入技能
			qbSkill = new QbSkill();
			qbSkill.setSkillId(qbSkillDao.getNextSkillId());
			qbSkill.setSkillName(skillName);
			qbSkill.setSkillAlias(null);
			qbSkill.setParentId("0");
			qbSkill.setLevel(1);
			qbSkill.setQbId(qbId);
			qbSkillDao.save(qbSkill);
		}

		qbQuestionSkillId.setSkillId(qbSkill.getSkillId());
		qbQuestionSkillDao.save(qbQuestionSkill);

		parentId = qbSkill.getSkillId();
	}

	/**
	 * 保存组数据
	 * 
	 * @param qbId
	 *            题库id
	 * @param timeLimit
	 *            时限
	 * @param questionIds
	 *            题目id列表
	 * @param createdBy
	 *            创建者
	 */
	private void saveGroup(int qbId, int timeLimit, List<Long> questionIds,
			int createdBy) {
		// 添加题组
		QbQuestion qbQuestion = new QbQuestion();
		qbQuestion.setQuestionId(qbQuestionDao.getNextQid());
		qbQuestion.setDegree(0);
		qbQuestion.setAnswerNum(0);
		qbQuestion.setCorrectNum(0);
		qbQuestion.setQbId(qbId);
		qbQuestion.setQuestionType(GradeConst.QUESTION_TYPE_NAME_GROUP);
		qbQuestion.setCategory(GradeConst.CATEGORY_INTERVIEW);
		qbQuestion.setQuestionDesc("面试题组");
		qbQuestion.setProgramLanguage(null);
		qbQuestion.setIsSample(0);
		qbQuestion.setPoint(0);
		qbQuestion.setSuggestTime(timeLimit);
		qbQuestion.setSubAsks(StringUtils.join(questionIds, ','));
		qbQuestion.setDeriveFlag(0);
		qbQuestion.setState(1);
		qbQuestion.setCreateBy(createdBy);
		qbQuestion.setCreateDate(new Timestamp(System.currentTimeMillis()));
		qbQuestion.setModifyDate(qbQuestion.getCreateDate());
		qbQuestion.setHtml(0);
		qbQuestion.setPrebuilt(0);
		qbQuestionDao.save(qbQuestion);
	}

}
