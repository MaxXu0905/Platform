package com.ailk.sets.platform.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.excel.intf.IExportExcel;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.grade.utils.QuestionUtils.FileInfo;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.dao.interfaces.IStatQuestionDao;
import com.ailk.sets.platform.domain.QuestionTypeCountInfo;
import com.ailk.sets.platform.domain.StatQuestion;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.param.GetQbGroupsParam;
import com.ailk.sets.platform.intf.model.param.GetQbQuestionsParam;
import com.ailk.sets.platform.intf.model.param.SearchCondition;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.model.question.QbGroupInfo;
import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;
import com.ailk.sets.platform.util.PaperCreateUtils;
import com.google.gson.Gson;

/**
 * 问题试卷dao
 * 
 * @author panyl
 * 
 */
@Repository
public class QbQuestionDaoImpl extends BaseDaoImpl<QbQuestion> implements
		IQbQuestionDao {

	private static final Logger logger = LoggerFactory
			.getLogger(QbQuestionDaoImpl.class);
	private static final Gson gson = new Gson();

	@Autowired
	private IConfigSysParamDao configSysParamDao;

	@Autowired
	private IStatQuestionDao statQuestionDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;
	@Autowired
	private IQbBaseDao qbBaseDao;

	@Autowired
	private IExportExcel exportExcel;

	@Autowired
	private IPaperQuestionDao paperQuestionDao;

	@Override
	public int getSumPointsForQuestion(List<Long> ids) {
		int result = 0;

		for (long id : ids) {
			QbQuestion qbQuestion = getEntity(id);
			if (qbQuestion == null)
				continue;

			if (qbQuestion.getPoint() == null)
				continue;

			result += qbQuestion.getPoint();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbQuestionProgram", key = "#programLanguage + '-' + #degree")
	public List<PaperQuestionToSkills> getProgramQuestionsByLanguangeAndDegree(
			String programLanguage, int degree) {
		logger.debug("get list subject questions for language {} , degree {} ",
				programLanguage, degree);
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM QbQuestion WHERE degree = :degree and programLanguage = :programLanguage "
				+ "and questionType = :questionType and isSample = 0 and state = 1 and category = 1 and prebuilt = 1";
		Query query = session.createQuery(hql);
		query.setInteger("degree", degree);
		query.setString("programLanguage", programLanguage);
		query.setString("questionType", Constants.QUESTION_TYPE_NAME_PROGRAM);

		List<QbQuestion> qbQuestions = query.list();
		List<PaperQuestionToSkills> list = new ArrayList<PaperQuestionToSkills>();

		for (QbQuestion qbQuestion : qbQuestions) {
			PaperQuestionToSkills paperQues = new PaperQuestionToSkills();
			paperQues.setQuestionId(qbQuestion.getQuestionId());
			paperQues.setDegree(qbQuestion.getDegree());
			list.add(paperQues);
		}

		return list;
	}

	@Override
	@Cacheable(value = "qbQuestionSample", key = "#programLanguage")
	public QbQuestion getTestSubjectQuestion(String programLanguage) {
		Session session = sessionFactory.getCurrentSession();

		return (QbQuestion) session
				.createQuery(
						"FROM QbQuestion WHERE questionType = ?1 AND programLanguage = ?2 AND isSample = 1 AND prebuilt = 1")
				.setMaxResults(1)
				.setString("1", Constants.QUESTION_TYPE_NAME_PROGRAM)
				.setString("2", programLanguage).uniqueResult();
	}

	@Override
	@Cacheable(value = "qbQuestionSample", key = "'video'")
	public QbQuestion getTestInterviewQuestion() {
		Session session = sessionFactory.getCurrentSession();

		return (QbQuestion) session
				.createQuery(
						"FROM QbQuestion WHERE questionType = ?1 AND isSample = 1")
				.setMaxResults(1)
				.setString("1", Constants.QUESTION_TYPE_NAME_INTERVIEW)
				.uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<PaperQuestionToSkills> getMotiObjectQuestions(
			List<String> skillIds, List<Integer> degrees) {
		logger.debug("get list moti questions for skillIds {} , degrees {} ",
				skillIds, degrees);
		Session session = sessionFactory.getCurrentSession();
		int minNumToMoti = 10;
		try {
			minNumToMoti = Integer.valueOf(configSysParamDao
					.getConfigParamValue("MIN_NUM_TO_MOTI"));
		} catch (Exception e) {
			logger.error("find error number ", e);
		}
		List<PaperQuestionToSkills> list = new ArrayList<PaperQuestionToSkills>();
		String sql = "select a.question_id from qb_question_skill a, "
				+ " ( select * from qb_question where question_type in (:questionType)  "
				+ " and degree in (:degrees) and prebuilt = 1 and is_sample=0 and state=1  "
				+ " and category = 1   and answer_num < :minNumToMoti ) b "
				+ " where a.question_id = b.question_id and a.skill_id in (:skillIds)";
		Query query = session.createSQLQuery(sql);
		query.setParameterList("degrees", degrees);
		query.setParameterList("questionType", PaperCreateUtils
				.getQuestionTypesInArray(PaperPartSeqEnum.OBJECT));
		query.setParameterList("skillIds", skillIds);
		query.setInteger("minNumToMoti", minNumToMoti);
		list = query.setResultTransformer(new ResultTransformer() {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				PaperQuestionToSkills p = new PaperQuestionToSkills();
				p.setQuestionId(((BigInteger) tuple[0]).longValue());
				p.setMoti(1);
				return p;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}).list();
		return list;
	}

	@Override
	@Cacheable(value = "qbQuestion")
	public QbQuestion getEntity(Serializable questionId) {
		return super.getEntity(questionId);
	}

	@Override
	public QbQuestion getEntityWithoutCache(Serializable questionId) {
		return super.getEntity(questionId);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestion", key = "#qbQuestion.questionId"),
			@CacheEvict(value = "qbQuestionProgram", key = "#qbQuestion.programLanguage + '-' + #qbQuestion.degree") })
	public void save(QbQuestion qbQuestion) {
		super.save(qbQuestion);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestion", key = "#qbQuestion.questionId"),
			@CacheEvict(value = "qbQuestionProgram", key = "#qbQuestion.programLanguage + '-' + #qbQuestion.degree") })
	public void update(QbQuestion qbQuestion) {
		super.update(qbQuestion);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestion", key = "#qbQuestion.questionId"),
			@CacheEvict(value = "qbQuestionProgram", key = "#qbQuestion.programLanguage + '-' + #qbQuestion.degree") })
	public void saveOrUpdate(QbQuestion qbQuestion) {
		super.saveOrUpdate(qbQuestion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbQuestion> getList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM QbQuestion").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbQuestion> getListByLanguage(String questionType,
			String programLanguage) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM QbQuestion WHERE state = 1 AND questionType = ?1 AND programLanguage = ?2")
				.setString("1", questionType).setString("2", programLanguage)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbQuestion> getListByCreator(int createBy, String condition) {
		Session session = sessionFactory.getCurrentSession();

		if (condition == null) {
			return session
					.createQuery(
							"FROM QbQuestion WHERE state = 1 AND createBy = ?1")
					.setInteger("1", createBy).list();
		} else {
			return session
					.createQuery(
							"FROM QbQuestion WHERE state = 1 AND createBy = ?1 AND "
									+ condition).setInteger("1", createBy)
					.list();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbQuestion> getListByParams(int createBy, Integer qbId,
			int category) {
		Session session = sessionFactory.getCurrentSession();

		if (qbId != null) {
			return session
					.createQuery(
							"FROM QbQuestion WHERE state = 1 AND createBy = ?1 AND qbId = ?2 AND category = ?3")
					.setInteger("1", createBy).setInteger("2", qbId)
					.setInteger("3", category).list();
		} else {
			return session
					.createQuery(
							"FROM QbQuestion WHERE state = 1 AND createBy = ?1 AND category = ?2")
					.setInteger("1", createBy).setInteger("2", category).list();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbQuestion> getListByQbId(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery("FROM QbQuestion WHERE state = 1 AND qbId = ?1")
				.setInteger("1", qbId).list();
	}

	@Override
	public long getNextQid() {
		Session session = sessionFactory.getCurrentSession();

		List<?> list = session.createSQLQuery("SELECT uf_getQid() FROM dual")
				.list();
		return ((BigInteger) list.get(0)).longValue();
	}

	@Override
	public QbQuestion getRandomQuestion(String category) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sql = new StringBuilder(
				" select * from qb_question where state = 1 and prebuilt = "
						+ Constants.PREBUILT_SYS);
		if ("4".equals(category))
			sql.append(" and question_type='"
					+ Constants.QUESTION_TYPE_NAME_GROUP + "' ");
		else if ("3".equals(category))
			sql.append(" and category = 3 ");
		else
			return null;

		sql.append(" order by rand() limit 1 ");
		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addEntity(QbQuestion.class);
		return (QbQuestion) query.uniqueResult();
	}

	public List<QbQuestion> getQbQuestionsByGroup(long qid) {
		String qids = getEntity(qid).getSubAsks();
		if (StringUtils.isEmpty(qids))
			return null;

		List<QbQuestion> result = new ArrayList<QbQuestion>();
		for (String qidStr : qids.split(",", -1)) {
			QbQuestion qbQuestion = getEntity(Long.parseLong(qidStr));
			result.add(qbQuestion);
		}

		return result;
	}

	@Override
	public void discardQuestion(String skillId) {
		List<Long> qids = qbQuestionSkillDao.getQuestionIds(skillId);
		for (long qid : qids) {
			QbQuestion qbQuestion = getEntity(qid);

			qbQuestion.setState(Constants.QUES_DISCARD);
			update(qbQuestion);
		}
	}

	@Override
	public Map<String, Long> getQuestionNum(int qbId) {
		Session session = getSession();
		String sql = "select category,count(question_id) from qb_question where state = 1 and qb_id = "
				+ qbId
				+ " and category>1 and question_type != '"
				+ Constants.QUESTION_TYPE_NAME_INTERVIEW
				+ "' group by category";
		Query query = session.createSQLQuery(sql);
		final Map<String, Long> result = new HashMap<String, Long>();
		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 7688876341121138923L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				result.put((String) tuple[0], (Long) tuple[1]);
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		return result;
	}

	@Override
	public Long getChoiceNum(int qbId) {
		Session session = getSession();
		String hql = "select count(questionId) from QbQuestion where category = 1 and state = 1 and questionType in  ('"
				+ Constants.QUESTION_TYPE_NAME_S_CHOICE
				+ "','"
				+ Constants.QUESTION_TYPE_NAME_M_CHOICE
				+ "','"
				+ Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS
				+ "','"
				+ Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS
				+ "')  and qbId="
				+ qbId;
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	@Override
	public Long getProgramNum(int qbId) {
		Session session = getSession();
		String hql = "select count(questionId) from QbQuestion where category = 1 and state = 1 and questionType in ('"
				+ Constants.QUESTION_TYPE_NAME_PROGRAM
				+ "','"
				+ Constants.QUESTION_TYPE_NAME_TEXT + "') and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	@Override
	public Long getTotalNum(int qbId) {
		Session session = getSession();
		String hql = "select count(questionId) from QbQuestion where question_type not in( '"
				+ Constants.QUESTION_TYPE_NAME_INTERVIEW
				+ "','"
				+ Constants.QUESTION_TYPE_NAME_GROUP
				+ "') and state = 1 and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<QbQuestion> getChoiceQuestion(int qbId) {
		Session session = getSession();
		String hql = "from QbQuestion where questionType in  ('"
				+ Constants.QUESTION_TYPE_NAME_S_CHOICE + "','"
				+ Constants.QUESTION_TYPE_NAME_M_CHOICE + "','"
				+ Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS + "','"
				+ Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS
				+ "') and  state = 1 and  category = 1 and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<QbQuestion> getProgramQuestion(int qbId) {
		Session session = getSession();
		String hql = "from QbQuestion where questionType in ('"
				+ Constants.QUESTION_TYPE_NAME_PROGRAM + "','"
				+ Constants.QUESTION_TYPE_NAME_TEXT
				+ "') and  state = 1 and category = 1 and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<QbQuestion> getGroupQuestion(int qbId, Page page) {
		Session session = getSession();
		String hql = "from QbQuestion where questionType ='"
				+ Constants.QUESTION_TYPE_NAME_GROUP
				+ "' and  state = 1 and category = 4 and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return query.list();
	}

	public Long getEssayNum(int qbId) {
		Session session = getSession();
		String hql = "select count(questionId) from QbQuestion where questionType ='"
				+ Constants.QUESTION_TYPE_NAME_ESSAY
				+ "' and  state = 1 and category = 1 and qbId=" + qbId;
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	@Override
	public List<QbGroupInfo> searchGroups(int employerId, GetQbGroupsParam param) {
		List<QbGroupInfo> result = new ArrayList<QbGroupInfo>();
		final SearchCondition searchCondition = param.getSearchCondition();

		List<QbQuestion> qbQuestions = getListByParams(employerId,
				param.getQbId(), GradeConst.CATEGORY_INTERVIEW);
		for (QbQuestion qbQuestion : qbQuestions) {
			if (!qbQuestion.getQuestionType().equals(
					GradeConst.QUESTION_TYPE_NAME_GROUP))
				continue;

			int qbId = qbQuestion.getQbId();
			QbBase base = qbBaseDao.getEntity(qbId);
			if (base.getCategory() == GradeConst.CATEGORY_PAPER) {// 试卷类需要过滤掉
				continue;
			}

			if (searchCondition != null
					&& StringUtils
							.isNotEmpty(searchCondition.getQuestionDesc())
					&& !qbQuestion
							.getQuestionDesc()
							.toUpperCase()
							.contains(
									searchCondition.getQuestionDesc()
											.toUpperCase()))
				continue;

			QbGroupInfo qbGroupInfo = new QbGroupInfo();
			qbGroupInfo.setQuestionId(qbQuestion.getQuestionId());
			qbGroupInfo.setQuestionType(qbQuestion.getQuestionType());
			qbGroupInfo.setQuestionDesc(qbQuestion.getQuestionDesc());
			qbGroupInfo.setSuggestTime(qbQuestion.getSuggestTime());
			qbGroupInfo.setModifyDate(qbQuestion.getModifyDate());

			qbGroupInfo.setGroupQuestion(exportExcel.getGroup(qbQuestion
					.getQuestionId()));
			/*
			 * StatQuestion statQuestion = statQuestionDao.getEntity(qbQuestion
			 * .getQuestionId()); if (statQuestion != null) { if
			 * (statQuestion.getTotalNum() > 0) {
			 * qbGroupInfo.setAvgScore((double) statQuestion .getTotalScore() /
			 * statQuestion.getTotalNum()); }
			 * 
			 * qbGroupInfo.setAnswerNumber(statQuestion.getAnswerNum());
			 * qbGroupInfo.setRightAnswerNumber(statQuestion.getCorrectNum());
			 * qbGroupInfo.setDiscussNumber(statQuestion.getPraiseNum());
			 * qbGroupInfo.setBadDiscussNumber(statQuestion.getNegNum()); }
			 */

			result.add(qbGroupInfo);
		}

		if (searchCondition != null) {
			Collections.sort(result, new Comparator<QbGroupInfo>() {

				@Override
				public int compare(QbGroupInfo o1, QbGroupInfo o2) {
					if (searchCondition.getModifyDateAsc() != null) {
						if (searchCondition.getModifyDateAsc().booleanValue())
							return o1.getModifyDate().compareTo(
									o2.getModifyDate());
						else
							return o2.getModifyDate().compareTo(
									o1.getModifyDate());
					}

					if (searchCondition.getAvgScoreAsc() != null) {
						if (searchCondition.getAvgScoreAsc().booleanValue()) {
							if (o1.getAvgScore() == null)
								return -1;
							else
								return o1.getAvgScore().compareTo(
										o2.getAvgScore());
						} else {
							if (o2.getAvgScore() == null)
								return -1;
							else
								return o2.getAvgScore().compareTo(
										o1.getAvgScore());
						}
					}

					if (searchCondition.getAnswerNumAsc() != null) {
						if (searchCondition.getAnswerNumAsc().booleanValue())
							return o1.getAnswerNumber() - o2.getAnswerNumber();
						else
							return o2.getAnswerNumber() - o1.getAnswerNumber();
					}

					if (searchCondition.getSuggestTimeAsc() != null) {
						if (searchCondition.getSuggestTimeAsc().booleanValue())
							return o1.getSuggestTime() - o2.getSuggestTime();
						else
							return o2.getSuggestTime() - o1.getSuggestTime();
					}

					if (searchCondition.getNegNumAsc() != null) {
						if (searchCondition.getNegNumAsc().booleanValue())
							return o1.getBadDiscussNumber()
									- o2.getBadDiscussNumber();
						else
							return o2.getBadDiscussNumber()
									- o1.getBadDiscussNumber();
					}

					return 0;
				}
			});
		}

		Page page = param.getPage();
		int fromIndex = page.getFirstRow();
		int toIndex = page.getLastRow();
		if (fromIndex > result.size())
			fromIndex = result.size();
		if (toIndex > result.size())
			toIndex = result.size();
		return result.subList(fromIndex, toIndex);
	}

	public List<QbQuestionInfo> searchQuestions(int employerId,
			GetQbQuestionsParam param) {
		List<QbQuestionInfo> result = searchAllQuestions(employerId, param,
				true);
		final SearchCondition searchCondition = param.getSearchCondition();
		if (searchCondition != null) {
			Collections.sort(result, new Comparator<QbQuestionInfo>() {

				@Override
				public int compare(QbQuestionInfo o1, QbQuestionInfo o2) {
					if (searchCondition.getModifyDateAsc() != null) {
						if (searchCondition.getModifyDateAsc().booleanValue())
							return o1.getModifyDate().compareTo(
									o2.getModifyDate());
						else
							return o2.getModifyDate().compareTo(
									o1.getModifyDate());
					}

					if (searchCondition.getAvgScoreAsc() != null) {
						if (searchCondition.getAvgScoreAsc().booleanValue()) {
							if (o1.getAvgScore() == null)
								return -1;
							else if (o2.getAvgScore() == null) {
								return 1;
							}
							return o1.getAvgScore().compareTo(o2.getAvgScore());
						} else {
							if (o2.getAvgScore() == null)
								return -1;
							else if (o1.getAvgScore() == null) {
								return 1;
							}
							return o2.getAvgScore().compareTo(o1.getAvgScore());
						}
					}

					if (searchCondition.getAnswerNumAsc() != null) {
						if (searchCondition.getAnswerNumAsc().booleanValue())
							return o1.getAnswerNumber() - o2.getAnswerNumber();
						else
							return o2.getAnswerNumber() - o1.getAnswerNumber();
					}

					if (searchCondition.getSuggestTimeAsc() != null) {
						if (searchCondition.getSuggestTimeAsc().booleanValue())
							return o1.getSuggestTime() - o2.getSuggestTime();
						else
							return o2.getSuggestTime() - o1.getSuggestTime();
					}

					if (searchCondition.getNegNumAsc() != null) {
						if (searchCondition.getNegNumAsc().booleanValue())
							return o1.getBadDiscussNumber()
									- o2.getBadDiscussNumber();
						else
							return o2.getBadDiscussNumber()
									- o1.getBadDiscussNumber();
					}

					return 0;
				}
			});
		} else {
			Collections.sort(result, new Comparator<QbQuestionInfo>() {
				@Override
				public int compare(QbQuestionInfo o1, QbQuestionInfo o2) {
					if (o2.getModifyDate() != null
							&& o1.getModifyDate() != null)
						return o2.getModifyDate().compareTo(o1.getModifyDate());
					return 0;
				}

			});

		}

		Page page = param.getPage();
		int fromIndex = page.getFirstRow();
		int toIndex = page.getLastRow();
		if (fromIndex > result.size())
			fromIndex = result.size();
		if (toIndex > result.size())
			toIndex = result.size();
		return result.subList(fromIndex, toIndex);
	}

	/**
	 * 根据搜索条件获取题目列表
	 * 
	 * @param employerId
	 * @param param
	 * @param needDetail
	 *            //是否需要题目详情，当获取题目总数时，不需要获取题目详情false;当获取题目列表时，需要获取题目详情true
	 * @return
	 */
	private List<QbQuestionInfo> searchAllQuestions(int employerId,
			GetQbQuestionsParam param, boolean needDetail) {
		List<QbQuestionInfo> result = new ArrayList<QbQuestionInfo>();
		final SearchCondition searchCondition = param.getSearchCondition();

		List<QbQuestion> qbQuestions = getListByParams(employerId,
				param.getQbId(), param.getCategory());
		for (QbQuestion qbQuestion : qbQuestions) {
			int qbId = qbQuestion.getQbId();
			QbBase base = qbBaseDao.getEntity(qbId);
			if (base.getCategory() == GradeConst.CATEGORY_PAPER) {// 试卷类需要过滤掉
				continue;
			}
			int questionType = GradeConst.toQuestionTypeInt(qbQuestion
					.getQuestionType());

			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
			case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
				if (searchCondition.isChoice())
					break;
				continue;
			case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
				if (searchCondition.isProgram())
					break;
				continue;
			case GradeConst.QUESTION_TYPE_ESSAY:
				if (searchCondition.isEssay())
					break;
				continue;
			default:
				continue;
			}

			if (searchCondition != null
					&& StringUtils
							.isNotEmpty(searchCondition.getQuestionDesc())
					&& !qbQuestion
							.getQuestionDesc()
							.toUpperCase()
							.contains(
									searchCondition.getQuestionDesc()
											.toUpperCase()))
				continue;

			if (searchCondition != null
					&& StringUtils.isNotEmpty(searchCondition.getProgramLang())) {
				if (!searchCondition.getProgramLang().equals(
						qbQuestion.getProgramLanguage())) {
					continue;
				}
			}

			QbQuestionInfo qbQuestionInfo = new QbQuestionInfo();
			qbQuestionInfo.setSkills(qbSkillDao.getQbSkills(qbQuestion
					.getQuestionId()));

			if (searchCondition.getSkillId() != null) {
				if (qbQuestionInfo.getSkills() == null)
					continue;

				boolean contains = false;
				for (QbSkill qbSkill : qbQuestionInfo.getSkills()) {
					if (qbSkill.getSkillId().equals(
							searchCondition.getSkillId())) {
						contains = true;
						break;
					}
				}

				if (!contains)
					continue;
			}

			qbQuestionInfo = getQbQuestionInfo(qbQuestionInfo, qbQuestion,
					needDetail);

			result.add(qbQuestionInfo);
		}
		return result;
	}

	@Override
	public QbQuestionInfo getQbQuestionInfo(QbQuestionInfo qbQuestionInfo,
			QbQuestion qbQuestion, boolean needDetail) {
		qbQuestionInfo.setQuestionId(qbQuestion.getQuestionId());
		qbQuestionInfo.setQuestionType(qbQuestion.getQuestionType());
		qbQuestionInfo.setQuestionDesc(QuestionUtils.escape(
				qbQuestion.getQuestionDesc(), qbQuestion.getHtml() == 1));
		qbQuestionInfo.setSuggestTime(qbQuestion.getSuggestTime());
		qbQuestionInfo.setModifyDate(qbQuestion.getModifyDate());
		qbQuestionInfo.setCategory(qbQuestion.getCategory());
		if (needDetail) {
			StatQuestion statQuestion = statQuestionDao.getEntity(qbQuestion
					.getQuestionId());
			if (statQuestion != null) {
				if (statQuestion.getAnswerNum() > 0) {
					double tmp = (double) statQuestion.getTotalScore()
							/ statQuestion.getAnswerNum();
					qbQuestionInfo.setAvgScore(MathUtils.round(tmp, 2));
					Integer score = qbQuestion.getPoint();
					if (score != null && score != 0) {// 10分制分数
						double avgScore = tmp / score * 10;
						qbQuestionInfo
								.setAvgScore(MathUtils.round(avgScore, 2));
					}
					qbQuestionInfo.setAvgTime(statQuestion.getAvgTime());
				}
				qbQuestionInfo.setAnswerNumber(statQuestion.getAnswerNum());
				qbQuestionInfo.setRightAnswerNumber(statQuestion
						.getCorrectNum());
				qbQuestionInfo.setDiscussNumber(statQuestion.getPraiseNum()
						+ statQuestion.getNegNum());
				qbQuestionInfo.setBadDiscussNumber(statQuestion.getNegNum());
			}
		}

		if (!Constants.QUESTION_TYPE_NAME_INTERVIEW.equals(qbQuestion
				.getQuestionType())
				&& !Constants.QUESTION_TYPE_NAME_GROUP.equals(qbQuestion
						.getQuestionType())) {
			QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao
					.get(qbQuestion.getQuestionId());
			QuestionContent questionContent = gson.fromJson(
					qbQuestionDetail.getContent(), QuestionContent.class);
			QuestionConf questionConf = questionContent.getQuestionConf();
			qbQuestionInfo.setAnswers(QuestionUtils.escape(
					questionConf.getOptions(), qbQuestion.getHtml() == 1));
			qbQuestionInfo.setOptAnswers(QuestionUtils.getAnswers(questionConf
					.getAnswer()));

			FileInfo fileInfo = QuestionUtils.getFileInfo(questionContent);
			if (fileInfo != null) {
				qbQuestionInfo.setMode(fileInfo.getMode());
				qbQuestionInfo.setRefAnswer(fileInfo.getRefAnswer());
			}
		}

		return qbQuestionInfo;
	}

	@Override
	public void updateState(long questionId, int state) {
		QbQuestion qbQuestion = getEntity(questionId);
		if (qbQuestion == null)
			return;

		qbQuestion.setState(state);
		update(qbQuestion);
	}

	@SuppressWarnings("serial")
	@Override
	public Map<Integer, QuestionTypeCountInfo> getQuestionTypeCountInfo(
			List<Integer> qbIds) {
		final Map<Integer, QuestionTypeCountInfo> map = new HashMap<Integer, QuestionTypeCountInfo>();
		Session session = getSession();
		String sql = "select qb_id, question_type,category, count(*) from qb_question"
				+ " where  state = 1 and  qb_id in (:qbIds)  group by qb_id, question_type , category";
		Query q = session.createSQLQuery(sql);
		q.setParameterList("qbIds", qbIds);
		q.setResultTransformer(new ResultTransformer() {

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Integer qbId = (Integer) tuple[0];
				String questionType = (String) tuple[1];
				Integer category = (Integer) tuple[2];
				Integer count = ((BigInteger) tuple[3]).intValue();
				QuestionTypeCountInfo countInfo = map.get(qbId);
				if (countInfo == null) {
					countInfo = new QuestionTypeCountInfo();
					map.put(qbId, countInfo);
				}
				if (questionType.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS))
					countInfo.addTypeToCount(
							Constants.QB_QUESTIONS_SKILL_OBJECT, count);
				else if (questionType
						.equals(Constants.QUESTION_TYPE_NAME_PROGRAM)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_TEXT))
					countInfo.addTypeToCount(
							Constants.QB_QUESTIONS_SKILL_SUBJECT, count);
				else if (questionType
						.equals(Constants.QUESTION_TYPE_NAME_ESSAY))
					countInfo.addTypeToCount(
							Constants.QB_QUESTIONS_SKILL_ESSAY, count);
				else
					logger.warn("not known questionType {} for qbId {} ",
							questionType, qbId);
				if (category == Constants.CATEGORY_INTE) {
					countInfo.addTypeToCount(Constants.QB_QUESTIONS_SKILL_INTE,
							count);
				} else if (category == Constants.CATEGORY_INTER)
					if (questionType.equals(Constants.QUESTION_TYPE_NAME_GROUP))
						countInfo.addTypeToCount(
								Constants.QB_QUESTIONS_SKILL_VIDEO, count);
					else
						logger.warn("not known category {} for qbId {} ",
								category, qbId);
				return countInfo;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		q.list();
		return map;
	}

	@Override
	public Integer getQbQuestionsCount(int employerId, GetQbQuestionsParam param)
			throws PFServiceException {
		List<QbQuestionInfo> result = searchAllQuestions(employerId, param,
				false);
		return result.size();

	}

	@Override
	public boolean hasQuestionOfSelfSkill(String skillId) {
		logger.debug("hasQuestionOfSelfSkill skillId={}", skillId);
		String sql = "select count(a.question_id) from qb_question_skill a, "
				+ " (select * from qb_question where  is_sample=0 and state=1 and prebuilt = 0 ) b"
				+ " where a.question_id = b.question_id and a.skill_id=:skillId  ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("skillId", skillId);
		BigInteger res = (BigInteger) query.uniqueResult();
		logger.debug("question size is {} for skillId {} ", res, skillId);
		if (res == null)
			return false;
		return res.intValue() > 0;
	}

	@Override
	public List<QbQuestion> getListByPaperId(int paperId) {
		List<QbQuestion> qbQuestions = new ArrayList<QbQuestion>();
		List<PaperQuestion> paperQuestions = paperQuestionDao
				.getNormalPaperQuestions(paperId);

		for (PaperQuestion paperQuestion : paperQuestions) {
			QbQuestion qbQuestion = getEntity(paperQuestion.getId()
					.getQuestionId());
			if (qbQuestion == null)
				continue;

			qbQuestions.add(qbQuestion);
		}

		return qbQuestions;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "qbQuestion", allEntries = true),
			@CacheEvict(value = "qbQuestionSample", allEntries = true),
			@CacheEvict(value = "qbQuestionProgram", allEntries = true) })
	public void evict() {
	}

}
