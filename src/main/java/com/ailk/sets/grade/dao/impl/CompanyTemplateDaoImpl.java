package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICompanyTemplateDao;
import com.ailk.sets.grade.jdbc.CompanyTemplate;
import com.ailk.sets.grade.jdbc.CompanyTemplatePK;

@Repository
public class CompanyTemplateDaoImpl implements ICompanyTemplateDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public CompanyTemplate get(int employerId, int testType, String templateId) {
		Session session = sessionFactory.getCurrentSession();

		CompanyTemplatePK companyTemplatePK = new CompanyTemplatePK();
		companyTemplatePK.setEmployerId(employerId);
		companyTemplatePK.setTestType(testType);
		companyTemplatePK.setTemplateId(templateId);

		return (CompanyTemplate) session.get(CompanyTemplate.class,
				companyTemplatePK);
	}

	@Override
	public void saveOrUpdate(CompanyTemplate companyTemplate) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(companyTemplate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyTemplate> getList(int employerId, int testType) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CompanyTemplate WHERE companyTemplatePK.employerId = ?1 AND companyTemplatePK.testType = ?2")
				.setInteger("1", employerId).setInteger("2", testType).list();
	}

}
