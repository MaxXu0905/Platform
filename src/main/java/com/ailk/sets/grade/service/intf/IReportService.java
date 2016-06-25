package com.ailk.sets.grade.service.intf;

import java.util.List;

import com.ailk.sets.grade.grade.execute.InvalidDataException;
import com.ailk.sets.grade.intf.report.Interview;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.grade.intf.report.Report;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;

public interface IReportService {

	/**
	 * 获取报告记录
	 * 
	 * @param testId
	 *            测试ID
	 * @return 报告对象
	 * @throws InvalidDataException
	 */
	public CandidateReport loadCandidateReport(long testId)
			throws InvalidDataException;

	/**
	 * 获取测试记录
	 * 
	 * @param testId
	 *            测试ID
	 * @throws InvalidDataException
	 */
	public CandidateTest loadCandidateTest(long testId)
			throws InvalidDataException;

	/**
	 * 获取面试信息及评分
	 * 
	 * @param employerId
	 *            招聘人ID
	 * @param testType
	 *            测试类型
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public Interview getInterview(int employerId, int testType, long testId)
			throws Exception;

	/**
	 * 获取面试信息
	 * 
	 * @param employerId
	 *            招聘人ID
	 * @param testType
	 *            测试类型
	 * @return
	 * @throws Exception
	 */
	public InterviewInfo getInterviewInfo(int employerId, int testType)
			throws Exception;

	/**
	 * 获取面试评分
	 * 
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public List<InterviewItem> getInterviewItems(long testId) throws Exception;

	/**
	 * 获取全面概述
	 * 
	 * @param report
	 *            报告
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public List<OverallItem> getOverallItems(Report report, long testId)
			throws Exception;

	
	/**
	 * 获得未推送报告
	 * @return
	 */
	public List<CandidateReport> getUnNotifiedReport();
	
	/**
	 * 更新报告！
	 * @param candidateReport
	 * @throws Exception
	 */
	public void updateReport(CandidateReport candidateReport)throws Exception;
}
