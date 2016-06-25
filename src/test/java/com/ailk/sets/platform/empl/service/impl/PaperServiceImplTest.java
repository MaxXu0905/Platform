package com.ailk.sets.platform.empl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.service.local.IPaperService;

public class PaperServiceImplTest {
	
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/beans.xml" });
	}
	@Test
	public void  getQbQuestionViews() throws PFServiceException{
		
		IPaperService paperService = (IPaperService)context.getBean("paperServiceImpl");
		List<Integer> skills = new ArrayList<Integer>();
		skills.add(2);
//		qbQuestionService.getQbQuestionViews(1, skills);
		
		paperService.createPaperInstance(2,1,"1");
	}
	
	@Test
	public void test(){
		int plLow = 8;
		int plHigh = 9;
		double ratio = 0.9;
		  double degree = plLow + (plHigh - plLow) * ratio;
		  int degreeHigh = (int)Math.floor(degree);
		  int degreeLow = (int)Math.ceil(degree);
		  System.out.println(degreeHigh);
		  System.out.println(degreeLow);
	}
	@Test
	public void createPaperInstance() throws PFServiceException{
		IPaperService paperService = (IPaperService)context.getBean("paperServiceImpl");
		long time1 = System.currentTimeMillis();
		paperService.createPaperInstance(1127,2026,"MaTTSHtWoG");
		long time2 = System.currentTimeMillis();
		System.out.println("waste ======= " + (time2 -time1));
	    time1 = System.currentTimeMillis();
//		paperService.createPaperInstance(1130,37,"aa3plyC9Ce");
//		 time2 = System.currentTimeMillis();
//		System.out.println("waste ======= " + (time2 -time1));
	}
	
	
}
