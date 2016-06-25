package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class ReqInfoKey implements Serializable{
	private static final long serialVersionUID = -4439046407425105389L;
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
	public ReqInfoKey(){
		
	}
	public ReqInfoKey(String sessionId, String url) {
		this.sessionId = sessionId;
		this.url = url;
	}
	@Override
	public String toString() {
		return "ReqInfoKey [sessionId=" + sessionId + ", url=" + url + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReqInfoKey other = (ReqInfoKey) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
