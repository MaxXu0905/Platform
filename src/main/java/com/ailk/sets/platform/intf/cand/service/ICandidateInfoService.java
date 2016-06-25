package com.ailk.sets.platform.intf.cand.service;

import java.util.List;

import com.ailk.sets.grade.intf.report.InterviewInfo.ConfigRegionInfo;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.InfoNeeded;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.ConfigCollege;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Mapping;

/**
 * 
 * 应聘者基本信息服务
 * 
 * @author 毕希研
 * 
 */
public interface ICandidateInfoService {
	/**
	 * 获取常规信息配置以及应聘者填写的信息
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public List<InfoNeeded> getCandConfigInfoExts(long testId) throws PFServiceException;

	public boolean saveCandidateInfo(long testId,List<CandidateInfoExt> list);
	
	public CandidateTest getCandidateTest(long paperInstId);
	
	public List<ConfigRegionInfo> getConfigRegionInfos() throws Exception ;
	
//	public List<ConfigCollegeInfo> getConfigCollegeInfos() throws Exception ;
	
	public List<ConfigCollege> getConfigColleges(String searchTxt, int size) throws Exception ;
	
	public List<Mapping> getQueryInfoBySearchName(long testId,String infoId, String searchName,int size);
	
	public List<Mapping> getQueryInfoBySearchName(String infoId, String searchName,int size);
	
	public Candidate getCandidateByOpenId(String openId);
}
