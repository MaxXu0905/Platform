package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.school.domain.ReportSkillsScoreInfo;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.ailk.sets.platform.intf.school.domain.SchoolReportCondition;
import com.ailk.sets.platform.intf.school.domain.SchoolReportInfo;

public interface ISchoolReportService {
	/**
	 * 获取报告列表
	 * @return
	 */
	public List<?> getSchoolReportList(SchoolReportCondition condition);
    /**
     * 获取测评各个科目分数
     * @param testId
     * @return
     */
	public List<ReportSkillsScoreInfo>  getReportSkillsScoreInfo(long testId);
	
	/**
	 * 获取报告总数
	 * @return
	 */
	public long getCountReportList(SchoolReportCondition condition);
	
	/**
	 * 设置测试结果
	 * @param testId
	 * @param choose
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateTestResult(long testId, int choose) throws PFServiceException;
	
	public SchoolReportInfo getSchoolReportInfo(long testId) throws PFServiceException;
	
	 /**
	  * 
	  * @param employerId
	  * @param posOrActId
	  * @param type  1测评id  2活动
	  * @return
	  */
	public List<ReportStatusCountInfo> getSchoolReportStatusCount(int employerId,int posOrActId, int type);
	
	
	/**
	 * 获取报告列表 , 内存中过滤
	 * @return
	 */
	public List<SchoolCandidateReport> getSchoolReportListInMem(SchoolReportCondition condition);
	
}
