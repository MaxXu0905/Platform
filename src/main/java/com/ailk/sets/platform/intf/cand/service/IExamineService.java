package com.ailk.sets.platform.intf.cand.service;

import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.CandidateExamInfo;
import com.ailk.sets.platform.intf.cand.domain.ExamineTimeInfo;
import com.ailk.sets.platform.intf.cand.domain.PaperData;
import com.ailk.sets.platform.intf.cand.domain.PaperMarkData;
import com.ailk.sets.platform.intf.cand.domain.QuestionExt;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.common.PaperCreateResult;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.monitor.MonitorInfo;

/**
 * 应聘者考试接口
 * 
 * @author panyl
 * 
 */
public interface IExamineService {
	/**
	 * 获取剩余时间
	 * 
	 * @param paperId
	 * @param partSeq
	 * @return
	 */
	public ExamineTimeInfo getExamineTimeLeft(long testId, int partSeq);

	/**
	 * 标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public boolean markQuestion(long testId, int partSeq, long qId);

	/**
	 * 取消标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public boolean unMarkQuestion(long testId, int partSeq, long qId);

	/**
	 * 获取试卷基本数据
	 * 
	 * @param testId
	 * @param passCode
	 * @return
	 */
	public PaperData getPaperData(long testId, String passCode);

	/**
	 * 更新部分状态
	 * 
	 * @param testId
	 * @param partSeq
	 * @param status
	 * @return
	 */
	public PFResponse updatePaperInstancePartStatus(long testId, int partSeq, int status);

	/**
	 * 
	 * @param testId
	 * @param type  1:按部分获取剩余时间   2:按题目获取剩余时间
	 * @return
	 * @throws Exception
	 */
	public CandidateExamInfo getCandidateExamInfo(long testId, int type) throws Exception;

	/**
	 * 交卷
	 * 
	 * @param testId
	 * @param partSeq
	 * @param status
	 * @return
	 */
	public PFResponse updatePaperInstance(long testId, int status);

	/**
	 * 标记or取消标记
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @return
	 */
	public boolean markOrUnMarkQuestin(long testId, int partSeq, long qId);

	/**
	 * 获取标记题目信息
	 * 
	 * @param testId
	 * @return
	 */
	public List<PaperMarkData> getPaperMarkDatas(long testId);

	/**
	 * 跟踪答题到哪部分哪一题
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @return
	 */
	public PFResponse updatePartSeqAndQuesitonId(long testId, int partSeq,long qId,int partIndex,int questionIndex);

	/**
	 * 更新答题时间
	 * 
	 * @param testId
	 * @param qId
	 * @param answerTime
	 * @return
	 */
	public PFResponse addTimeToPaperInstanceQuestion(long testId, long qId, int answerTime);

	/**
	 * 获取题目类型
	 * 
	 * @param qId
	 * @return
	 */
	public QbQuestion getQuestionById(long qId);

	/**
	 * 获取面试题剩余时间信息
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public QuestionExt getQuestionExt(long testId, long qId);

	/**
	 * 更新试卷题目信息 (面试题)
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @param type
	 *            1开始答题 2结束答题
	 * @return
	 */
	public PFResponse updatePaperInstanceQuestionInfo(long testId, int partSeq, long qId, int type);

	/**
	 * 更新中断次数
	 * 
	 * @param testId
	 * @return
	 */
	public PFResponse updateInterrupt(long testId) throws PFServiceException;

	/**
	 * 更新考试状态，用于跟踪到哪一个页面，用于页面还原
	 * 
	 * @param testId
	 * @param status
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateCandidateExamStatus(long testId, int status);

	/**
	 * 根据邀请id获取CandidateExam
	 * 
	 * @param testId
	 * @return
	 */
	public CandidateTest getCandidateExamByInviId(long testId);

	/**
	 * 开始考试
	 * 
	 * @param testId
	 * @return
	 */
	public PFResponse startExamPaper(long testId);

	/**
	 * 获取切换次数的配置信息
	 * 
	 * @return
	 * @throws PFServiceException 
	 */
	public MonitorInfo getMonitorInfo(long testId) throws PFServiceException;
	
	/**
	 * 应聘者调用生成试卷实例
	 * @param testId
	 * @param inviteCode
	 * @return
	 */
	public PFResponse createPaper(long testId, String inviteCode) throws PFServiceException;
	
	/**
	 * 更新生成试卷状态
	 * @return
	 */
	public PFResponse updateCandidateTestPaperState(long testId, int paperState);
	
	/**
	 * 获取生成试卷状态
	 * @param testId
	 * @return
	 */
	public PaperCreateResult getCandidateTestPaperState(long testId);
	
	/**
	 * 获取测试试卷信息
	 * @param testId
	 * @param passCode
	 * @return
	 */
	public PaperData getTestPaperData(long testId, String passCode);
	
	/**
	 * 出报告  并返回排名信息
	 * @param testId
	 * @return
	 */
	public PFResponseData<CandReportAndInfo> gradeAndGetRanking(long testId) throws Exception;
}
