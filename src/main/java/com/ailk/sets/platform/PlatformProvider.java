package com.ailk.sets.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.grade.grade.common.TraceManager;

public class PlatformProvider {

	private static Logger logger = LoggerFactory
			.getLogger(PlatformProvider.class);

	public static void main(String[] args) {
		try {
			logger.debug("begin start platform ... ");
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "/spring/beans.xml", "/spring/provider.xml" });
			context.start();
			logger.debug("end start platform ... ");
			Thread.sleep(Long.MAX_VALUE);
		} catch (Throwable e) {
			logger.error(TraceManager.getTrace(e));
			System.exit(1);
		}
	}

}