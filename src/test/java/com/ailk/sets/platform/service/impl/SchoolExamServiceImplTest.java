package com.ailk.sets.platform.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.intf.cand.domain.SchoolExamCondition;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperData;
import com.ailk.sets.platform.intf.cand.service.ISchoolExamService;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class SchoolExamServiceImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	}
	
	@Test
	public void testGeneratePaper() {
		ISchoolExamService examService = (ISchoolExamService)context.getBean("schoolExamService");
		SchoolExamCondition condition = new SchoolExamCondition();
		condition.setActivityId(221);
		condition.setWeixinId("asdfsadf_sdfsadf@qq.com");
		try {
			Object o = examService.generatePaper(condition);
			System.out.println(o);
		} catch (PFServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
    public void testGetPaperByInvitationId(){
    	ISchoolExamService examService = (ISchoolExamService)context.getBean("schoolExamService");
    	try {
			SchoolPaperData data = examService.getPaperByInvitationId(121522);
			System.out.println(data);
		} catch (PFServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Test
	public void getPositionExamInfos(){
		ISchoolExamService examService = (ISchoolExamService)context.getBean("schoolExamService");
    	try {
    		String candidateName = "张经国";
    		String candidateEmail = "zhangjg@asiainfo.com";
			Object data = examService.getPositionExamInfos(candidateEmail,candidateName,"fLLzaQUJBees35J");//1441的entry
			System.out.println(data);
		} catch (PFServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
