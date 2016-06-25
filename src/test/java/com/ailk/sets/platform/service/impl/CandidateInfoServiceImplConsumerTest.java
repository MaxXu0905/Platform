package com.ailk.sets.platform.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.grade.intf.report.InterviewInfo.ConfigRegionInfo;
import com.ailk.sets.platform.intf.cand.service.ICandidateInfoService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.ConfigCollege;
import com.ailk.sets.platform.intf.model.Mapping;

public class CandidateInfoServiceImplConsumerTest {
	   @Test
	   public void getConfigRegionInfos() throws Exception{
		      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
			  context.start();
			  ICandidateInfoService service = (ICandidateInfoService)context.getBean("candidateInfo"); // get service invocation proxy
		      List<ConfigRegionInfo> list = service.getConfigRegionInfos();
		      System.out.println(list);
	   }
	   
	   @Test
	   public void testGetQueryInfoBySearchName() throws Exception{
		      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/beans.xml" });
			  context.start();
			  ICandidateInfoService service = (ICandidateInfoService)context.getBean("candidateInfo"); // get service invocation proxy
		      List<Mapping> list = service.getQueryInfoBySearchName(148,Constants.COLLEGE, "北京",10);
		      
		      System.out.println(list);
	   }
	   
	   
	   @Test
	   public void testGetConfigColleges() throws Exception{
		      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/beans.xml" });
			  context.start();
			  ICandidateInfoService service = (ICandidateInfoService)context.getBean("candidateInfo"); // get service invocation proxy
		      List<ConfigCollege> list = service.getConfigColleges("kj",10);
		      
		      System.out.println(list);
	   }
}
