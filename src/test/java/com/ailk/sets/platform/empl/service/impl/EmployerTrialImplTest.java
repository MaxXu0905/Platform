package com.ailk.sets.platform.empl.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.EmployerCompanyInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApplyActivite;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class EmployerTrialImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	}
	@Test
	public void  registEmployerTrial() throws PFServiceException{
		
		IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
		EmployerTrialApply apply = new EmployerTrialApply();
		apply.setUserEmail("panyltest222@asiainfo.com");
		apply.setUserName("潘永雷");
		apply.setAcctRole(2);
		PFResponse r = employerTrial.registEmployerTrial(apply);
		System.out.println(r);
	}
	
	@Test
	public void  activeEmployerTrail() throws PFServiceException{
		
		IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
		EmployerTrialApply apply = new EmployerTrialApply();
//		apply.setCompany("亚信");
		apply.setUserEmail("2469600569@qq.com");
		apply.setUserName("潘永雷");
		PFResponse r = employerTrial.activeEmployerTrail("E08A97EE2044B18CED2AF6ECC12DE7F5", "123","hello");
		System.out.println(r);
	}
	@Test
	public void getEmployerCompanyInfo() {
		IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
		EmployerCompanyInfo info = employerTrial.getEmployerCompanyInfo(100000060);
		System.out.println(info);
	}
	@Test
	public void saveEmployerCompanyInfo(){
		IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
		EmployerCompanyInfo info = new EmployerCompanyInfo();
		info.setCompanyName("nihao亚信");
		PFResponse pf = employerTrial.saveEmployerCompanyInfo(100000060,info);
		System.out.println(pf);
	}
	
	@Test
	public void sendActiveEmail(){
	    IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
	    
	    EmployerTrialApplyActivite apply = new EmployerTrialApplyActivite(); 
	    apply.setUserName("李攀");
	    apply.setUserEmail("lipan3@asiainfo.com");
	    apply.setUrl("www.baidu.com");
	    try
        {
            employerTrial.sendActiveEmail(apply);
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
	    
	}
	
	@Test
	public void supportEmail(){
	    IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
	    
	    try
        {
          PFResponse res =   employerTrial.supportEmail("panyl@asiainfo-linkage.com");
          System.out.println(res);
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
	    
	}
	@Test
	public void inviteEmployerJoin() {
		EmployerAuthorizationIntf employerAuth = new EmployerAuthorizationIntf();
		employerAuth.setEmployerId(1);
		employerAuth.setEmailGranted("panyl@asiainfo.com");
		 IEmployerTrial employerTrial = (IEmployerTrial)context.getBean("employerTrial");
		    
		    try
	        {
	          PFResponse res =   employerTrial.inviteEmployerJoin(employerAuth);
	          System.out.println(res);
	        } catch (PFServiceException e)
	        {
	            e.printStackTrace();
	        }
	}
}
