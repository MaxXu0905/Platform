package com.ailk.sets.platform.intf.model.wx.model;

import java.io.Serializable;

public class QRTicket implements Serializable {

	private static final long serialVersionUID = 7525842254525349251L;
	private String ticket;
	private int expire_seconds;
	private String url;//微信获取二维码会返回一个url，否则json转换会token出错
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(int expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
}
