package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QbDifficultyLevelDaoImplTest {

	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(QbDifficultyLevelDaoImplTest.class
				.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] {
				"/spring/provider.xml", "/spring/consumer.xml",
				"/spring/beans.xml" });
	}

	@Test
	public void getQbQuestionById() throws Exception {
		QbDifficultyLevelDaoImpl dao = (QbDifficultyLevelDaoImpl) context
				.getBean("qbDifficultyLevelDaoImpl");
		dao.getDifficultyLevel("EASY", 1);
		Thread.sleep(1000);
		dao.getDifficultyLevel("EASY", 1);
	}
}