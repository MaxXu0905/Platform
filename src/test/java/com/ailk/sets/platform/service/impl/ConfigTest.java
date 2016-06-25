package com.ailk.sets.platform.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.service.IConfig;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class ConfigTest {
	@Test
	public void getConfig() throws PFServiceException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/consumer.xml" });
		context.start();
		IConfig config = (IConfig) context.getBean(IConfig.class);
		List<ConfigCodeName> list = config
				.getConfigCode(ConfigCodeType.POSITION_LEVEL);
		System.out.println(list);
	}
}
