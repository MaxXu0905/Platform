package com.ailk.sets.platform.service.impl;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.domain.SmsSendResult;
import com.ailk.sets.platform.intf.empl.service.ISystemService;

public class SystemServicelTest {

	private static ISystemService systemService;
	{
	      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	      systemService = (ISystemService)context.getBean("systemService");
	}
	
	@Test
	public void testSendSms() throws Exception {
		SmsSendResult res = systemService.sendSms(100000114, "186112708792");
		System.out.println(res);
	}
}
