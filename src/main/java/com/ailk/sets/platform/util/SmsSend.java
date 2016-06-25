package com.ailk.sets.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.empl.domain.SmsSendResult;
import com.google.gson.Gson;

public class SmsSend {

	private static final String URL_ACCESS_TOKEN = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";
	private static final String URL_SEND_SMS = "http://api.189.cn/v2/emp/templateSms/sendSms";
	private static final int TIMEOUT = 60000;
	private static final String APP_ID = "869836290000038252";
	private static final String APP_SECRET = "feab309a7005f335b15576d81312b658";
	private static final String TEMPLATE_ID = "91003076";

	private static final Gson gson = new Gson();
	private static PoolingHttpClientConnectionManager connManager;
	private static CloseableHttpClient httpClient;
	private static RequestConfig requestConfig;

	private static SimpleDateFormat format;
	private static String accessToken;
	private static long expire;
	
	private static Logger logger = LoggerFactory.getLogger(SmsSend.class);
	
	static {
		connManager = new PoolingHttpClientConnectionManager();
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		connManager.setDefaultSocketConfig(socketConfig);

		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setMaxTotal(200);
		connManager.setDefaultMaxPerRoute(20);

		httpClient = HttpClients.custom().setConnectionManager(connManager).build();

		requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT).build();

		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("begin send sms ");
		SmsSend.sendSms("18611270892", "移动", "http://fir.im/jvso", "");
		System.out.println("end send sms");
	}

	public static SendSmsResponse sendSms(String phoneNo,String signals,String downUrl){
		return sendSms(phoneNo, signals, downUrl,null);
	}
	public static SendSmsResponse sendSms(String phoneNo,String signals,String iOSUrl,String androidUrl) {
		SendSmsResponse result = new SendSmsResponse();
		if (accessToken == null || System.currentTimeMillis() >= expire) {
			logger.debug("get new token for current {}, expire {} ",System.currentTimeMillis(),expire);
			if (!getAccessToken()){
				result.res_code = -99;
			    return result;
			}
		}
		
		SmsSendParams params = new SmsSendParams();
		params.param1 = signals;
		params.param2 = iOSUrl;
		if(!StringUtils.isEmpty(androidUrl))
		  params.param3 = androidUrl;
		String templateParam = gson.toJson(params);

		String timestamp = format.format(new Date());
		HttpPost httpPost = new HttpPost(URL_SEND_SMS);

		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("app_id", APP_ID));
			nvps.add(new BasicNameValuePair("access_token", accessToken));
			nvps.add(new BasicNameValuePair("acceptor_tel", phoneNo));
			nvps.add(new BasicNameValuePair("template_id", TEMPLATE_ID));
			nvps.add(new BasicNameValuePair("template_param", templateParam));
			nvps.add(new BasicNameValuePair("timestamp", timestamp));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpPost);

			try {
				HttpEntity entity = response.getEntity();
				if (entity == null){
					result.res_code = -98;
					result.res_message = "not connection";
					return result;
				}
					

				byte[] bytes = new byte[1024];
				StringBuffer sb = new StringBuffer();
				int len;

				InputStream is = entity.getContent();
				while ((len = is.read(bytes)) != -1)
					sb.append(new String(bytes, 0, len));

				logger.debug("send sms result {} ", sb.toString());
				
				result = gson.fromJson(sb.toString(), SendSmsResponse.class);
				if (result == null || result.res_code != 0)
					return result;

				return result;
			} catch(Exception e ){
				logger.error("error send sems ",e);
				
			}finally {
				if (response != null)
					response.close();
			}
		} catch(Exception e ){
			logger.error("error send sems",e);
			
		}finally {
			httpPost.releaseConnection();
		}

		return result;
	}

	public static SmsSendResult  sendSmsByPlatform(String phoneNo,String signals,String iOSUrl,String androidUrl){
		      SmsSendResult result = new SmsSendResult();
		      SendSmsResponse res = null;
		      if(StringUtils.isEmpty(androidUrl)){
		    	  res = sendSms(phoneNo, signals, iOSUrl);
		      }else{
		    	  res = sendSms(phoneNo, signals, iOSUrl, androidUrl);
		      }
              if(res.res_code == 0){
            	  result.setCode(FuncBaseResponse.SUCCESS);
              }else{
            	  result.setCode(FuncBaseResponse.FAILED);
            	  result.setMessage(res.res_message);
              }
              result.setErrorCode(res.res_code);
              result.setContext(res.idertifier);
              return result;
	}
	private static synchronized boolean getAccessToken() {
		HttpPost httpPost = new HttpPost(URL_ACCESS_TOKEN);

		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
			nvps.add(new BasicNameValuePair("app_id", APP_ID));
			nvps.add(new BasicNameValuePair("app_secret", APP_SECRET));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpPost);

			try {
				HttpEntity entity = response.getEntity();
				if (entity == null){
					 logger.debug("get entity is null  , response {} ", response);
					return false;
				}
					

				byte[] bytes = new byte[1024];
				StringBuffer sb = new StringBuffer();
				int len;

				InputStream is = entity.getContent();
				while ((len = is.read(bytes)) != -1)
					sb.append(new String(bytes, 0, len));
                logger.debug("get token result {} ", sb.toString());
				AccessTokenResponse result = gson.fromJson(sb.toString(), AccessTokenResponse.class);
				if (result == null || result.res_code != 0)
					return false;

				accessToken = result.access_token;
				expire = System.currentTimeMillis() + (result.expires_in - TIMEOUT) * 1000;
				return true;
			} catch (Exception e){
				logger.error("error get token ",e);
			}
			finally {
				if (response != null)
					response.close();
			}
		}  catch (Exception e) {
			logger.error("error get token ",e);
		} finally {
			httpPost.releaseConnection();
		}

		return false;
	}

	private static class SmsSendParams {
		String param1;
		String param2;
		String param3;
	}

	private static class AccessTokenResponse {
		int res_code;
		String access_token;
		long expires_in;
	}

	private static class SendSmsResponse {
		int res_code;
		@SuppressWarnings("unused")
		String idertifier;
		String res_message;
	}

}
