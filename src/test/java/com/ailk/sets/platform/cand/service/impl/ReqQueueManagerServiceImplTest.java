package com.ailk.sets.platform.cand.service.impl;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.ReqInfoKey;
import com.ailk.sets.platform.intf.cand.service.IReqQueueManagerService;

public class ReqQueueManagerServiceImplTest {
	@Test
	 public void test() throws Exception{
		    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/cconsumer.xml","/spring/beans.xml" });
	        context.start();
	        IReqQueueManagerService reqQueueManagerService = (IReqQueueManagerService)context.getBean("reqQueueManagerServiceImpl"); // get service invocation proxy
	        Thread.sleep(3000l);
	        reqQueueManagerService.addToReqQueue(new ReqInfoKey("aaaa", "url"));
	        Thread.sleep(6000l);
	}
}
