package com.ailk.sets.platform.intf.model.wx.model;

import java.sql.Timestamp;

public class TokenManager {
	private Token token;
	private Timestamp getTokenTime;

	public TokenManager(Token token) {
		this.token = token;
		this.getTokenTime = new Timestamp(System.currentTimeMillis());
	}

	public boolean isInvilid() {
		long now = System.currentTimeMillis();
		long interval = (now - getTokenTime.getTime());
		return (interval / 1000) > (token.getExpiresIn() / 2);
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Timestamp getGetTokenTime() {
		return getTokenTime;
	}

	public void setGetTokenTime(Timestamp getTokenTime) {
		this.getTokenTime = getTokenTime;
	}

}
