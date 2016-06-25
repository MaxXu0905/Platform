package com.ailk.sets.platform.empl.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.service.ISSOLogin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/localbean.xml","/spring/beans.xml" })
public class SSOLoginImplTest {
	@Autowired
	private ISSOLogin ssoLogin;
	@Test
	public void ssoLogin(){
		EmployerRegistInfo pf = ssoLogin.ssoLogin("26cd823023f70bcf8f30078f72ee7e3f", 1);
	    System.out.println(pf);
	}

}
