package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.Analyse;

public interface IAnalyseDao {

	public List<Analyse> getList();

	public void saveOrUpdate(Analyse analyse);

}
