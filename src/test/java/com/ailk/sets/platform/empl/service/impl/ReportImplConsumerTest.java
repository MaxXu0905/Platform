package com.ailk.sets.platform.empl.service.impl;


import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.service.IReport;

public class ReportImplConsumerTest {
	  /* @Test
	   public void testGetPaperInstancePartInfo(){
		      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
			  context.start();
		      IReport report = (IReport)context.getBean("report"); // get service invocation proxy
		      List<PaperInstancePartInfo> list = report.getPaperInstancePartInfo(97);
	          for(PaperInstancePartInfo i : list){
	        	  System.out.println(i);
	          }
	   }
	   
	   @Test
	   public void testGetPaperInstanceInfo(){
		      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
			  context.start();
		      IReport report = (IReport)context.getBean("report"); // get service invocation proxy
		      List<PaperInstanceInfo> list = report.getPaperInstanceInfo(97);
	          for(PaperInstanceInfo i : list){
	        	  System.out.println(i);
	          }
	   }
	   @Test
	     public void createCandReportStatDetail(){
		   ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
			  context.start();
		      IReport report = (IReport)context.getBean("report"); // get service invocation proxy
		      boolean result =  report.createCandReportStatDetail(97);
		      System.out.println(result);
	   }*/
	   @Test
	   public void testGetPaperInfo() {
		   ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
			  context.start();
		      IReport report = (IReport)context.getBean("report"); // get service invocation proxy
//		      PaperInfo paperInfo = report.getPaperInfo(145);
//		
//		      System.out.println(paperInfo);
	   }
	 }
