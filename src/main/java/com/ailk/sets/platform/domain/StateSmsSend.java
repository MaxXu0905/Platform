package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * StateSmsSend entity. @author MyEclipse Persistence Tools
 */

public class StateSmsSend implements java.io.Serializable {
	public static final int APP_TYPE = 40;
	private static final long serialVersionUID = 3737768220360898805L;
	private Long id;
	private String sender;
	private String receiver;
	private Integer type;
	private String content;
	private Integer state;
	private Integer errorCode;
	private String errorDetail;
	private Integer retries;
	private Timestamp lastUpdate;

	// Constructors

	/** default constructor */
	public StateSmsSend() {
	}

	/** full constructor */
	public StateSmsSend(String sender, String receiver, Integer type, String content, Integer state, Integer errorCode,
			String errorDetail, Integer retries, Timestamp lastUpdate) {
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.content = content;
		this.state = state;
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
		this.retries = retries;
		this.lastUpdate = lastUpdate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetail() {
		return this.errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public Integer getRetries() {
		return this.retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}