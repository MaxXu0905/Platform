package com.ailk.sets.platform.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameId;

@Repository
public class PositionSeriesDaoImpl extends BaseDaoImpl<PositionSeries> implements IPositionSeriesDao {
	private Logger logger = LoggerFactory.getLogger(PositionSeriesDaoImpl.class);

	@Autowired
	private IPaperDao paperDao;
	@Autowired
	private StatQuestionByseriesDegreeDaoImpl statQuestionByseriesDegreeDaoImpl;

	public List<PositionSeries> getPositionSeries(int employerId) {
		Session session = getSession();
		String secondLevel = "from  PositionSeries where seriesId != " + Constants.POSITION_SEREIS_ID_SCHOOL
				+ " and   seriesType = 2 and (prebuilt = 1 or createBy = " + employerId + ")  order by seriesId asc ";
		List<PositionSeries> secondSeries = session.createQuery(secondLevel).list();
		logger.debug("secondSeries size is {} ", secondSeries.size());
		for (PositionSeries secSeri : secondSeries) {
			String thirdLevel = "from  PositionSeries where  seriesType = 3  and parentId = " + secSeri.getSeriesId() + " and (prebuilt = 1 or createBy = "
					+ employerId + ")  ";
			List<PositionSeries> thirdSeries = session.createQuery(thirdLevel).list();
			logger.debug("thirdSeries size is {} ", thirdSeries.size());
			if (CollectionUtils.isNotEmpty(thirdSeries)) {
				secSeri.setChildren(thirdSeries);
			}
			for (PositionSeries thiSeri : thirdSeries) {
//				thiSeri.setSysPaperNumber(paperDao.getCountPaperBySeriesId(thiSeri.getSeriesId()));// 设置是否有百一试卷
				int res = statQuestionByseriesDegreeDaoImpl.getQuestionNumber(thiSeri.getSeriesId()) ;
				thiSeri.setSysQuestionNumber(res > 0 ? 1 : 0);
			}
		}
		return secondSeries;
	}
	
	public List<PositionSeries> getPositionSeriesByName(String seriesName,int employerId){
		String q = "from  PositionSeries where  seriesType = 3  and seriesName=:seriesName and (prebuilt = 1 or createBy = "
				+ employerId + ")  ";
		Session session = getSession();
		Query query = session.createQuery(q);
		query.setString("seriesName", seriesName);
		return query.list();
	}

	@Override
	@Cacheable(value = "positionSeries")
	public PositionSeries getEntity(Serializable objId){
		Session session = sessionFactory.getCurrentSession();
		return (PositionSeries) session.get(PositionSeries.class,
				objId);
	}

	@Override
	@CacheEvict(value = "positionSeries", allEntries = true)
	public void evict() {
	}

}
