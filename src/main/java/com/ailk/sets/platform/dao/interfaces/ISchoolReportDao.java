package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.school.domain.ReportSkillsScoreInfo;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;

public interface ISchoolReportDao {
	public List<SchoolCandidateReport> getSchoolReportList(Integer employerId,Integer positionId,Integer activityId,Page page,String searchTxt,Integer testResult,String intentPos) ;
	
	

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
	public long getCountReportList(Integer employerId, Integer positionId, Integer activityId, Page page, String searchTxt,
			Integer testResult, String intentPos);
	/**
	 * 获取报告分数
	 * @param testId
	 * @return
	 */
	public Double getGetScore(long testId);
	
	 /**
	  * 
	  * @param employerId
	  * @param posOrActId
	  * @param type  1测评id  2活动
	  * @return
	  */
	public List<ReportStatusCountInfo> getSchoolReportStatusCount(int employerId,int posOrActId, int type);
	
	/**
	 * 根据招聘者获取统计信息
	 * @param employerId
	 * @return
	 */
	public List<ReportStatusCountInfo> getSchoolReportStatusCountByEmployerId(int employerId, int testType);
	
	/**
	 * 根据测评id获取统计信息
	 * @param positionId
	 * @return
	 */
	public List<ReportStatusCountInfo> getSchoolReportStatusCountByPositionId(int employerId, int positionId);
	
	/**
	 * 根据活动id获取所有测评id
	 * @param activityId
	 * @return
	 */
	public List<SchoolCandidateReport> getAllSchoolReportList(Integer employerId, Integer positionId, Integer activityId, Integer testResult) ;

}
