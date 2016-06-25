package com.ailk.sets.platform.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.PositionLevelId;

@Repository
public class PositionLevelDaoImpl {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Cacheable(value = "positionLevel")
	public PositionLevel getPositionLevel(int seriesId, int levelId) {
		Session session = sessionFactory.getCurrentSession();

		return (PositionLevel) session.get(PositionLevel.class,
				new PositionLevelId(seriesId, levelId));
	}

	@CacheEvict(value = "positionLevel", allEntries = true)
	public void evict() {
	}

}