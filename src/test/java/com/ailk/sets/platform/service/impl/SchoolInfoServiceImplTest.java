package com.ailk.sets.platform.service.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.SchoolInfoData;
import com.ailk.sets.platform.intf.cand.service.ISchoolInfoService;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class SchoolInfoServiceImplTest {
	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(SchoolInfoServiceImplTest.class
				.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] {
				"/spring/localbean.xml", "/spring/beans.xml" });
	}

	@Test
	public void getCompanyById() throws PFServiceException {

		ISchoolInfoService schoolInfoService = (ISchoolInfoService) context
				.getBean("schoolInfoService");
		Company c = schoolInfoService.getCompanyById(2);
		System.out.println(c.getCompanyName() + "================");

	}
	

	@Test
	public void getCompanyRecruitActivitysActive() throws PFServiceException {

		ISchoolInfoService schoolInfoService = (ISchoolInfoService) context
				.getBean("schoolInfoService");
		List c = schoolInfoService.getCompanyRecruitActivitysActive("5likC5lxk2dkGe4");
		System.out.println(c);

	}

	@Test
	public void getCompanyByPositionEntry() throws PFServiceException {

		ISchoolInfoService schoolInfoService = (ISchoolInfoService) context
				.getBean("schoolInfoService");
		SchoolInfoData info = schoolInfoService.getCompanyByPositionEntry("VMl7Jj0fv8fGyUg");
		System.out.println(info.getCompanyName() + "================");

	}

}
