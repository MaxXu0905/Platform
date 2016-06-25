package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QbNoramlDaoImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(QbNoramlDaoImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	}
  
	@Test
	public void testGetSeq(){
	/*	QbNormalDaoImpl dao = (QbNormalDaoImpl)context.getBean("qbNoramlDaoImpl");
		System.out.println(dao.getNextSequence());*/
	}
	
	@Test
	public void testGetNormal()
	{/*
		QbNormalDaoImpl dao = (QbNormalDaoImpl)context.getBean("qbNoramlDaoImpl");

		String testSql = " select * from qb_question where is_sample = 1 and question_type  in('interview') ";
		List<QbQuestion> testQuestions = dao.getNoramlListBySql(testSql, QbQuestion.class);
		System.out.println("the size is {} for sql {} " + testQuestions.size() + testSql);
	
	*/}
	
	
	
	
}
