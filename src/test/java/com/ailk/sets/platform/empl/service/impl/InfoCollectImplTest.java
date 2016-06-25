package com.ailk.sets.platform.empl.service.impl;

import java.util.Collection;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.service.IInfoCollect;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class InfoCollectImplTest {
	static ApplicationContext context;
	 static IInfoCollect coll;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
		  coll = (IInfoCollect)context.getBean("infoCollect");
	}
	
	@Test
	public void  getInfoExt() throws PFServiceException{
		
		 Collection<ConfigInfoExtEx>   t= coll.getInfoExt(1,1310);
		 for(ConfigInfoExtEx ext : t)
		System.out.println("---------"+ext);
	}
	
	
	@Test
	public void  getQRCodePicUrl() throws PFServiceException{
		
		 String   t= coll.getQRCodePicUrl(100000069);
		System.out.println("---------"+t);
	}
	
}
