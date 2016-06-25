package com.ailk.sets.platform.service.local;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.platform.domain.paper.CandidateTestPartId;

/**
 * 考试服务调度器
 * 
 * @author panyl
 * 
 */
@Service
public class ExamineServiceScheduler {
	@Autowired
	private IPaperHanderService paperHanderService;
	private Logger logger = LoggerFactory.getLogger(ExamineServiceScheduler.class);

	@Scheduled(fixedDelay = 1000 * 120, initialDelay = 1000 * 10)
	public void schedule() {
		try{
			//校招按部分计时
	    List<CandidateTestPartId> ids = paperHanderService.getNeededHandInCandidateTestPart();
	    if(ids.size() > 0){
	    	for(CandidateTestPartId id : ids){
	    		paperHanderService.processCandidateTestPart(id);
	    	}
	    }
	    List<Long> testIds = paperHanderService.getDataOutTest();
	    if(testIds.size() > 0){
	    	for(long testId : testIds)
	    	paperHanderService.processDateOutTest(testId);
	    }
		}catch(Exception e){
			logger.error("error to scheduler ", e);
		}
	}
	

}
