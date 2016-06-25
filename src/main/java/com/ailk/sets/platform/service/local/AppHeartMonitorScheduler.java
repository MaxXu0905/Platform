package com.ailk.sets.platform.service.local;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.platform.domain.AppStatusMonitor;
import com.ailk.sets.platform.intf.common.Constants;

/**
 * msg APP心跳监控器扫描器
 * 
 * @author panyl
 * 
 */
@Service
public class AppHeartMonitorScheduler {
	@Autowired
	private IAppMsgService appMsgService;
	private Logger logger = LoggerFactory.getLogger(AppHeartMonitorScheduler.class);

//	@Scheduled(fixedDelay = 1000 * 60, initialDelay = 1000 * 120)
	public void schedule() {
		try {

			AppStatusMonitor appStatusMonitor = appMsgService.getAppStatusMonitor();
			if (appStatusMonitor != null) {
				Timestamp lastBeatTime = appStatusMonitor.getLastBeatTime();
				long now = System.currentTimeMillis();
				int lastBeatStatus = appStatusMonitor.getLastBeatStatus();
				if((now - lastBeatTime.getTime()) >  120 * 1000L){
					if(lastBeatStatus != Constants.APP_STATUS_DEAD){//没有心跳了
						appStatusMonitor.setLastBeatStatus(Constants.APP_STATUS_DEAD);
						lastBeatStatus = Constants.APP_STATUS_DEAD;
						appMsgService.updateAppStatusMonitor(appStatusMonitor);
					}
				}
				int lastNotifyStatus = appStatusMonitor.getLastNotifyStatus();
				if(lastNotifyStatus != lastBeatStatus){
					appStatusMonitor.setLastNotifyStatus(lastBeatStatus);
					appStatusMonitor.setLastBeatStatus(lastBeatStatus);
					appMsgService.sendWXNotifyMsg(lastNotifyStatus, lastBeatStatus);
					appMsgService.updateAppStatusMonitor(appStatusMonitor);
				}
				
				
			}
		} catch (Exception e) {
			logger.error("error to scheduler AppHeartMonitorScheduler ", e);
		}
	}

}
