package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IConfigTemplateDao;
import com.ailk.sets.grade.jdbc.ConfigTemplate;
import com.ailk.sets.grade.jdbc.ConfigTemplatePK;

@Repository
public class ConfigTemplateDaoImpl implements IConfigTemplateDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "configTemplate")
	public ConfigTemplate get(int testType, String templateId) {
		Session session = sessionFactory.getCurrentSession();
		
		ConfigTemplatePK configTemplatePK = new ConfigTemplatePK();
		configTemplatePK.setTestType(testType);
		configTemplatePK.setTemplateId(templateId);

		return (ConfigTemplate) session.get(ConfigTemplate.class, configTemplatePK);
	}

	@Override
	@CacheEvict(value = "configTemplate", allEntries = true)
	public void evict() {
	}

}
