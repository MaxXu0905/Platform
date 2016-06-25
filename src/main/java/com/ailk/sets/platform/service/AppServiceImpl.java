package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IAppStatusMonitorDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoFromAppDao;
import com.ailk.sets.platform.domain.AppStatusMonitor;
import com.ailk.sets.platform.domain.CandidateInfoFromApp;
import com.ailk.sets.platform.domain.CandidateInfoFromAppId;
import com.ailk.sets.platform.intf.cand.service.IAppService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.MsgCandidateInfo;
import com.ailk.sets.platform.intf.domain.MsgInfo;
@Transactional(rollbackFor = Exception.class)
public class AppServiceImpl implements IAppService {
	@Autowired
	private ICandidateInfoFromAppDao candidateInfoFromAppDao;
	@Autowired
	private  IAppStatusMonitorDao appStatusMonitorDao;

	private Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
	@Override
	public PFResponse saveMsgCandidateInfo(MsgCandidateInfo candidateInfo) {
		PFResponse pf = new PFResponse();
		pf.setCode(FuncBaseResponse.SUCCESS);
		logger.debug("candidateInfoFromAppDao , candidateInfo is {}  ", candidateInfo);
        String fileName = candidateInfo.getFileName();
        List<MsgInfo> msgs = candidateInfo.getMsgInfos();
        for(MsgInfo msgInfo : msgs){
        	String[] infos = msgInfo.getContext().split("#");
        	if(infos.length != 3){
        		logger.error("the context is error for {}, please check ", msgInfo);
        		continue;
        	}
        	String passcode = infos[0].trim();
        	String candidateName = infos[1].trim();
        	String candidateEmail = infos[2].trim();
        	CandidateInfoFromAppId id = new CandidateInfoFromAppId(passcode, candidateName, candidateEmail);
        	
        	CandidateInfoFromApp oldAppInfo = candidateInfoFromAppDao.getEntity(id);
        	if(oldAppInfo != null){
        		logger.warn("the candidateInfo is alread exsits for msg {} ", msgInfo);
        	}else{
        		oldAppInfo = new CandidateInfoFromApp(id);;
        	}
        	oldAppInfo.setCandidatePhone(msgInfo.getPhone());
        	oldAppInfo.setBatchName(fileName);
        	oldAppInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
        	candidateInfoFromAppDao.saveOrUpdate(oldAppInfo);
        }
		return pf;
	}

	@Override
	public PFResponse udpateAppHeartBeat(int status) {
		if(status != Constants.APP_STATUS_NORMAL && status != Constants.APP_STATUS_NO_SIGNAL){
			logger.error("the status is not right ,please check {}", status);
			throw new RuntimeException("the status is not right ,please check , status is " + status);
		}
		PFResponse pf = new PFResponse();
		pf.setCode(FuncBaseResponse.SUCCESS);
		String appId = Constants.APP_ID_MSG;
		AppStatusMonitor monitor = appStatusMonitorDao.getEntity(appId);
		if(monitor == null){
			monitor = new AppStatusMonitor(appId);
		}
		monitor.setLastBeatStatus(status);
		monitor.setLastBeatTime(new Timestamp(System.currentTimeMillis()));
		appStatusMonitorDao.saveOrUpdate(monitor);
		return pf;
	}

}
