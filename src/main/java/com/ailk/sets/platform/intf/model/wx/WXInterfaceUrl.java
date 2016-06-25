package com.ailk.sets.platform.intf.model.wx;

public class WXInterfaceUrl {

	/**
	 * 获取token接口
	 */
	public static final String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	/**
	 * 获取二维码ticket接口
	 */
	public static final String QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";
	/**
	 * 获取二维码图片接口
	 */
	public static final String QRCODEPIC = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket={0}";
	/**
	 * 用户信息接口
	 */
	public static final String USERINFOURL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";
	/**
	 * 推送信息接口
	 */
	public static final String SENDMSGURL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}";
	
	/**
	 * 使用授权后的code获取access_token、openId信息
	 */
	public static final String AUTH_INFO = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	
	/**
	 * 临时二维码请求json
	 * 
	 * @param sceneId
	 * @return
	 */
	public static String getTempQRCodeRequestJson(long sceneId) {
		return "{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId + "}}}";
	}

	/**
	 * 二维码请求json
	 * 
	 * @param sceneId
	 * @return
	 */
	public static String getQRCodeRequestJson(int sceneId) {
		return "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId + "}}}";
	}

	/**
	 * 获取发送消息文本
	 * 
	 * @param openId
	 * @param content
	 * @return
	 */
	public static String getSendText(String openId, String content) {
		return "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}";
	}
}
