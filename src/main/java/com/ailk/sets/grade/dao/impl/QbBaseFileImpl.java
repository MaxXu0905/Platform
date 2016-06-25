package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IQbBaseFileDao;
import com.ailk.sets.grade.jdbc.QbBaseFile;

@Repository
public class QbBaseFileImpl implements IQbBaseFileDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "qbBaseFile")
	public int get(int fileId) {
		Session session = sessionFactory.getCurrentSession();

		QbBaseFile qbBaseFile = (QbBaseFile) session.get(QbBaseFile.class, fileId);
		return qbBaseFile.getQbId();
	}

	@Override
	@CacheEvict(value = "qbBaseFile", allEntries = true)
	public void evict() {
	}

}
