package com.ailk.sets.platform.service.impl;

import java.util.Collection;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.service.IInfoCollect;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class InfoCollectTest {
    static ApplicationContext context;
    static IInfoCollect infoCollect ;
    static{
        PropertyConfigurator.configure(InviteServiceTest.class.getResource("/log4j.properties"));
        context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
        infoCollect = (IInfoCollect)context.getBean("infoCollect");
    }
    
	@Test
	public void testInfoCollect() throws PFServiceException
	{
        Collection<ConfigInfoExtEx> list = infoCollect.getInfoExt(100000065,null);
        for(ConfigInfoExtEx c : list)
        {
        	System.out.println(c.getInfoId());
        	System.out.println(c.getChoosed());
        }
	}
}
