package com.ailk.sets.platform.intf.model.wx.model;

import java.io.Serializable;

public class Token implements Serializable {

	private static final long serialVersionUID = -3025184578906334938L;

	private String accessToken;
	private Integer expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccess_token(String accesssToken) {
		this.accessToken = accesssToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpires_in(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

}
