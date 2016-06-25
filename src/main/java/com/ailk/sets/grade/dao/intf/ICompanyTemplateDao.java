package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.CompanyTemplate;

public interface ICompanyTemplateDao {

	public CompanyTemplate get(int employerId, int testType, String templateId);

	public void saveOrUpdate(CompanyTemplate companyTemplate);

	public List<CompanyTemplate> getList(int employerId, int testType);

}
