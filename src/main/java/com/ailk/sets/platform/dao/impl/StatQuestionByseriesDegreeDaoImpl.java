package com.ailk.sets.platform.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.StatQuestionByseriesDegree;
import com.ailk.sets.platform.domain.StatQuestionByseriesDegreeId;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Repository
public class StatQuestionByseriesDegreeDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;

	private Logger logger = LoggerFactory
			.getLogger(StatQuestionByseriesDegreeDaoImpl.class);

	@Cacheable(value = "statQuestionByseriesDegree")
	public int getObjectQuestionNumber(int seriesId, int levelId,
			String skillId, int degreeId) {
		Session session = sessionFactory.getCurrentSession();

		int totalNumber = 0;
		for (String questionType : PaperCreateUtils
				.getQuestionTypesInArray(PaperPartSeqEnum.OBJECT)) {
			StatQuestionByseriesDegreeId id = new StatQuestionByseriesDegreeId(
					seriesId, levelId, skillId, degreeId, questionType);
			StatQuestionByseriesDegree res = (StatQuestionByseriesDegree) session
					.get(StatQuestionByseriesDegree.class, id);
			if (res != null)
				totalNumber += res.getQuestionNum();
		}

		return totalNumber;
	}
	@Cacheable(value = "statQuestionByseriesDegreeBySeriesId")
	public int getQuestionNumber(int seriesId) {
		Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from StatQuestionByseriesDegree where id.seriesId = " + seriesId);
        Long res = (Long)query.uniqueResult();
        if(res == null)
        	return 0;
        return res.intValue();
	}
	
	@Caching(evict = { @CacheEvict(value = "statQuestionByseriesDegree", allEntries = true),
			@CacheEvict(value = "statQuestionByseriesDegreeBySeriesId", allEntries = true) })
	public void evict() {
		logger.debug("clear cache for statQuestionByseriesDegree");
	}

}
