package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.intf.report.InterviewInfo.ConfigRegionInfo;

public interface IConfigRegionDao {

	public List<ConfigRegionInfo> getConfigRegionInfos() throws Exception;
	
	public void evict();

}
