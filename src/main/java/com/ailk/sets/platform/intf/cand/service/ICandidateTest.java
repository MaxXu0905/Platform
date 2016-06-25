package com.ailk.sets.platform.intf.cand.service;

import java.util.List;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.Times;
import com.ailk.sets.platform.intf.model.candidateTest.AbNormalInfo;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateTestMonitorClone;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateTestSwitchPage;
import com.ailk.sets.platform.intf.model.candidateTest.InvitationOnLine;
import com.ailk.sets.platform.intf.model.feedback.CandidateTestFeedback;
import com.ailk.sets.platform.intf.model.feedback.FeedbackCountInfo;

public interface ICandidateTest {
	/**
	 * 保存一条测试监控信息
	 * 
	 * @param candidateTestMonitorClone
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse saveTestMonitor(CandidateTestMonitorClone candidateTestMonitorClone) throws PFServiceException;

	/**
	 * 获取测试监控url
	 * 
	 * @param testId
	 * @param isAbnormal
	 * @return
	 * @throws PFServiceException
	 */
	public AbNormalInfo getTestMonitor(long testId, int isAbnormal, Page page) throws PFServiceException;

	/**
	 * 更新应聘者报告中头像的url
	 * 
	 * @param testId
	 * @param url
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateCandidatePic(long testId, String url) throws PFServiceException;

	/**
	 * 检查应聘者头像
	 * 
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse checkCandidatePic(long testId) throws PFServiceException;

	/**
	 * 更新切换次数
	 * 
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public Times updateSwitchTimes(CandidateTestSwitchPage candidateTestSwitchPage) throws PFServiceException;

	/**
	 * 更新刷新次数
	 * 
	 * @param testId
	 * @return
	 * @throws PFServiceException
	 */
	public Times updateFreshTimes(long testId) throws PFServiceException;

	/**
	 * 保存吐槽
	 * 
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse saveFeedBack(CandidateTestFeedback candidateTestFeedback) throws PFServiceException;

	/**
	 * 获取二维码图片
	 * 
	 * @return
	 * @throws PFServiceException
	 */
	public String getQRPicUrl(long testId) throws PFServiceException;
	
	/**
	 * 更新candidateTestlog  包括答题者ip，浏览器，操作系统等等
	 * 
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateCandidateTestLog(long testId,EmployerOperationLog log) throws PFServiceException;
	/**
	 * 获取题目的评论汇总信息
	 * @param questionId
	 * @return
	 */
	public FeedbackCountInfo getFeedbackCountInfo(long questionId)  throws PFServiceException;
	/**
	 * 获取评论信息
	 * @param questionId
	 * @param page
	 * @return
	 */
	public List<CandidateTestFeedback> getCandidateTestFeedbacks(long questionId, Page page);
	/**
	 * 根据口令获取在线申请测评邀请信息
	 * @param passport
	 * @return
	 */
	public InvitationOnLine getInvitationOnLineByPassport(String passport);
	
}
