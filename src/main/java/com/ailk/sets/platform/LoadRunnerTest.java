package com.ailk.sets.platform;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class LoadRunnerTest {
	private static Logger logger = LoggerFactory.getLogger(PlatformProvider.class);
	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(LoadRunnerTest.class.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml", "/spring/beans.xml" });
	}

	public static void main(String[] args) throws NumberFormatException, PFServiceException {
		int loop = Integer.parseInt(args[0]);
		IInvite invite = (IInvite) context.getBean("inviteImpl");
		for (int i = 0; i < loop; i++) {
			Invitation invitation = new Invitation();
			invitation.setCandidateName(args[3]);
			invitation.setCandidateEmail(i + args[4]);
//			invite.invite(Integer.parseInt(args[1]), Integer.parseInt(args[2]), invitation);
		}
		logger.info("make data over " + loop);
		System.exit(0);
	}
}
