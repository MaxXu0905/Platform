package com.ailk.sets.grade.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.ailk.sets.grade.service.intf.IJmxCacheService;

public class EvictCacheMain {

	private static final String USERNAME = "controlRole";
	private static final String PASSWORD = "9432jfdsaPq!";
	private static final String HOSTNAME = "localhost";
	private static final String PORT = "9001";
	private static final String INTERFACE = IJmxCacheService.class.getName();
	private static final String SERVICE_NAME = "jmxCacheService";

	public static void main(String[] args) {
		String username = USERNAME;
		String password = PASSWORD;
		String hostname = HOSTNAME;
		String port = PORT;
		String intf = INTERFACE;
		String methodName = null;
		String serviceName = SERVICE_NAME;

		for (int i = 0; i < args.length; i += 2) {
			String key = args[i];
			String value = args[i + 1];

			if (key.equals("-username")) {
				username = value;
			} else if (key.equals("-password")) {
				password = value;
			} else if (key.equals("-hostname")) {
				hostname = value;
			} else if (key.equals("-port")) {
				port = value;
			} else if (key.equals("-intf")) {
				intf = value;
			} else if (key.equals("-method")) {
				methodName = value;
			} else if (key.equals("-service")) {
				serviceName = value;
			}
		}

		try {
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://" + hostname + ":" + port
							+ "/jmxrmi");

			Map<String, Object> env = new HashMap<String, Object>();
			String[] credentials = { username, password };
			env.put(JMXConnector.CREDENTIALS, credentials);

			JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			ObjectName mbeanName = new ObjectName("bean:name=" + serviceName);

			Class<?> intfClass = Class.forName(intf);
			Object jmxCacheService = JMX.newMBeanProxy(mbsc, mbeanName,
					intfClass);

			Method[] methods = jmxCacheService.getClass().getMethods();
			for (Method method : methods) {
				if (methodName == null) {
					if (!method.getName().startsWith("evict"))
						continue;
				} else {
					if (!method.getName().equals(methodName))
						continue;
				}

				method.invoke(jmxCacheService);
				System.out.println(method.getName() + " is called.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
