package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class PositionDaoTest {
	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml", "/spring/consumer.xml", "/spring/beans.xml" });
	}

	@Test
	public void test() throws PFDaoException, PFServiceException {
		((AbstractApplicationContext) ConfigInfoExtTest.context).start();
		IPositionDao configInfoExtDao = (IPositionDao) ConfigInfoExtTest.context.getBean("positionDaoImpl");
//		List<PositionLog> list = configInfoExtDao.getPositionLog(2, new Page());
//		System.out.println(list.size());
	}
}
