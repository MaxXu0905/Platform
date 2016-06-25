package com.ailk.sets.grade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IAnalyseDao;
import com.ailk.sets.grade.dao.intf.IAnalyseResultDao;
import com.ailk.sets.grade.jdbc.Analyse;
import com.ailk.sets.grade.jdbc.AnalyseResult;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnalyseService implements IAnalyseService{
	
	@Autowired
	private IAnalyseDao analyseDao;
	
	@Autowired
	private IAnalyseResultDao analyseResultDao;
	
	@Override
	public List<Analyse> getList() {
		return analyseDao.getList();
	}
	
	public void saveOrUpdate(List<AnalyseResult> analyseResults) {
		for (AnalyseResult analyseResult : analyseResults) {
			analyseResultDao.saveOrUpdate(analyseResult);
		}
	}
	
}
