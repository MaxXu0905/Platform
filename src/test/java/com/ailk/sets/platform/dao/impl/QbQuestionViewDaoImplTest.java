package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;

public class QbQuestionViewDaoImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	}
  
	@Test
	public void getQbQuestionById() throws Exception{
		QbQuestionViewDaoImpl dao = (QbQuestionViewDaoImpl)context.getBean("qbQuestionViewDaoImpl");
		List<Long> question = dao.getPaperQuestionsByDegreeAndSkill(2, "114", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "120", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "100", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "1000102", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "10004", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "125", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "124", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "123", PaperPartSeqEnum.EXTRA);
		question = dao.getPaperQuestionsByDegreeAndSkill(3, "114", PaperPartSeqEnum.EXTRA);
		
		System.out.println(question);
		
	}
	@Test
	public void getMotiQuestionsByDegreeAndSkill() throws Exception{
		QbQuestionViewDaoImpl dao = (QbQuestionViewDaoImpl)context.getBean("qbQuestionViewDaoImpl");
		List<Long> question = dao.getMotiQuestionsByDegreeAndSkill(8, "001", PaperPartSeqEnum.OBJECT);
		Thread.sleep(1000);
		question = dao.getMotiQuestionsByDegreeAndSkill(8, "001", PaperPartSeqEnum.OBJECT);
		question =  dao.getMotiQuestionsByDegreeAndSkill(1, "aaa", PaperPartSeqEnum.OBJECT);
		question = dao.getMotiQuestionsByDegreeAndSkill(1, "aaa", PaperPartSeqEnum.SUBJECT);
		question = dao.getPaperQuestionsByDegreeAndSkill(2, "aaa", PaperPartSeqEnum.OBJECT);
		Thread.sleep(1000);
		question =  dao.getMotiQuestionsByDegreeAndSkill(1, "aaa", PaperPartSeqEnum.SUBJECT);
		question =   dao.getMotiQuestionsByDegreeAndSkill(1, "aaa", PaperPartSeqEnum.OBJECT);
		
	}
}
