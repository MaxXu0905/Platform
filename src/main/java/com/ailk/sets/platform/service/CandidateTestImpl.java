package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestSwitchPageDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IFeedBackDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IOnlineExamReqDao;
import com.ailk.sets.platform.dao.interfaces.IStatQuestionDao;
import com.ailk.sets.platform.domain.CandidateTestMonitor;
import com.ailk.sets.platform.domain.StatQuestion;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.service.ICandidateTest;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.empl.domain.OnlineExamReq;
import com.ailk.sets.platform.intf.empl.service.IConfig;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Mapping;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.Times;
import com.ailk.sets.platform.intf.model.candidateTest.AbNormalInfo;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateTestMonitorClone;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateTestSwitchPage;
import com.ailk.sets.platform.intf.model.candidateTest.InvitationOnLine;
import com.ailk.sets.platform.intf.model.feedback.CandidateTestFeedback;
import com.ailk.sets.platform.intf.model.feedback.FeedbackCountInfo;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;

@Transactional(rollbackFor = Exception.class)
public class CandidateTestImpl implements ICandidateTest {
	@Autowired
	private ICandidateTestDao candidateTestDao;
	@Autowired
	private ICandidateDao candidateDaoImpl;
	@Autowired
	private IFeedBackDao feedBackDaoImpl;
	@Autowired
	private ICandidateTestSwitchPageDao candidateTestSwitchPageDaoImpl;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
    @Autowired
	private IConfig config;
	@Autowired
	private IStatQuestionDao statQuestionDao;
	@Autowired
	private IOnlineExamReqDao onlineExamReqDao;
	@Autowired
	private IInvitationDao invitationDao;
	private Logger logger = LoggerFactory.getLogger(CandidateTestImpl.class);

	@Override
	public PFResponse saveTestMonitor(CandidateTestMonitorClone candidateTestMonitorClone) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			if (candidateTestMonitorClone != null) {
				CandidateTestMonitor candidateTestMonitor = new CandidateTestMonitor();
				PropertyUtils.copyProperties(candidateTestMonitor, candidateTestMonitorClone);
				Timestamp createTime = new Timestamp(System.currentTimeMillis());
				candidateTestMonitor.setCreateTime(createTime);
				candidateTestDao.saveTestMonitor(candidateTestMonitor);
			} else
				pfResponse.setCode(FuncBaseResponse.PARAMETERERR);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("error call saveTestMonitor ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public AbNormalInfo getTestMonitor(long testId, int isAbnormal, Page page) throws PFServiceException {
		AbNormalInfo abNormalInfo = new AbNormalInfo();
		try {
			abNormalInfo.setPicFiles(candidateTestDao.getTestMonitor(testId, isAbnormal, page));
			abNormalInfo.setBreakTimes(candidateTestDao.getCandidateTest(testId).getBreakTimes());
			return abNormalInfo;
		} catch (Exception e) {
			logger.error("error call getTestMonitor ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse updateCandidatePic(long testId, String url) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			candidateTestDao.updateCandidatePic(testId, url);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			pfResponse.setCode(FuncBaseResponse.EXCEPTION);
			logger.error("error call updateCandidatePic ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	public PFResponse checkCandidatePic(long testId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			CandidateTest candidateTest = candidateTestDao.getCandidateTest(testId);
			// 应萌芽的要求,只有正在答题的时候，调用这个方法的时候才做中断次数更新，此处更新了一下中断次数

			if (candidateTest != null) {
				if (candidateTest.getTestState() == 1)
					candidateTestDao.updateBreakTimes(testId);
				if (StringUtils.isNotEmpty(candidateTest.getCandidatePic())) {
					pfResponse.setCode(FuncBaseResponse.SUCCESS);
					pfResponse.setMessage("find candidatepic");
				} else {
					pfResponse.setCode(FuncBaseResponse.FAILED);
					pfResponse.setMessage("no candidatepic" + " and testid is " + testId);
				}
			} else {
				pfResponse.setCode(FuncBaseResponse.FAILED);
				pfResponse.setMessage("cannot find candidateTest" + " and testid is " + testId);
			}
		} catch (Exception e) {
			logger.error("call checkCandidatePic error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public Times updateSwitchTimes(CandidateTestSwitchPage candidateTestSwitchPage) throws PFServiceException {
		Times times = new Times();
		try {
			CandidateTest candidateTest = candidateTestDao.getEntity(candidateTestSwitchPage.getId().getTestId());
			if (candidateTest.getTestState() == 1) {
				candidateTestDao.updateSwitchTimes(candidateTestSwitchPage.getId().getTestId());
				candidateTestSwitchPageDaoImpl.saveOrUpdate(candidateTestSwitchPage);
			}
			times.setCode(FuncBaseResponse.SUCCESS);
			times.setTimes(candidateTest.getSwitchTimes() + 1);
		} catch (Exception e) {
			logger.error("call updateSwitchTimes error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return times;
	}

	@Override
	public Times updateFreshTimes(long testId) throws PFServiceException {
		Times times = new Times();
		try {
			CandidateTest candidateTest = candidateTestDao.getEntity(testId);
			if (candidateTest.getTestState() == 1) {
				candidateTestDao.updateFreshTimes(testId);
			}
			times.setCode(FuncBaseResponse.SUCCESS);
			times.setTimes(candidateTest.getFreshTimes() + 1);
		} catch (Exception e) {
			logger.error("call updateFreshTimes error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return times;
	}

	@Override
	public PFResponse saveFeedBack(CandidateTestFeedback candidateTestFeedback) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			candidateTestFeedback.setFbTime(new Timestamp(System.currentTimeMillis()));
			feedBackDaoImpl.saveOrUpdate(candidateTestFeedback);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("call saveFeedBack error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public String getQRPicUrl(long testId) throws PFServiceException {
		try {
			Candidate candidate = candidateDaoImpl.getByTestId(testId);
			if (candidate == null || StringUtils.isEmpty(candidate.getOpenId())) {
				WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
				String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID),
						configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
				return wxCommunicator.getQRCodeUrl(WXInterfaceUrl.getTempQRCodeRequestJson(testId), token);
			}
			return null;
		} catch (Exception e) {
			logger.error("call getQRPicUrl error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	
	/**
	 * 更新candidateTestlog  包括答题者ip，浏览器，操作系统等等
	 * 
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateCandidateTestLog(long testId,EmployerOperationLog log) throws PFServiceException{
		logger.debug("updateCandidateTestLog testId {}, log {} ", testId,log);
		PFResponse pfResponse = new PFResponse();
		try {
			candidateTestDao.updateCandidateTestLog(testId,log);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("call updateCandidateTestLog error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}
	
	/**
	 * 获取题目的评论汇总信息
	 * @param questionId
	 * @return
	 */
	public FeedbackCountInfo getFeedbackCountInfo(long questionId)  throws PFServiceException{
		logger.debug("getFeedbackCountInfo questionId {}", questionId);
		FeedbackCountInfo result = new FeedbackCountInfo();
		try {
			StatQuestion stat = statQuestionDao.getEntity(questionId);
			if(stat == null){
				result.setNegNum(0);
				result.setPraiseNum(0);
			}else{
				result.setNegNum(stat.getNegNum());
				result.setPraiseNum(stat.getPraiseNum());
			}
			result.setCommentNum(feedBackDaoImpl.getTotalCommentNum(questionId));
			
			List<ConfigCodeName> list = config.getConfigCode(ConfigCodeType.TEST_FEEDBACK_ITEM);
			List<Mapping> map = null;
			if (!CollectionUtils.isEmpty(list)) {
				map = new ArrayList<Mapping>();
				for (ConfigCodeName configCodeName : list) {
					Mapping mapping = new Mapping();
					mapping.setKey(configCodeName.getId().getCodeId());
					mapping.setValue(configCodeName.getCodeName());
					map.add(mapping);
				}
			}
			result.setFeedItems(map);
			
		} catch (Exception e) {
			logger.error("call getFeedbackCountInfo error ", e);
			throw new PFServiceException(e);
		}
		return result;
	}
	
	/**
	 * 获取评论信息
	 * @param questionId
	 * @param page
	 * @return
	 */
	public List<CandidateTestFeedback> getCandidateTestFeedbacks(long questionId, Page page){
		logger.debug("getFeedbackCountInfo questionId {}, page {} ", questionId,page);
        return feedBackDaoImpl.getCandidateTestFeedbacks(questionId, page);
	}

	@Override
	public InvitationOnLine getInvitationOnLineByPassport(String passport) {
		logger.debug("getInvitationOnLineByPassport by passport {} ", passport);
		InvitationOnLine res = new InvitationOnLine();
		OnlineExamReq examReq = onlineExamReqDao.getOnlineExamReqByPassport(passport);
		if(examReq == null){
			res.setCode("PASSPORTERROR");
			return res;
		}
		Invitation invitation = invitationDao.getEntity(examReq.getTestId());
		if(invitation == null){
			logger.debug("not found any invitation for testId {}, passport {}  ", examReq.getTestId(),passport );
			res.setCode("PASSPORTERROR");
			return res;
		}
		if(invitation.getExpDate().before(new Timestamp(System.currentTimeMillis()))){
			res.setCode("TIMEOUT");
			return res;
		}
		CandidateTest candidateTest = candidateTestDao.getEntity(examReq.getTestId());
		if (candidateTest.getTestState() > 1) {
			res.setCode(FuncBaseResponse.FINISHEXAM);
			return res;
		}
		
		res.setCode(FuncBaseResponse.SUCCESS);
		res.setInvitationUrl(invitation.getInvitationUrl());
		return res;
	}
}
