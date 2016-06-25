package com.ailk.sets.grade.baidu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.jdbc.IpInfo;

@Service
@Transactional
public class FullIpLocationMain {

	private static final String AKS[] = { "esGGStzu71wM1CGU0peIrvWc",
			"jdpKshSNAEDhCq1FbtOumF5P", "UAEwRifa5uWevwWBWKe7OYil",
			"OdPZEeidPPZ5TzxCUtOA6l56", "2Drs593dlqz8ThgtT8hP6pVB",
			"0QHV1Gw71qNSyqauawAI76I8", "6wjcgXCuTsPRX8VzRaEtKCfV",
			"zUofvyDhjt1BUANc30Z7a5fG", "HHENay3FEcFWcq5QyiOeghrT",
			"Cd6gPqmvE9dkNWR93NjVG3Mw", "H1vYAYr8ccaVPXDddjYlqTu3",
			"BEhKFiL5jM6FEGKvnBSRES5j", "5hfzcK5u2DmCHAy6rpqI2RXU",
			"ehqt1poWWeUsneGglPYc5lS4", "UrjZMQkCpPN822SYQ79B0GIY",
			"BQGrpdPUckXy3GBopzNRsWix", "HbupscXECalRE8qCt1SpjGxj",
			"ZmE8KrrUemqmGwlIzLzz3n0m", "QBOHQl0mnHgDa3IG7I01W953",
			"skMOoD0j6vTnGGqeGqqUGNQM" };

	public static void main(String[] args) {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/beans.xml", "/spring/localbean.xml" });
		context.start();

		final ParseIpLocation instance = context.getBean(ParseIpLocation.class);
		final ConcurrentLinkedQueue<IpInfo> resultQueue = new ConcurrentLinkedQueue<IpInfo>();
		final ConcurrentLinkedQueue<String> requestQueue = new ConcurrentLinkedQueue<String>();

		for (int i = 0; i < 20; i++) {
			final String ak = AKS[i];

			new Thread() {
				@Override
				public void run() {
					while (true) {
						String ip = requestQueue.poll();
						if (ip == null) {
							synchronized (requestQueue) {
								while (true) {
									ip = requestQueue.poll();
									if (ip != null)
										break;

									try {
										requestQueue.wait();
									} catch (InterruptedException e) {
										continue;
									}
								}
							}
						}

						IpInfo ipInfo = ParseIpLocation.getIpLocation(ak, ip);
						if (ipInfo != null)
							resultQueue.add(ipInfo);
					}
				}
			}.start();
		}

		new Thread() {
			@Override
			public void run() {
				int rows = 0;

				while (true) {
					List<IpInfo> ipInfos = new ArrayList<IpInfo>();
					while (true) {
						IpInfo ipInfo = resultQueue.poll();
						if (ipInfo == null)
							break;

						ipInfos.add(ipInfo);
						if (ipInfos.size() == 100) {
							instance.save(ipInfos);
							rows += ipInfos.size();
							System.out.println(System.currentTimeMillis()
									+ ", rows=" + rows);
							ipInfos.clear();
						}
					}

					if (!ipInfos.isEmpty()) {
						instance.save(ipInfos);
						rows += ipInfos.size();
						System.out.println(System.currentTimeMillis()
								+ ", rows=" + rows);
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();

		for (int seg = 0; seg < 20; seg++) {
			int begin = seg * 13;
			int end = begin + 13;
			if (end > 256)
				end = 256;

			for (int i = begin; i < end; i++) {
				for (int j = 0; j < 256; j++) {
					for (int k = 0; k < 256; k++) {
						String ip = i + "." + j + "." + k + ".1";
						if (instance.exist(ip))
							continue;

						synchronized (requestQueue) {
							boolean empty = requestQueue.isEmpty();
							requestQueue.add(ip);
							if (empty)
								requestQueue.notifyAll();
						}
					}

					while (requestQueue.size() > 10000) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}

					System.out.println(i + "." + j);
				}
			}
		}

		while (!requestQueue.isEmpty() || !resultQueue.isEmpty()) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		System.exit(0);
	}
}
