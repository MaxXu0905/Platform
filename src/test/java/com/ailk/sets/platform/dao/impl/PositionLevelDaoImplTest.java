package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.intf.domain.PositionLevel;

public class PositionLevelDaoImplTest {

	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	}
  
	@Test
	public void getQbQuestionById() throws Exception{
		PositionLevelDaoImpl dao = (PositionLevelDaoImpl)context.getBean("positionLevelDaoImpl");
		PositionLevel q = dao.getPositionLevel(11, 1);
		Thread.sleep(1000);
		q = dao.getPositionLevel(11, 1);
		
	}
}
