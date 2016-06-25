package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.domain.StatQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.util.PrettyTimeMaker;

@Repository
public class PaperDaoImpl extends BaseDaoImpl<Paper> implements IPaperDao {

	private Logger logger = LoggerFactory.getLogger(InviteDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private QbQuestionDaoImpl qbQuestionDaoImpl;

	@Autowired
	private IConfigDao configDao;
	
	@Autowired
	private PaperQuestionDaoImpl paperQuestionDaoImpl;
	
	@Autowired
	private StatQuestionDaoImpl statQuestionDaoImpl;
	@Autowired
	private IInvitationDao invitationDao;

	public long getReportUnreadCount(int employerId,int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "select count(*) from CandidateReport where reportState=0 and testId in ";
		String filter = "(select testId from Invitation where positionId = "+ positionId ;
		filter+= invitationDao.getInvitationFilterByEmployer(positionId, employerId);
		filter+=")";
		Query query = session
				.createQuery(hql + filter);
		Long result = (Long) query.uniqueResult();
		return result == null ? 0 : result;
	}

	@Override
	public void updatePaperInstanceQuesUrl(long paperInstId, int questionId,
			String url) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "update CandidateTestQuestion set videoFile='" + url
				+ "' where id.testId=" + paperInstId + " and id.questionId = "
				+ questionId;
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperPart> getPaperPart(int paperId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PaperPart.class);
		criteria.add(Restrictions.eq("id.paperId", paperId));
		criteria.add(Restrictions.lt("id.partSeq", 20));
		return criteria.list();
	}

	@Override
	public CandidateTestPart getExamingPart(long paperInstId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTestPart.class);
		criteria.add(Restrictions.eq("id.TestId", paperInstId));
		criteria.add(Restrictions.eq("partState", 1));
		return (CandidateTestPart) criteria.uniqueResult();
	}

	/**
	 * 根据职位类别id获取试卷个数
	 * 
	 * @param seriesId
	 * @return
	 */
	public int getCountPaperBySeriesId(int seriesId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("select count(*)  from Paper where seriesId = "
						+ seriesId + " and ( prebuilt = 1 )");
		Long r = (Long) q.uniqueResult();
		logger.debug("the paper size is {} for seriesId {} ", r, seriesId);
		return r == null ? 0 : r.intValue();
	}

	/**
	 * 获取试卷信息
	 * 
	 * @param seriesId
	 * @param level
	 * @param employerId
	 * @return
	 * @throws PFDaoException
	 */
	@SuppressWarnings("unchecked")
	public List<PaperInfo> getPaperInfo(int seriesId, int level,
			int employerId, int testType) throws PFDaoException {
		List<PaperInfo> result = new ArrayList<PaperInfo>();
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = " from Paper where  testType = :testType and (createBy = :employerId or prebuilt = 1) ";
		if (testType == Constants.TEST_TYPE_CLUB) {
			hql += " and  seriesId = :seriesId and level = :level";
		}
		hql +=" order by createDate desc ";
		Query query = session.createQuery(hql);
		if (testType == Constants.TEST_TYPE_CLUB) {
			query.setInteger("seriesId", seriesId);
			query.setInteger("level", level);
		}
		query.setInteger("employerId", employerId);
		query.setInteger("testType", testType);
		List<Paper> papers = query.list();
		for (Paper paper : papers) {
			result.add(getPaperInfoFromPaper(paper));
		}
		return result;
	}

	public PaperInfo getPaperInfoFromPaper(Paper paper){
		PaperInfo paperInfo = new PaperInfo();
		try {
			PropertyUtils.copyProperties(paperInfo, paper);
		} catch (Exception e) {
			logger.error("error process copy ", e);
			throw new RuntimeException(e);
		}
		paperInfo.setCreateDateDesc(PrettyTimeMaker.format(paperInfo
				.getCreateDate()));
		paperInfo.setAnswerNumber(getCountPaperUsedNumber(paperInfo
				.getPaperId()));
		Map<String, List<Long>> questionToTypes = getPaperQuestionTypeInfoByPaper(paper
				.getPaperId());
		List<PaperQuestionTypeInfo> typeInfos = new ArrayList<PaperQuestionTypeInfo>();
		paperInfo.setTypeInfos(typeInfos);
		List<ConfigCodeName> configs = configDao
				.getConfigCode(Constants.CONFIG_QUESTION_TYPE_NAME);
		int questionNum = 0;
		for (ConfigCodeName conf : configs) {
			List<Long> ques = questionToTypes.get(conf.getId().getCodeId());
			if (ques != null && ques.size() > 0) {
				PaperQuestionTypeInfo typeInfo = new PaperQuestionTypeInfo();
				typeInfo.setTypeName(conf.getCodeName());
				typeInfo.setTypeId(conf.getId().getCodeId());
				typeInfo.setQuestionNumber(ques.size());
				// typeInfo.setAvgPoint(statQuestionImpl.getAvgPointByQuestions(ques));
				typeInfos.add(typeInfo);
				questionNum += ques.size();
			}
		}

		// 使用考卷id，得到各部分考卷内容
		List<PaperPart> paperPartList = getPaperPart(paper.getPaperId());
		long paperTime = 0;
		for (PaperPart paperPart : paperPartList) {
			if (null != paperPart.getSuggestTime()) {
				paperTime += paperPart.getSuggestTime();
			}
		}
		paperInfo.setPaperTime(paperTime);
		paperInfo.setQuestionNum(questionNum);
		return paperInfo;
	
	}
	public int getCountPaperUsedNumber(int paperId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("select count(*) from CandidateTest where paperId = :paperId and  endTime != null");
		q.setInteger("paperId", paperId);
		Long r = (Long) q.uniqueResult();
		if (r == null) {
			return 0;
		}
		return r.intValue();
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Long>> getPaperQuestionTypeInfoByPaper(int paperId) {
		Map<String, List<Long>> typeToQues = new HashMap<String, List<Long>>();
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(" from PaperQuestion where id.paperId = :paperId and partSeq < 20  ");
		query.setInteger("paperId", paperId);
		List<PaperQuestion> ques = query.list();
		for (PaperQuestion q : ques) {
			processPaperQuestion(q.getId().getQuestionId(), typeToQues);
		}
		return typeToQues;
	}
	
	public Map<String, List<Long>> getPaperQuestionTypeInfoByQbId(int qbId) {
		Map<String, List<Long>> typeToQues = new HashMap<String, List<Long>>();
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(" from QbQuestion where qbId = :qbId ");
		query.setInteger("qbId", qbId);
		List<QbQuestion> ques = query.list();
		for (QbQuestion q : ques) {
			processPaperQuestion(q.getQuestionId(), typeToQues);
		}
		return typeToQues;
	}
	
	private void processPaperQuestion(long questionId,Map<String, List<Long>> typeToQues){

		QbQuestion question = qbQuestionDaoImpl.getEntity(questionId);
		if (question == null) {
			logger.error("not found the ques for id {} ",questionId);
			return;
		}
		String type = question.getQuestionType();
		int category = question.getCategory();
		if (category == Constants.CATEGORY_SKILL) {// 技能分为选择题,编程题,问答题
			if (type.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE)
					|| type.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE)
					|| type.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS)
					|| type.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS)) {
				putQuestionIdToTypes(question, typeToQues,
						Constants.QUESTION_TYPE_OBJECT);
			} else if (type.equals(Constants.QUESTION_TYPE_NAME_PROGRAM)
					|| type.equals(Constants.QUESTION_TYPE_NAME_TEXT)) {
				putQuestionIdToTypes(question, typeToQues,
						Constants.QUESTION_TYPE_SUBJECT);
			} else  if (type.equals(Constants.QUESTION_TYPE_NAME_ESSAY)) {
				putQuestionIdToTypes(question, typeToQues,
						Constants.QUESTION_TYPE_ESSAY);
			}
			else {
				logger.warn("not found type for question id {} type {} ",
						question.getQuestionId(), type);
			}
		} else if (category == Constants.CATEGORY_BUSI) {// 业务
			putQuestionIdToTypes(question, typeToQues,
					Constants.QUESTION_TYPE_BUSINESS);
		} else if (category == Constants.CATEGORY_INTE) {// 智力
			putQuestionIdToTypes(question, typeToQues,
					Constants.QUESTION_TYPE_INTELLIGE);
		} else if (category == Constants.CATEGORY_INTER) {// 面试题
			if (type.equals(Constants.QUESTION_TYPE_NAME_INTERVIEW)) {
				putQuestionIdToTypes(question, typeToQues,
						Constants.QUESTION_TYPE_INTERVIEW);
			}
		} else {
			logger.warn("not found type four id {} type {} ",
					question.getQuestionId(), type);
		}
	
	}

	/**
	 * 
	 * @param question
	 * @param typeToQues
	 * @param questionType
	 *            选择题 编程题 业务题 智力题 面试题
	 */
	private void putQuestionIdToTypes(QbQuestion question,
			Map<String, List<Long>> typeToQues, String questionType) {
		List<Long> questionIds = typeToQues.get(questionType);
		if (questionIds == null) {
			questionIds = new ArrayList<Long>();
			typeToQues.put(questionType, questionIds);
		}
		questionIds.add(question.getQuestionId());
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<Paper> getPrebuiltPaper() {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Paper where prebuilt = " + Constants.PREBUILT_SYS +" order by level");
		return q.list();
	}
	
	/**
	 * 获得完成该试卷的大致时间（大致时间是指所有题正确完成时间平均值的总和）
	 * @param paperId
	 * @return
	 */
	@Override
	public Double getPaperTotalTime(Integer paperId)
	{
	    List<PaperQuestion> paperQuestions = paperQuestionDaoImpl.getNormalPaperQuestions(paperId);
	    Double totalTime = 0d;
	    for (PaperQuestion paperQuestion : paperQuestions)
        {
	        StatQuestion statQuestion = statQuestionDaoImpl.getEntity(paperQuestion.getId().getQuestionId());
	        // 如果存在平均时间则取平均作答时间；如果没有人答对过那么取建议作答时间
            if(null==statQuestion || null==statQuestion.getAvgTime() || 0==statQuestion.getAvgTime())
	        {
	            QbQuestion qbQuestion = qbQuestionDaoImpl.getEntity(paperQuestion.getId().getQuestionId());
	            totalTime += qbQuestion.getSuggestTime();
	        }else
	        {
	            totalTime += statQuestion.getAvgTime();
	        }
        }
	    return totalTime;
	   /* totalTime = Math.ceil(totalTime/60);
	    return totalTime.intValue();*/
	}
}
