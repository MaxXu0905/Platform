package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.empl.domain.PaperModel;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.candidateReport.PositionGroupReport;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

public class ReportImplTest {
	static ApplicationContext context;
	static IReport report;
	static{
		 PropertyConfigurator.configure(ReportImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
		 report = (IReport)context.getBean("report");
	}
	@Test
    public void testgetPaperModel() throws Exception{
    	IReport report = (IReport)context.getBean("report");
    	long time1 = System.currentTimeMillis();
    	PaperModel m = report.getPaperModel(908);
    	long time2 = System.currentTimeMillis();
    	System.out.println(time2- time1);
    	System.out.println(m);
    }
	
	@Test
	public void testgetReport() throws Exception
	{
		GetReportParam param = new GetReportParam(1681 ,"0" , new Page(20, 1) ,new Interval(
				null, null));
//		param.setInputKey("钻石版");
//		param.setCommitPaperFromDate("2014-08-13");
//		param.setCommitPaperToDate("2014-08-13");
//		param.setPositionIntent("");
		param.setEmployerId(100001260);
		IReport report = (IReport)context.getBean("report");
		List<CandReportAndInfo> list = report.getReport(param);
		System.out.println(list.size());
	}
	
	
	@Test
	public void testgetReportInMem() throws Exception
	{
		GetReportParam param = new GetReportParam(1681 ,"0" , new Page(20, 1) ,new Interval(
				"0", "100"));
//		param.setInputKey("钻石版");
//		param.setCommitPaperFromDate("2014-08-13");
//		param.setCommitPaperToDate("2014-08-13");
//		param.setPositionIntent("");
		param.setEmployerId(100001260);
//		param.setOrderByPositionId(1440);
		param.setFilterScorePositionId(1679);
		IReport report = (IReport)context.getBean("report");
		List<PositionGroupReport> list = report.getReportInMem(param);
		System.out.println(list.size());
	}
	@Test
	public void setTestResult()
	{
	    try
        {
            PFResponse rsp = report.setTestResult(100001184, 1249, 125601 , 2);
            System.out.println(rsp.getMessage());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
	}
	
	@Test
	public void getEmployerStatus(){
		 PFResponseData<Integer> rsp = report.getEmployerStatus(2245,"r62JcjKntbCdby4miUx28zAE6hE96n");
         System.out.println(rsp.getMessage());
	}
	

	@Test
	public void ownReport() throws Exception{
		PFResponse rsp= report.ownReport(100001184,121913);
         System.out.println(rsp);
	}
}
