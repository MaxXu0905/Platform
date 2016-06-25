package com.ailk.sets.grade.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.Test;

import com.ailk.sets.grade.service.intf.IJmxCacheService;

public class JmxCacheClient {
	
	@Test
	public void execute() {
		try {
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://10.1.249.33:9001/jmxrmi");

			Map<String, Object> env = new HashMap<String, Object>();
			String[] credentials = { "controlRole", "9432jfdsaPq!" };
			env.put(JMXConnector.CREDENTIALS, credentials);

			JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			ObjectName mbeanName = new ObjectName("bean:name=jmxCacheService");
			IJmxCacheService jmxCacheService = JMX.newMBeanProxy(mbsc,
					mbeanName, IJmxCacheService.class);

			Method[] methods = IJmxCacheService.class.getMethods();
			for (Method method : methods) {
				if (!method.getName().startsWith("evict"))
					continue;

				method.invoke(jmxCacheService);
				System.out.println(method.getName() + " is called.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
