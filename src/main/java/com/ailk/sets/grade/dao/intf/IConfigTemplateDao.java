package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.grade.jdbc.ConfigTemplate;

public interface IConfigTemplateDao {

	public ConfigTemplate get(int testType, String templateId);

	public void evict();

}
