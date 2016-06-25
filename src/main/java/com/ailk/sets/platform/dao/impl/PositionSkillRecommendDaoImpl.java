package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.PositionSkillRecommend;

@Repository
public class PositionSkillRecommendDaoImpl {
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Cacheable(value = "positionSkillRecommend")
	public List<PositionSkillRecommend> getPositionSkillRecommends(int seriesId, int level){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PositionSkillRecommend where id.seriesId = :seriesId and id.positionLevel =:positionLevel");
		query.setInteger("seriesId", seriesId);
		query.setInteger("positionLevel", level);
		return query.list();
	}
	
	@CacheEvict(value = "positionSkillRecommend", allEntries = true)
	public void evict() {
	}


}
