package com.ailk.sets.platform.intf.model.wx;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.model.wx.model.QRTicket;
import com.ailk.sets.platform.intf.model.wx.model.Token;
import com.ailk.sets.platform.intf.model.wx.model.WXAuthInfo;
import com.ailk.sets.platform.intf.model.wx.model.WXBaseInfo;
import com.ailk.sets.platform.util.StreamUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午9:27:24
 * @desc : 微信请求工具类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class WXCommunicator
{
    private static Logger logger = LoggerFactory.getLogger(WXCommunicator.class);
    private static Map<String,TokenInfo> tokens = new HashMap<String, TokenInfo>();
    static class TokenInfo{
    	private Token token;
    	private long  putTime;
		public Token getToken() {
			return token;
		}
		public void setToken(Token token) {
			this.token = token;
		}
		public long getPutTime() {
			return putTime;
		}
		public void setPutTime(long putTime) {
			this.putTime = putTime;
		}
    	
    }
    private HttpClient webClient;
    
    public WXCommunicator(HttpClient webClient)
    {
        this.webClient = webClient;
    }

    /**
     * 获取授权token (每次重新获取)
     * 
     * @param appid
     * @param secret
     * @return
     */
    public String getAccessToken(String appid, String secret)
    {
        String response = null;
        logger.info("getAccessToken start.{appid=" + appid + ",secret:" + secret + "}");
        String url = null;
        try
        {
        	TokenInfo oldInfo = tokens.get(appid + "#" + secret);
        	if(oldInfo != null && (System.currentTimeMillis() - oldInfo.getPutTime() <= 1800 * 1000L)){//小于半小时不用重新获取
        		 logger.debug("return old  access token " + oldInfo.getToken().getAccessToken());
        		 return oldInfo.getToken().getAccessToken();
        	}
            url = MessageFormat.format(WXInterfaceUrl.TOKENURL, appid, secret);
            response = webClient.execute(new HttpGet(url), new BasicResponseHandler());
            Token t = new ObjectMapper().readValue(response, Token.class);
            logger.debug("get new access token " + t.getAccessToken());
            String token =  t.getAccessToken();
            TokenInfo info = new TokenInfo();
            info.setPutTime(System.currentTimeMillis());
            info.setToken(t);
            tokens.put(appid + "#" + secret, info);
            return token;
        } catch (Exception e)
        {
            logger.error("get access toekn exception , response=" + response + ", url  =" + url, e);
        }
        return null;
    }

    /**
     * 获取二维码图片
     * 
     * @param requestJson
     * @return
     * @throws Exception
     */
    public String getQRCodeUrl(String requestJson, String accessToken) throws Exception
    {
        logger.debug("send qrcodeurl requestJson is " + requestJson + "---------token is "
                + accessToken);
        StringEntity entity = new StringEntity(requestJson);
        entity.setContentEncoding(Constants.CHARSET_UTF8);
        entity.setContentType("application/json");
        HttpPost hp = new HttpPost(MessageFormat.format(WXInterfaceUrl.QRCODE, accessToken));
        hp.setEntity(entity);
        HttpResponse response = webClient.execute(hp);
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value())
        {
            InputStream is = response.getEntity().getContent();
            String content = StreamUtils.inputStream2String(is, Constants.CHARSET_UTF8);
            logger.debug("get qrcode first step response is " + content);
            if (content.indexOf("errcode") != -1)
            {
                logger.error("error get qrcode , response is " + content);
                return null;
            }
            QRTicket qrTicket = new ObjectMapper().readValue(content, QRTicket.class);
            return MessageFormat.format(WXInterfaceUrl.QRCODEPIC, qrTicket.getTicket());
        } else
            logger.error("error get qrcode ticket");
        return null;
    }

    /**
     * 推送信息
     * 
     * @param token
     * @param msg
     * @return
     */
    public String sendMessage(String requestJson, String token)
    {
        try
        {
            logger.debug("send message requestJson is " + requestJson + "---------token is "
                    + token);
            StringEntity entity = new StringEntity(requestJson, Constants.CHARSET_UTF8);
            entity.setContentEncoding(Constants.CHARSET_UTF8);
            entity.setContentType("application/json");
            HttpPost hp = new HttpPost(MessageFormat.format(WXInterfaceUrl.SENDMSGURL, token));
            hp.setEntity(entity);
            HttpResponse response = webClient.execute(hp);
            HttpEntity httpEntity = response.getEntity();
            logger.debug("send message return "
                    + StreamUtils.inputStream2String(httpEntity.getContent(),
                            Constants.CHARSET_UTF8));
            return "";
        } catch (Exception e)
        {
            logger.error("send message exception", e);
            return null;
        }
    }

    /**
     * 使用OAuth2.0授权后的得到的code获得授权信息(用户openId token等...)
     * 
     * @param code
     * @return
     */
    public WXAuthInfo getAuthInfoByCode(WXBaseInfo info , String code)
	{
	    try
        {
            HttpPost post = new HttpPost(MessageFormat.format(WXInterfaceUrl.AUTH_INFO,
                    info.getAppId(),
                    info.getAppSecret(),
                    code));
            String response = webClient.execute(post, new BasicResponseHandler());
            return new ObjectMapper().readValue(response, WXAuthInfo.class);
        } catch (Exception e)
        {
            logger.error(" get auth info by code error", e);
        }
	    return null;
	}

    /**
     * 拉取用户信息
     * 
     * @param token
     * @param openid
     * @return
     */
    // public WeixinOpenUser getUserInfo(String token, String openid) {
    // try {
    // log.info("getUserInfo start.{token:" + token + ",openid:" + openid +
    // "}");
    // String url = MessageFormat.format(this.getUserInfoUrl, token, openid);
    // String response = executeHttpGet(url);
    // JsonNode json = JsonUtils.read(response);
    // if (json.get("openid") != null) {
    // WeixinOpenUser user = new WeixinOpenUser();
    // user.setOpenUserId(json.get("openid").asText());
    // user.setState(json.get("subscribe").asText());
    // if ("1".equals(user.getState())) {
    // user.setUserName(json.get("nickname").asText());
    // user.setSex(json.get("sex").asText());
    // user.setCity(json.get("city").asText());
    // user.setLanguage(json.get("language").asText());
    // }
    // return user;
    // }
    // } catch (Exception e) {
    // log.error("get user info exception", e);
    // }
    // return null;
    // }

    public static void main(String[] args)
    {
        WXCommunicator wp = new WXCommunicator(HttpClientUtil.getHttpClient());
        String json = "{\"touser\":\"obivdt_1_XclG2XIydo7DQlDpna0\", \"msgtype\":\"text\", \"text\": {\"content\":\"李泡泡，您好！\n您在微信校招3参加的测评得分为0分。\n\n其中：\n0.0分null分\n此信息是考试完成后由百一测评（www.101test.com）自动发送，不代表公司的意见。\n请等待公司的通知，祝您应聘成功！\"} }";
        String token = "8LRDe9S0UJwAG1Ublhgc0AFX6L2F-bb0l4nWg3PG1PUfYfvGFDMJ0YvYLhOY4NRyMI3kJKmZxsAY6cMG0-4FUg";
        wp.sendMessage(json, token);
    }
}
