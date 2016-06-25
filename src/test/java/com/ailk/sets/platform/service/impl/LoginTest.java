package com.ailk.sets.platform.service.impl;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.empl.service.ILogin;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.employer.LoginInfo;

public class LoginTest {

	@Test
	public void getSkillLabels() throws Exception {
		System.out.println("++++++++++++++++++++++++");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/beans.xml","/spring/consumer.xml"});
        context.start();
        final ILogin login = (ILogin)context.getBean("loginServ");
        Employer empl = login.getEmployerByEmployerId(100001029);
        LoginInfo li = login.certify("panyl@asiainfo.com", "202CB962AC59075B964B07152D234B70");
        for(int i = 0; i< 5; i++){
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					 try {
						login.updateOpenId(100001029, "dfdfddfff3" + Math.random());
					} catch (PFServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
        }
       

        System.out.println(li);
	}
	
//	@Test
	public void register()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
        context.start();
        Employer employer = new Employer();
        employer.setCompanyId(1);
        employer.setCreateDate(new Date());
        employer.setEmployerName("毕希研");
        employer.setEmployerNumber("20268");
        ILogin login = (ILogin)context.getBean("loginServ");
//        RegisterInfo info = login.register(employer);
	}
}
