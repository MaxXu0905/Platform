package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.service.ISchoolReportService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.school.domain.SchoolReportCondition;
import com.ailk.sets.platform.intf.school.domain.SchoolReportInfo;

public class SchoolReportServiceImplTest {
	static ApplicationContext context;
	static{
		 PropertyConfigurator.configure(SchoolReportServiceImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	}
	
	@Test
	public void getSchoolReportList() throws PFServiceException{
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		Page page = new Page(30, 1);
		SchoolReportCondition condition = new SchoolReportCondition();
		condition.setPage(page);
//		condition.setPositionId(934);
		condition.setEmployerId(100000072);
		condition.setSearchTxt("");
		condition.setActivityId(360);
//		condition.setTestResult(2);
		List list = service.getSchoolReportList(condition);
		System.out.println(list.size());
	}
	@Test
	public void getReportSkillsScoreInfo(){
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		service.getReportSkillsScoreInfo(1347);
	}
	
	@Test
	public void getCountReportList(){
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		SchoolReportCondition condition = new SchoolReportCondition();
//		condition.setActivityId(54);
		condition.setPositionId(1127);
		condition.setEmployerId(100000048);
//		condition.setTestResult(2);
//		condition.setIntentPos("1");
		long size = service.getCountReportList(condition);
		System.out.println("=================" + size);
	}
	@Test
	public void getSchoolReportInfo() throws Exception{
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		SchoolReportInfo size = service.getSchoolReportInfo(1813);
		System.out.println("=================" + size);
	}
	@Test
	public void getSchoolReportStatusCount(){
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		List list = service.getSchoolReportStatusCount(100000048,1127,1);
		System.out.println("=================" + list);
	}
	
	@Test
	public void getSchoolReportListInMem() throws PFServiceException{
		ISchoolReportService service = (ISchoolReportService)context.getBean("schoolReportService");
		Page page = new Page(30, 1);
		SchoolReportCondition condition = new SchoolReportCondition();
		condition.setPage(page);
//		condition.setSearchTxt("李");
		condition.setActivityId(54);
//		condition.setPositionId(1161);
		condition.setTestResult(0);
		long time1 = System.currentTimeMillis();
		List list = service.getSchoolReportListInMem(condition);
		long time2 = System.currentTimeMillis();
		System.out.println(list.size() + "======" + (time2 - time1));
		list = service.getSchoolReportList(condition);
		long time3 = System.currentTimeMillis();
		System.out.println(list.size() + "======" + (time3 - time2));
	}
	
}
