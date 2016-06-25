package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.ConfigReportKpi;

public interface IConfigReportKpiDao {

	public List<ConfigReportKpi> getListByOpenSession();

}
