package com.ailk.sets.platform.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;

public class CategoryTest {
	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml", "/spring/consumer.xml", "/spring/beans.xml" });
	}

/*	@Test
	public void test() throws PFDaoException, PFServiceException {
		((AbstractApplicationContext) CategoryTest.context).start();
		IPosition positionImpl = (IPosition) CategoryTest.context.getBean("positionImpl");
		Position pos = new Position();
//		pos.setProgramLanguage("java");
		pos.setLevel(2);
		positionImpl.getHistory(2, pos, "1", new Page());
	}*/
}
