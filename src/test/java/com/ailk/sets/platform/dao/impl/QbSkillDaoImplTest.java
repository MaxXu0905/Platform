package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;

public class QbSkillDaoImplTest {

	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(LabelAnalysisImplTest.class
				.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] {
				"/spring/provider.xml", "/spring/consumer.xml",
				"/spring/beans.xml" });
	}

	@Test
	public void getQbQuestionById() throws Exception {
		IQbSkillDao dao = (IQbSkillDao) context.getBean(IQbSkillDao.class);
		dao.getEntity("3100101");
		dao.getEntity("3100101");
		Thread.sleep(1000);
		dao.getEntity("3100101");
	}

}
