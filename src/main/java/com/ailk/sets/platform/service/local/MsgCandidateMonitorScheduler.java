package com.ailk.sets.platform.service.local;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.platform.domain.CandidateInfoFromApp;
import com.ailk.sets.platform.domain.paper.CandidateTestPartId;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;

/**
 * 短息消息应聘者扫描器
 * 
 * @author panyl
 * 
 */
@Service
public class MsgCandidateMonitorScheduler {
	@Autowired
	private IAppMsgService appMsgService;
	private Logger logger = LoggerFactory.getLogger(MsgCandidateMonitorScheduler.class);

//	@Scheduled(fixedDelay = 1000 * 60, initialDelay = 1000 * 15)
	public void schedule() {
		try{
	    List<CandidateInfoFromApp> candidates = appMsgService.getNeedSendInvitationCandidates();
	    if(candidates.size() > 0){
	    	for(CandidateInfoFromApp app : candidates){
	    		try{
	    			InviteResult res = appMsgService.inviteByCandidateFromApp(app);
	    			app.setTestId(res.getTestId());
	    		}catch(Exception e){
	    			logger.error("error inviteByCandidateFromApp for CandidateInfoFromApp " + app, e);
	    			app.setTestId(-1L);
	    		}
	    		appMsgService.updateCandidateInfoFromApp(app);
	    		long maxTime = appMsgService.getMaxSleepTimeForInvite();
                appMsgService.sendWXInvitationFailedMsg(app, app.getTestId());
                appMsgService.sendWXInvitationMsgForCan(app, app.getTestId());
	    		Thread.sleep(maxTime);
	    	}
	    }
		}catch(Exception e){
			logger.error("error to scheduler MsgCandidateMonitorScheduler ", e);
		}
	}
	

}
