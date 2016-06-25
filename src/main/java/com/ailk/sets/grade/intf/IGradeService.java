package com.ailk.sets.grade.intf;

import java.util.List;

import com.ailk.sets.grade.intf.report.GetComparedReportsResponse;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;

public interface IGradeService {

	/**
	 * 检查答题环境
	 * 
	 * @param testId
	 *            测试ID
	 * @return
	 */
	public BaseResponse chechEnv(long testId);

	/**
	 * 生成试卷请求
	 * 
	 * @param testId
	 *            测试ID
	 * @param qids
	 *            试题ID列表
	 * @author xugq
	 * 
	 */
	public BaseResponse genExam(long testId, List<Long> qids) throws Exception;

	/**
	 * 获取试题ID列表
	 * 
	 * @param testId
	 *            邀请ID
	 */
	public GetQidsResponse getQids(long testId) throws Exception;

	/**
	 * 获取试题详细信息
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @author xugq
	 * 
	 */
	public GetQInfoResponse getQInfo(long testId, long qid) throws Exception;

	/**
	 * 获取附加题详细信息
	 * 
	 * @param qid
	 *            试题ID
	 * @return
	 */
	public GetQInfoResponse getQInfo(long qid) throws Exception;

	/**
	 * 编程题、附加题提交文件
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @param items
	 *            变更文件列表
	 * @return
	 */
	public BaseResponse commitFiles(long testId, long qid,
			List<CommitFile> items) throws Exception;

	/**
	 * 选择题提交答案请求
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @param answer
	 *            答案
	 * @param desc
	 *            答案说明
	 * @answer 答案
	 * 
	 */
	public BaseResponse commitChoice(long testId, long qid, String answer,
			String desc) throws Exception;

	/**
	 * 编程题测试
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @param items
	 *            变更文件列表
	 * @param arg
	 *            参数
	 * @return
	 */
	public RunTestResponse runTest(long testId, long qid,
			List<CommitFile> items, String arg) throws Exception;

	/**
	 * 编程题测试样例
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @param items
	 *            变更文件列表
	 * @param sampleId
	 *            样例ID
	 * @return
	 */
	public RunTestResponse runTest(long testId, long qid,
			List<CommitFile> items, int sampleId) throws Exception;

	/**
	 * 获取报告详情
	 * 
	 * @param reportId
	 *            报告ID
	 * @return
	 */
	public GetReportResponse getReport(long reportId) throws Exception;

	/**
	 * 获取报告详情
	 * 
	 * @param reportId
	 *            报告ID
	 * @param reportPassport
	 *            报告通行证
	 * @return
	 */
	public GetReportResponse getReport(long reportId, String reportPassport)
			throws Exception;

	/**
	 * 获取报告的概述
	 * 
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public GetReportSummaryResponse getReportSummary(long testId)
			throws Exception;

	/**
	 * 立即出报告，并获取报告的概述
	 * 
	 * @param testId
	 *            测试ID
	 * @return
	 * @throws Exception
	 */
	public GetReportSummaryResponse gradeReport(long testId) throws Exception;

	/**
	 * 获取待比较的报告信息
	 * 
	 * @param testIds
	 *            待比较测试列表
	 * @return
	 * @throws Exception
	 */
	public GetComparedReportsResponse getComparedReports(List<Long> testIds)
			throws Exception;

	/**
	 * 保存面试相关的信息
	 * 
	 * @param testId
	 *            测试ID
	 * @param items
	 *            面试信息
	 * @return
	 */
	public ScoreResponse saveInterview(long testId, List<InterviewItem> items)
			throws Exception;

	/**
	 * 获取面试相关的信息
	 * 
	 * @param employerId
	 *            招聘人ID
	 * @param testType
	 *            测试类型
	 * @param testId
	 *            测试ID
	 * @return 面试相关信息
	 */
	public GetInterviewResponse getInterview(int employerId, int testType,
			long testId) throws Exception;

	/**
	 * 对试题打分
	 * 
	 * @param anchor
	 *            锚点
	 * @param testId
	 *            测试ID
	 * @param questionId
	 *            试题ID
	 * @param score
	 *            得分
	 * @return
	 */
	public ScoreResponse scoreQuestion(int anchor, long testId,
			long questionId, double score);

	/**
	 * 获取候选人基本信息
	 * 
	 * @param candidateId
	 *            候选人ID
	 * @param employerId
	 *            招聘人ID
	 * @param positionId
	 *            职位ID
	 * @return
	 */
	public GetCandidateInfoResponse getCandidateInfo(int candidateId,
			int employerId, int positionId) throws Exception;

	/**
	 * 编程题测试前准备
	 * 
	 * @param testId
	 *            邀请ID
	 * @param qid
	 *            试题ID
	 * @param items
	 *            变更文件列表
	 * @param response
	 *            应答
	 * @return 候选题对象
	 */
	/*public CandidateExamQuestion prepareTest(long testId, long qid,
			List<CommitFile> items, RunTestResponse response) throws Exception;*/

}
