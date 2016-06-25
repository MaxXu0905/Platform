package com.ailk.sets.platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/localbean.xml","/spring/beans.xml" })
public class HttpClientTest {
	
	@Test
	public void doGet() throws Exception{
		 HttpClient client = HttpClientUtil.getNormalHttpClient();///{from}/positionModel/{positionId}/{tocken}
//       HttpPost httppost = new HttpPost("http://localhost/EmplPortal/sets/outpage/aimrjob/positionModel/1/26cd823023f70bcf8f30078f72ee7e3f");   
      
		 HttpGet httpGet = new HttpGet("http://218.107.56.4/UserPortal/BYToken?token=233d9b928280777e6b564f0db5c4972cfa15c93a756d80b97a7953d6ec844450be3de29081d297b8d9f7f026980e0851");   
		  HttpResponse response =  client.execute(httpGet);
		  HttpEntity entity = response.getEntity();   
	         System.out.println("----------------------------------------");   
	         System.out.println(response.getStatusLine());   
	        if (entity != null) {   
	           System.out.println("Response content length: " + entity.getContentLength());   
	         }   
	        // 显示结果   
	         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
	         String line = null;   
	         while ((line = reader.readLine()) != null) {   
	           System.out.println(line);   
	         }   
	         if (entity != null) {   
	             entity.consumeContent();   
	           }   
	}
	
    @Test
	public void doPostWithBody() throws Exception{

        HttpClient client = HttpClientUtil.getNormalHttpClient();///{from}/positionModel/{positionId}/{tocken}
//        HttpPost httppost = new HttpPost("http://localhost/EmplPortal/sets/outpage/aimrjob/positionModel/1/26cd823023f70bcf8f30078f72ee7e3f");   
       
      HttpPost httppost = new HttpPost("http://124.207.3.27/sets/outpage/aimrjob/positionModel/461/26cd823023f70bcf8f30078f72ee7e3f");   

        System.out.println("请求: " + httppost.getRequestLine());   
        
        StringEntity reqentity = new StringEntity("{\"status\":1,\"data\":{\"employerName\":\"张三\",\"employerEmail\":\"linc@163.com\"}}");
        reqentity.setContentEncoding(Constants.CHARSET_UTF8);
        reqentity.setContentType("application/json");
        httppost.setEntity(reqentity);
        HttpResponse response = client.execute(httppost);
        
        // 设置请求的数据   
         httppost.setEntity(reqentity);   
         HttpEntity entity = response.getEntity();   
         System.out.println("----------------------------------------");   
         System.out.println(response.getStatusLine());   
        if (entity != null) {   
           System.out.println("Response content length: " + entity.getContentLength());   
         }   
        // 显示结果   
         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
         String line = null;   
         while ((line = reader.readLine()) != null) {   
           System.out.println(line);   
         }   
         if (entity != null) {   
           entity.consumeContent();   
         }   
     
    }
    @Test
    public void doPostWithParam() throws Exception{
       HttpClient client = HttpClientUtil.getNormalHttpClient();
       HttpPost httppost = new HttpPost("http://localhost/EmplPortal/sets/outpage/getPositionList/26cd823023f70bcf8f30078f72ee7e3f/1");   
       System.out.println("请求: " + httppost.getRequestLine());   
       // 构造最简单的字符串数据   
        StringEntity reqEntity = new StringEntity("username=test&password=test");   //接收到这个既可以用getInputStream获得,也可以getParamter获得
       // 设置类型   
        reqEntity.setContentType("application/x-www-form-urlencoded");   
       // 设置请求的数据   
        httppost.setEntity(reqEntity);   
        HttpResponse response = client.execute(httppost);   
        HttpEntity entity = response.getEntity();   
        System.out.println("----------------------------------------");   
        System.out.println(response.getStatusLine());   
       if (entity != null) {   
          System.out.println("Response content length: " + entity.getContentLength());   
        }   
       // 显示结果   
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
        String line = null;   
        while ((line = reader.readLine()) != null) {   
          System.out.println(line);   
        }   
        if (entity != null) {   
          entity.consumeContent();   
        }   
    }
    
    @Test
    public void testCallSaveCandidateInfos() throws Exception{

        HttpClient client = HttpClientUtil.getNormalHttpClient();///{from}/positionModel/{positionId}/{tocken}
//        HttpPost httppost = new HttpPost("http://localhost/EmplPortal/sets/outpage/aimrjob/positionModel/1/26cd823023f70bcf8f30078f72ee7e3f");   
       
      HttpPost httppost = new HttpPost("http://124.207.3.27/sets/readOnly/saveMsgCandidateInfo");   

        System.out.println("请求: " + httppost.getRequestLine());   
        
        StringEntity reqentity = new StringEntity("{\"fileName\":\"20141015141414124\",\"msgInfos\":[{\"phone\":\"13312312315\",\"context\":\"烤鸭22+潘永雷+panyl@asiainfo.com\"},{\"phone\":\"13312312312\",\"context\":\"烤鸭+潘永雷+panyl@asiainfo.com\"},{\"phone\":\"13312312312\",\"context\":\"烤鸭+潘永雷222+panyl@asiainfo.com\"}]}");
        reqentity.setContentEncoding(Constants.CHARSET_UTF8);
        reqentity.setContentType("application/json");
        httppost.setEntity(reqentity);
        HttpResponse response = client.execute(httppost);
        
        // 设置请求的数据   
         httppost.setEntity(reqentity);   
         HttpEntity entity = response.getEntity();   
         System.out.println("----------------------------------------");   
         System.out.println(response.getStatusLine());   
        if (entity != null) {   
           System.out.println("Response content length: " + entity.getContentLength());   
         }   
        // 显示结果   
         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
         String line = null;   
         while ((line = reader.readLine()) != null) {   
           System.out.println(line);   
         }   
         if (entity != null) {   
           entity.consumeContent();   
         }   
     
    }
}
