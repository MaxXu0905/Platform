package com.ailk.sets.platform;

import java.sql.Timestamp;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.empl.service.impl.LabelAnalysisImplTest;
import com.ailk.sets.platform.service.local.impl.TestServiceImpl;

public class TestServiceImplTest{
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	} 
	@Test
   public void save1(){
	   try{
		      TestServiceImpl testService = (TestServiceImpl)context.getBean("testServiceImpl");
		      PositionLog log = new PositionLog();
		      log.setPositionId(-1);
		      log.setPositionState(-1);
		      log.setStateId(-1L);
		      log.setEmployerId(-1);
		      log.setLogTime(new Timestamp(System.currentTimeMillis()));
		      testService.save1(log);
	   }catch(Exception e){
		   System.out.println(1111 + e.toString());
		   System.out.println(222);
	   }
	 
      
    /*  PositionLog log2 = new PositionLog();
      log2.setPositionId(-2);
      log2.setPositionState(-1);
      log2.setStateId(-1);
      log2.setEmployerId(-1);
      log2.setLogTime(new Timestamp(System.currentTimeMillis()));
      testService.save1(log2);*/
      
      throw new RuntimeException();
   }
}
