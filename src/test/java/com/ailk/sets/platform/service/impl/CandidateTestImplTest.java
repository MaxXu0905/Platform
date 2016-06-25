package com.ailk.sets.platform.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperData;
import com.ailk.sets.platform.intf.cand.service.ICandidateTest;
import com.ailk.sets.platform.intf.cand.service.ISchoolExamService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;

public class CandidateTestImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	}
	
	@Test
	public void getCandidateTestFeedbacks() {
		ICandidateTest examService = (ICandidateTest)context.getBean("candidateTest");
		Page page = new Page();
		page.setPageSize(10);
		page.setRequestPage(1);
		try {
			Object o = examService.getCandidateTestFeedbacks(107040009000159l, page);
			System.out.println(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFeedbackCountInfo() {
		ICandidateTest examService = (ICandidateTest)context.getBean("candidateTest");
		try {
			Object o = examService.getFeedbackCountInfo(107040009000159l);
			System.out.println(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getInvitationOnLineByPassport(){
		ICandidateTest examService = (ICandidateTest)context.getBean("candidateTest");
		try {
			Object o = examService.getInvitationOnLineByPassport("aaaa");
			System.out.println(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
