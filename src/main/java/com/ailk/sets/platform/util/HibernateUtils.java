package com.ailk.sets.platform.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * 数据库管理
 * 
 * @author 毕希研
 * 
 */
public class HibernateUtils {

	private static final Logger logger = Logger.getLogger(HibernateUtils.class);
	private static final SessionFactory sessionFactory;

	static {
		try {
			// 从hibernate.cfg.xml创建SessionFactory
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			logger.fatal(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}

}