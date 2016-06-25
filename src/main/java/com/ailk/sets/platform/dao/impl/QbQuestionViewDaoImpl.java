package com.ailk.sets.platform.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.util.PaperCreateUtils;

/**
 * 问题试卷dao
 * 
 * @author panyl
 * 
 */
@Repository
public class QbQuestionViewDaoImpl {
	private Logger logger = LoggerFactory.getLogger(QbQuestionViewDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	private IConfigSysParamDao configSysParamDao;

	@Cacheable(value = "qbQuestionView")
	public List<Long> getPaperQuestionsByDegreeAndSkill(int degree, String skillId,
			PaperPartSeqEnum paperType) {
		logger.debug("============== degree={},skillId={}",degree,skillId);
		String sql = "select a.question_id from qb_question_skill a, " + 
		" (select * from qb_question where question_type in (:questionType) and is_sample=0 and state=1 and prebuilt = 1 and category = 1 and qb_id != 90000   and degree = :degree ) b" + 
		" where a.question_id = b.question_id and a.skill_id=:skillId  ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("degree", degree);
		query.setParameterList("questionType", PaperCreateUtils.getQuestionTypesInArray(paperType));
		query.setParameter("skillId", skillId);
		@SuppressWarnings("unchecked")
		List<Long> list = query.setResultTransformer(new ResultTransformer() {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				return ((BigInteger)tuple[0]).longValue();
			}
			
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}).list();
//		logger.debug("the db question size is {}  for sql {} , skillId = " + skillId + ", questionType = "
//				+ PaperCreateUtils.getQuestionTypesInStr(paperType) +", degree = "+ degree, list.size(), query.getQueryString());
		return list;
	}
	
	@Cacheable(value = "qbQuestionViewForMoti")
	public List<Long> getMotiQuestionsByDegreeAndSkill(int degree, String skillId, PaperPartSeqEnum paperType) {
		int minNumToMoti;
		try {
			minNumToMoti = Integer.valueOf(configSysParamDao.getConfigParamValue("MIN_NUM_TO_MOTI"));
		} catch (Exception e) {
			logger.error("found MIN_NUM_TO_MOTI error , please check ... ", e);
			minNumToMoti = 10;
		}
		logger.debug("minNumToMoti value is {}", minNumToMoti);
		String sql = "select a.question_id from qb_question_skill a, " + 
				" (select * from qb_question where question_type in (:questionType) and is_sample=0 and state=1  and category = 1 and qb_id != 90000   and degree = :degree  and answer_num < :minNumToMoti ) b," + 
				" employer c "  +
				" where a.question_id = b.question_id and a.skill_id=:skillId and b.create_by = c.employer_id  and c.company_id = 1 ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("degree", degree);
		query.setParameterList("questionType", PaperCreateUtils.getQuestionTypesInArray(paperType));
		query.setParameter("skillId", skillId);
		query.setParameter("minNumToMoti", minNumToMoti);
		@SuppressWarnings("unchecked")
		List<Long> list = query.setResultTransformer(new ResultTransformer() {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				return ((BigInteger)tuple[0]).longValue();
			}
			
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}).list();
		return list;
	}

	@CacheEvict(value = "qbQuestionView", allEntries = true)
	public void evict() {
		logger.debug("clear cache for qbQuestionView");
	}
	
	@CacheEvict(value = "qbQuestionViewForMoti", allEntries = true)
	public void evictMoti() {
		logger.debug("clear cache for qbQuestionViewForMoti");
	}

}
