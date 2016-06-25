package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IConfigCodeNameDao;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameId;

@Repository
public class ConfigCodeNameDaoImpl implements IConfigCodeNameDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "configCodeName")
	public ConfigCodeName get(ConfigCodeNameId configCodeNameId) {
		Session session = sessionFactory.getCurrentSession();

		return (ConfigCodeName) session.get(ConfigCodeName.class,
				configCodeNameId);
	}

	@Override
	@CacheEvict(value = "configCodeName", allEntries = true)
	public void evict() {
	}

}
