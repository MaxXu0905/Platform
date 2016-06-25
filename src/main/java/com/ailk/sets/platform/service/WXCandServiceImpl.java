package com.ailk.sets.platform.service;

import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.impl.CandidateInfoFromAppDao;
import com.ailk.sets.platform.dao.impl.PositionDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.invatition.OnlineExamReqResult;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;
import com.ailk.sets.platform.util.DateUtils;
import com.ailk.sets.platform.util.EmailUtils;

@Transactional(rollbackFor = Exception.class)
public class WXCandServiceImpl implements IWXCandService {
	private Logger logger = LoggerFactory.getLogger(WXCandServiceImpl.class);
	@Autowired
	private ICandidateDao candidateDaoImpl;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private CandidateInfoFromAppDao candidateInfoFromAppDao;
	@Autowired
	private PositionDaoImpl positionDao;
	@Autowired
	private IInvite invite;
	@Override
	public Candidate bindingCandidate(long testId, String openId) throws PFServiceException {
		try {
			logger.debug("binding candidate ,testId is " + testId + ",openId is " + openId);
			Candidate candidate = candidateDaoImpl.getByTestId(testId);
			if (candidate != null) {
				logger.debug("candidate id is " + candidate.getCandidateId());
				candidate.setOpenId(openId);
				candidateDaoImpl.saveOrUpdate(candidate);
				candidate.setCode(FuncBaseResponse.SUCCESS);
			} else {
				candidate = new Candidate();
				candidate.setCode(FuncBaseResponse.FAILED);
				candidate.setMessage("can not find candidate from candidateTest");
			}
			return candidate;
		} catch (Exception e) {
			logger.error("call bindingCandidate error", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	/**
	 *  用户手动绑定事件 
	 *
	 */
	@Override
	public Candidate bindingHandCandidate(String userName, String email, String openId) throws PFServiceException {
		try {
			logger.debug("binding candidate ,username is " + userName + ",openId is " + openId+",email is "+email);
			if(StringUtils.isBlank(userName) || StringUtils.isBlank(email) || StringUtils.isBlank(openId))
	        {
	            throw new PFServiceException("用户名 或 邮箱  或openId为空");
	        }
			Candidate candidate = new Candidate();
			Candidate isBind = candidateDaoImpl.getCandidate(userName, email, openId);
			if (isBind != null) {
				candidate.setCode(FuncBaseResponse.SUCCESS);
				
				
			} else {
				candidate.setCode(FuncBaseResponse.FAILED);
				candidate.setMessage("can not find candidate from candidateTest");
			}
			return isBind;
		} catch (Exception e) {
			logger.error("call bindingCandidate error", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	
	@Override
	public PFResponse unBindingCandidate(String openId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			int removedNum = candidateDaoImpl.removeOpenId(openId);
			logger.debug("unbinding candidate openId is " + openId + " and removeNum is " + removedNum);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public void sendWelcome(String openId) {
		try {
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEWELCOME);
			logger.debug("应聘者直接关注后的提示（未绑定）"+text);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send welcome text", e);
		}
	}
    /**
     * 扫描二维码关注回复
     * */
	@Override
	public void sendScanwelcome(String openId,String candidateName) {
		try {
			
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEWELCOMESCAN);
			text = MessageFormat.format(text,candidateName);
			logger.debug("应聘者扫描后发送的消息"+text);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send welcome text", e);
		}
		
	}
	/**
	 * 手动绑定失败后通知
	 *
	 */
	@Override
	public void sendFailedMessage(String openId) {
		try {
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLREPORT);
			logger.debug("手动绑定格式不正确时回复信息："+text);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send failedMessage text", e);
		}
		
	}
	
	/**
	 * 手动绑定成功后回复
	 * 
	 * */
	@Override
	public void sendSuccessMessage(String openId, Candidate candidate) {
           try {
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLSUCCESSREPORT);
			text = MessageFormat.format(text,candidate.getCandidateName(),candidate.getCandidateEmail());
			logger.debug("手动绑定成功后回复"+text);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send successMessage text", e);
		}		
	}
	
	/**
	 * 手动绑定格式不正确
	 * */
	
	@Override
	public String bindFailed(String openId) {
        try {
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLREPORT);
			return text;
		} catch (PFDaoException e) {
			logger.error("error send successMessage text", e);
			return null;
		}
	}
	

    /**
     * 申请在线考试失败
     *
     */
    @Override
    public void onlineReqFailed(String openId , String faildMsg) {
        try {
            if (StringUtils.isBlank(faildMsg))
            {
                faildMsg = configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_PARAM_ERROR);
            }
            logger.debug("申请考试失败回复信息："+faildMsg);
            WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
            String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
            wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, faildMsg), token);
        } catch (PFDaoException e) {
            logger.error("error send failedMessage text", e);
        }
        
    }
    
    
    /**
     * 申请在线考试成功
     *
     */
    public void onlineReqSuccess(String openId , String content) {
        try {
            logger.debug("申请考试成功回复信息："+content);
            WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
            String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
            wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, content), token);
        } catch (PFDaoException e) {
            logger.error("error send failedMessage text", e);
        }
        
    }
    
    @Override
    public void onlineReqSave(String openId, String passport, String username, String email)
    {
        String errorContent = "在线考试申请失败";
        try
        {
            errorContent = configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_FAILD);
            
            // 校验口令
            if(StringUtils.isBlank(passport))
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_PASSPORT_EMPTY));
                return;
            }
            Position position = positionDao.getPositionObjByPassport(passport);
            if (null == position)
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_NO_PAPER));
                return;
            }
            // 暂未开启申请/不在申请时间内
            if(null==position.getOnlineReqEndDate() || DateUtils.getCurrentTimestamp().compareTo(position.getOnlineReqEndDate())>0)
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_TIME_OUT));
                return;
            }
            
            // 姓名非空校验
            if (StringUtils.isBlank(username))
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_NAME_EMPTY));
                return;
            }
            // 姓名非空校验
            if (StringUtils.isBlank(email))
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_EMAIL_EMPTY));
                return;
            }
            
            // 稍微检验一下邮箱
            Pattern pattern = Pattern
                    .compile("^[\\w\\-][\\w\\-\\.]*@[a-zA-Z0-9]+([a-zA-Z0-9\\-\\.]*[a-zA-Z0-9\\-]+)*\\.[a-zA-Z0-9]{2,}$");
            if(!pattern.matcher(email).matches())
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_EMAIL_NOT_RIGHT));
                return;
            }
            if(!EmailUtils.lookupDomain(email))
            {
                onlineReqFailed(openId,configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_EMAIL_NOT_EXIST));
                return;
            }
            Invitation invitation = new Invitation();
            invitation.setCandidateEmail(email);
            invitation.setCandidateName(username);
            invitation.setOpenId(openId);
            invitation.setPositionId(position.getPositionId());
       
            OnlineExamReqResult result = invite.onlineExamReqInvite(invitation);
            if (result.getStatus()==OnlineExamReqResult.STATUS_SUCCESS)
            {
                
                // 考试申请成功！在电脑浏览器中打开：{1}/itest/。并在页面中输入通行证：{2}开始考试。
                String content = configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_SUCCESS);
                content = MessageFormat.format(content,
                        configSysParamDao.getConfigParamValue(ConfigSysParam.BASE_URL_CAND_PORTAL),
                        result.getPassport());
                onlineReqSuccess(openId , content);
            }else
            {
                // 您已申请过该考试！如还未参加考试，请在电脑浏览器中打开：{1}/itest/。并在页面中输入通行证：{2}开始考试。
                String content = configSysParamDao.getConfigParamValue(ConfigSysParam.ONLINE_EXAM_REQ_REPEAT);
                content = MessageFormat.format(content,
                        configSysParamDao.getConfigParamValue(ConfigSysParam.BASE_URL_CAND_PORTAL),
                        result.getPassport());
                onlineReqSuccess(openId , content);
            }
            logger.debug("在线申测处理成功");
        } catch (Exception e)
        {
            onlineReqFailed(openId, errorContent);
            logger.error("在线申测异常",e);
            e.printStackTrace();
        }
    }
}
