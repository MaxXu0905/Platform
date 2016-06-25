package com.ailk.sets.platform.empl.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
public class PaperPartProcessForEssayTest {
	@Autowired
	private PaperPartProcessForEssay paper;
	
	@Test
	public void processPaperForEssay() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml", });
	    paper.processPaperForEssay(1605);
	    // create table paper_part_0905 as select * from paper_part;
	    // create table papar_question_0905 as select * from paper_question; 
//		System.in.read();
	}
	
	
}
