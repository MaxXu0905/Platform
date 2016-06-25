package com.ailk.sets.platform.domain;

import java.io.Serializable;

public class ForgetPassEntity implements Serializable {

	private static final long serialVersionUID = 1994081495615681828L;

	private String url;
	private String username;
	private String path = "forgetpassWord";

	public String getSubject() {
		return "百一测评密码找回邮件";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPath() {
		return path;
	}

}
