package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.common.OutResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReportPart;
import com.ailk.sets.platform.intf.empl.domain.PaperModel;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.candidateReport.PositionGroupReport;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;

public interface IReport {

	/**
	 * 设置测试结果
	 * 
	 * @param testId
	 * @param choose
	 * @return
	 * @throws PFServiceException
	 */
	public PositionStatistics setTestResult(int employerId, int positionId, long testId, int choose) throws PFServiceException;

	/**
	 * 根据邀请码获取测试结果
	 * 
	 * @param testId
	 * @return
	 */
	public int getTestResult(long testId);

	/**
	 * 获取试卷模板构成
	 * 
	 * @param positionId
	 * @return
	 */
	public PaperModel getPaperModel(int positionId) throws Exception;
	
	/**
	 * 获取试卷模板构成
	 * 
	 * @param paperId
	 * @return
	 */
	public PaperModel getPaperModelByPaperId(int paperId) throws Exception;
	
	/**
	 * 获取试卷模板构成
	 * 
	 * @param positionId
	 * @return
	 */
//	public PaperModel getPaperModel(PositionPaperAnalysisResult result,Position position);

	/**
	 * @return
	 */
	public List<ConfigReport> getConfigReport();

	/**
	 * @return
	 */
	public List<ConfigReportPart> getConfigReportPart();

	/**
	 * 根据报告id获取报告
	 * 
	 * @param testId
	 * @return
	 */
	public CandidateReport getCandidateReport(long testId);

	/**
	 * 根据应聘人获取报告
	 * 
	 * @param candidateId
	 * @return
	 */
	public List<CandidateReport> getReportByCandidate(int candidateId);

	/**
	 * 根据测试结果状态获取报告列表等信息
	 * 
	 * @param positionId
	 * @param testResult
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<CandReportAndInfo> getReport(GetReportParam param) throws PFServiceException;

	/**
	 * 设置报告状态已读
	 * 
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse setReportStateRead(int employerId, int positionId, long testId) throws PFServiceException;

	/**
	 * 是否是该report的拥有者
	 * 
	 * @param employerId
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse ownReport(int employerId, long testId) throws PFServiceException;
	/**
	 * 第三方调用更新测评状态
	 * @param employerId
	 * @param positionId
	 * @param testId
	 * @param choose
	 * @return
	 */
	public OutResponse setTestResultOnly(int employerId,long testId, int choose);
	 
	/**
	  * 获取账号状态信息
	  * @param testId
	  * @param passport
	  * @return
	  */
	public PFResponseData<Integer>  getEmployerStatus(long testId, String passport);
	
	/**
	 * 获取报告数据  内存过滤
	 * @param param
	 * @return
	 */
	public List<PositionGroupReport> getReportInMem(GetReportParam param);
	
	/**
	 * 获取报告数量
	 * @param employerId
	 * @param positionId
	 * @param activityId
	 * @return
	 */
	public int getReportNumber(int employerId, Integer positionId, Integer activityId);
}
