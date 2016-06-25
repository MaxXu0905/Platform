package com.ailk.sets.platform.cand.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.CandidateExamInfo;
import com.ailk.sets.platform.intf.cand.domain.ExamineTimeInfo;
import com.ailk.sets.platform.intf.cand.domain.PaperData;
import com.ailk.sets.platform.intf.cand.service.IExamineService;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.service.impl.SchoolPositionServiceImplTest;

public class ExamineServiceImplTest
{
    static ApplicationContext context;
    static
    {
        PropertyConfigurator.configure(SchoolPositionServiceImplTest.class
                .getResource("/log4j.properties"));
        context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml",
                "/spring/beans.xml" });
    }

    @Test
    public void testConsumer() throws Exception
    {
        IExamineService examineService = (IExamineService) context.getBean("examineService");
        ExamineTimeInfo hello = examineService.getExamineTimeLeft(14, 1); // do
                                                                          // invoke!
        System.out.println(hello.getTimeLeft()); // cool, how are you~
    }

    @Test
    public void testGetPaperData()
    {
        System.out.println(-1);
        System.out.println(000);
        System.out.println(1111);
        IExamineService examineService = (IExamineService) context.getBean("examineService");
        System.out.println(2222);
        PaperData hello = examineService.getPaperData(100000000002487l, "AA2xycOJro"); // do
                                                                           // invoke!
        System.out.println(hello.getPartDatas()); // cool, how are you~
    }
    
    @Test
    public void testGetTestPaperData()
    {
        System.out.println(-1);
        System.out.println(000);
        System.out.println(1111);
        IExamineService examineService = (IExamineService) context.getBean("examineService");
        System.out.println(2222);
        PaperData hello = examineService.getTestPaperData(1997, "VgrGz1TpMV"); // do
                                                                           // invoke!
        System.out.println(hello.getPartDatas()); // cool, how are you~
    }

    @Test
    public void testGetCandidateExamInfo() throws Exception
    {
        System.out.println(-1);
        System.out.println(000);
        System.out.println(1111);
        IExamineService examineService = (IExamineService) context.getBean("examineService");
        System.out.println(2222);
        CandidateExamInfo hello = examineService.getCandidateExamInfo(100000000006957L, 1); // do
                                                                               // invoke!
        System.out.println(hello);
    }

    @Test
    public void createPaper() throws Exception
    {
        IExamineService examineService = (IExamineService) context.getBean("examineService");
        examineService.createPaper(2079, "DiFfLmVvrE");

    }
    
    @Test
    public void gradeAndGetRanking() throws Exception{
    	  IExamineService examineService = (IExamineService) context.getBean("examineService");
    	  PFResponseData d =  examineService.gradeAndGetRanking(100000000007336L);
    	  System.out.println(d);
    }
}
