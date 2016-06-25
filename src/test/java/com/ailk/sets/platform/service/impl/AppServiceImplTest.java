package com.ailk.sets.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.service.IAppService;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.MsgCandidateInfo;
import com.ailk.sets.platform.intf.domain.MsgInfo;

public class AppServiceImplTest {

	private static IAppService appService;
	{
	      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	      appService = (IAppService)context.getBean("appService");
	}
	
	@Test
	public void saveMsgCandidateInfo(){
		MsgCandidateInfo info = new MsgCandidateInfo();
		info.setFileName("文件名称");
		
		List<MsgInfo> msgs = new ArrayList<MsgInfo>();
		MsgInfo msg = new MsgInfo();
		msg.setPhone("13312341234");
		msg.setContext("xxx#潘永雷21#panyl@asiainfo.com");
		msgs.add(msg);
		
		msg = new MsgInfo();
		msg.setPhone("13312341234");
		msg.setContext("xxx+潘永雷22+panyl@asiainfo.com");
		msgs.add(msg);
		
		
		info.setMsgInfos(msgs);
		PFResponse res = appService.saveMsgCandidateInfo(info);
		System.out.println(res);
	}
	@Test
	public void udpateAppHeartBeat(){
		PFResponse res = appService.udpateAppHeartBeat(1);
		System.out.println(res);
	}
}
