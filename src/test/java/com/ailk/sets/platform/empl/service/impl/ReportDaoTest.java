package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

public class ReportDaoTest {
    static ApplicationContext context;
    static{
         PropertyConfigurator.configure(ReportDaoTest.class.getResource("/log4j.properties"));
         context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/beans.xml" });
    }

	@Test
	public void getReport() throws PFDaoException, PFServiceException {
		IReport report = (IReport) context.getBean("report");
		Page page = new Page();
		page.setPageSize(5);
		page.setRequestPage(2);
		
		Interval interval = new Interval();
		interval.setMin("0");
		interval.setMax("100");
		
		GetReportParam param = new GetReportParam();
		param.setEmployerId(100000069);
		param.setPositionId(1127);
		param.setPage(page);
		param.setTestResult("0");
		param.setPassport("MaTTSHtWoG");
		param.setScore(interval);
		List<CandReportAndInfo> list = report.getReport(param);
		System.out.println(list.size());
	}
}
