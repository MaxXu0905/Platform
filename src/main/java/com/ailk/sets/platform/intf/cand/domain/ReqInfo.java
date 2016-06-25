package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

/**
 * 请求信息
 * @author panyl
 * 
 */
public class ReqInfo implements Serializable{
	private static final long serialVersionUID = 987026968513132072L;
	private Long beginTime;
	private Long usedTime;

	private String sessionId;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}
	public Long getUsedTime() {
		return usedTime;
	}
	
}
