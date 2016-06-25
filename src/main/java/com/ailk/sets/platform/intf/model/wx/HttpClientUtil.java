package com.ailk.sets.platform.intf.model.wx;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static HttpClient webClient;

	private HttpClientUtil() {
	}

	public static synchronized HttpClient getHttpClient() {
		if (webClient == null) {
			try {
				SSLContext sslContext = SSLContexts.custom().useTLS().build();
				SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslcsf).build();
				PoolingHttpClientConnectionManager hccm = new PoolingHttpClientConnectionManager(r);
				hccm.setMaxTotal(100);
				webClient = HttpClients.custom().setConnectionManager(hccm).build();
			} catch (Exception ex) {
				logger.error("HttpClientUtil exception", ex);
			}
		}
		return webClient;
	}
	
	public static synchronized HttpClient getNormalHttpClient() {
		HttpClient httpClient = new DefaultHttpClient();
		return httpClient;
	}
}
