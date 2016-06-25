package com.ailk.sets.platform.intf.model.wx.resp;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ailk.sets.platform.intf.model.wx.util.CDataAdapter;

public class BaseMessage {
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String ToUserName; // 开发者微信号
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String FromUserName; // 发送方帐号（一个OpenID）
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String CreateTime; // 消息创建时间 （整型
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String MsgType; // 消息类型（text/image/location/link）

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
