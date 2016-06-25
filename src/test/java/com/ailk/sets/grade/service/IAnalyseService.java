package com.ailk.sets.grade.service;

import java.util.List;

import com.ailk.sets.grade.jdbc.Analyse;
import com.ailk.sets.grade.jdbc.AnalyseResult;

public interface IAnalyseService {
	
	public List<Analyse> getList();

	public void saveOrUpdate(List<AnalyseResult> analyseResults);
	
}
