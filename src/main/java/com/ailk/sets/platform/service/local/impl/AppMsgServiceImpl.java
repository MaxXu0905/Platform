package com.ailk.sets.platform.service.local.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.IAppStatusMonitorDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoFromAppDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.domain.AppStatusMonitor;
import com.ailk.sets.platform.domain.CandidateInfoFromApp;
import com.ailk.sets.platform.domain.PassEmailEntity;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IConfig;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;
import com.ailk.sets.platform.service.local.IAppMsgService;
import com.ailk.sets.platform.util.DateUtils;
@Transactional(rollbackFor = Exception.class)
@Service
public class AppMsgServiceImpl  implements IAppMsgService{
	@Autowired
	private ICandidateInfoFromAppDao candidateInfoFromAppDao;
	@Autowired
	private IInvite inviteImpl;
	@Autowired
	private IPositionDao positionDao;
	
    @Autowired
    private IConfigSysParamDao configSysParamDao;

    @Autowired
    private IAppStatusMonitorDao appStatusMonitorDao; 
    @Autowired
    private IInvitationDao invitationDao;
    @Autowired
    private IConfig config;
    private Logger logger = LoggerFactory.getLogger(AppMsgServiceImpl.class);
    
	@Override
	public List<CandidateInfoFromApp> getNeedSendInvitationCandidates() {
		return candidateInfoFromAppDao.getNeedSendInvitationCandidates();
	}

	@Override
	public InviteResult inviteByCandidateFromApp(CandidateInfoFromApp candidateInfoApp) throws PFServiceException {
	    Position position =	positionDao.getPositionObjByPassport(candidateInfoApp.getId().getPasscode());
	    Invitation invitation = new Invitation();
	    if(position == null){
	    	logger.debug("passcode error {} ", candidateInfoApp.getId().getPasscode());
	    	InviteResult res = new InviteResult();
	    	res.setTestId(-3);
	    	return res;
	    }
	    if(position.getOnlineReqEndDate() != null && (System.currentTimeMillis() > position.getOnlineReqEndDate().getTime())){//
	    	logger.debug("the time is offer than {} ", position.getOnlineReqEndDate());
	    	InviteResult res = new InviteResult();
	    	res.setTestId(-2);
	    	return res;
	    }
        invitation.setCandidateEmail(candidateInfoApp.getId().getCandidateEmail());
        invitation.setCandidateName(candidateInfoApp.getId().getCandidateName());
        invitation.setEmployerId(position.getEmployerId());
        invitation.setPositionId(position.getPositionId());
        if(position.getExamEndDate() != null)//邀请结束时间
            invitation.setValidDate(DateUtils.getDateStr(position.getExamEndDate(), DateUtils.DATE_FORMAT_3));
//        invitation.setBeginDate("2014-07-28 11:25");
//        invitation.setSelfContext("sdfds注意的范德萨发第三方的撒范德萨");
//        invitation.setCanWithOutCamera(1);
//        invitation.setTestPositionName("校招测评1");
        return  inviteImpl.invite(invitation);
	}

	@Override
	public void updateCandidateInfoFromApp(CandidateInfoFromApp candidateInfoApp) {
		candidateInfoFromAppDao.saveOrUpdate(candidateInfoApp);
		
	}

	@Override
	public void updateAppStatusMonitor(AppStatusMonitor monitor) {
		appStatusMonitorDao.saveOrUpdate(monitor);
	}
	@Override
	public long getMaxSleepTimeForInvite() throws PFDaoException {
		   String time =  configSysParamDao.getConfigParamValue("MAX_SLEEP_FOR_INVITE");
		   return Long.valueOf(time);
	}

	@Override
	public AppStatusMonitor getAppStatusMonitor() {
		return appStatusMonitorDao.getEntity(Constants.APP_ID_MSG);
	}
	
	@Override
	public void sendWXNotifyMsg(int lastNotifyStatus,int nowStatus) throws Exception {
		WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
//		Employer employer = employerDaoImpl.getEntity(employerId);
		List<ConfigCodeName> openIds = config.getConfigCode(ConfigCodeType.MSG_NOTIFY_OPEN_ID);
		if(!CollectionUtils.isEmpty(openIds)){
			for(ConfigCodeName config : openIds)
			{
				String openId = config.getCodeName();
				if (StringUtils.isNotEmpty(openId)) {
					String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
					String text = configSysParamDao.getConfigParamValue(ConfigSysParam.APP_STATUS_CHANGED);
					String status = "";
					switch (nowStatus) {
					case Constants.APP_STATUS_DEAD:
						status = "没有网络信号";
						break;
					case Constants.APP_STATUS_NO_SIGNAL:
						status = "有网络信号没有手机信号";
						break;
					case Constants.APP_STATUS_NORMAL:
						status = "正常";
						break;
					default:
						break;
					};
					text = MessageFormat.format(text, status);
					logger.debug("tell openId {} status change from {} to {} , token {} ", new Object[]{openId, lastNotifyStatus,nowStatus , token});
					wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
				}
			}
			
		}
		
	}

	@Override
	public void sendWXInvitationFailedMsg(CandidateInfoFromApp candidateInfo, long testId) throws Exception {

		Invitation invitation = invitationDao.getEntity(testId);
		if(testId < 0  || (invitation != null && invitation.getInvitationState() == 0)){//邀请失败
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			List<ConfigCodeName> openIds = config.getConfigCode(ConfigCodeType.MSG_NOTIFY_OPEN_ID);
			if(!CollectionUtils.isEmpty(openIds)){
				for(ConfigCodeName config : openIds)
				{
					String openId = config.getCodeName();
					if (StringUtils.isNotEmpty(openId)) {
						String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
						String text = configSysParamDao.getConfigParamValue(ConfigSysParam.APP_INVITATION_FAILED);
						String error = "未知原因";
						if(testId < 0 ){
							if(testId == -1){
								error = "系统异常";
							}else if(testId == -2){
								error ="申请时间已经终止";
							}else if(testId == -3){
								error = "口令错误";
							}
						}else{
							error = invitation.getInvitationErrtxt();
						}
						text = MessageFormat.format(text,candidateInfo.getId().getCandidateName(), candidateInfo.getId().getCandidateEmail(), error);
						logger.debug("tell openId {} invitationFaild for candidateEmail {} , candidateName {} , token {} ", new Object[]{openId, candidateInfo.getId().getCandidateEmail(), candidateInfo.getId().getCandidateName() , token});
						wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
					}
				}
				
			}
		}
		
		
	}

	@Override
	public void sendWXInvitationMsgForCan(CandidateInfoFromApp candidateInfo, long testId) throws Exception {
		if(StringUtils.isEmpty(candidateInfo.getOpenId()) || testId < 0){
			logger.debug("the openId is null or testId < 0 , openId {}, testId {} ", candidateInfo.getOpenId(), testId);
			return;
		}
		WXCommunicator wxCommunicator = new WXCommunicator(
				HttpClientUtil.getHttpClient());
		Invitation invitation = invitationDao.getEntity(testId);
		String token = wxCommunicator.getAccessToken(
				configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID),
				configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
		/*String text = configSysParamDao
					.getConfigParamValue(ConfigSysParam.EMPLOYERPASS);*/
		if (invitation.getInvitationState() == 1) {
			String text = "已发送测评邀请邮件到您的邮箱，请查看收件箱或者垃圾邮件箱。\n如果没有收到，请发邮件告知service@101test.com。";
			logger.debug("pass text is " + text);
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(candidateInfo.getOpenId(), text), token);
		}
		
	}

}
