package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * AppStatusMonitor entity. @author MyEclipse Persistence Tools
 */

public class AppStatusMonitor implements java.io.Serializable {

	private static final long serialVersionUID = 6053900568908099608L;
	private String appId;
	private Timestamp lastBeatTime;
	private Integer lastBeatStatus;
	private Integer lastNotifyStatus;

	// Constructors

	/** default constructor */
	public AppStatusMonitor() {
	}

	/** minimal constructor */
	public AppStatusMonitor(String appId) {
		this.appId = appId;
	}

	/** full constructor */
	public AppStatusMonitor(String appId, Timestamp lastBeatTime, Integer lastBeatStatus, Integer lastNotifyStatus) {
		this.appId = appId;
		this.lastBeatTime = lastBeatTime;
		this.lastBeatStatus = lastBeatStatus;
		this.lastNotifyStatus = lastNotifyStatus;
	}

	// Property accessors

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Timestamp getLastBeatTime() {
		return this.lastBeatTime;
	}

	public void setLastBeatTime(Timestamp lastBeatTime) {
		this.lastBeatTime = lastBeatTime;
	}

	public Integer getLastBeatStatus() {
		return this.lastBeatStatus;
	}

	public void setLastBeatStatus(Integer lastBeatStatus) {
		this.lastBeatStatus = lastBeatStatus;
	}

	public Integer getLastNotifyStatus() {
		return this.lastNotifyStatus;
	}

	public void setLastNotifyStatus(Integer lastNotifyStatus) {
		this.lastNotifyStatus = lastNotifyStatus;
	}

}