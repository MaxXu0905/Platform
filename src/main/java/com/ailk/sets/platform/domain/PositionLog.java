package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * PositionLog entity. @author MyEclipse Persistence Tools
 */

public class PositionLog implements java.io.Serializable {

	private static final long serialVersionUID = -7222128265684623508L;
	private Integer logId;
	private Integer positionId;
	private Long stateId;
	private Integer positionState;
	private Integer employerId;
	private Timestamp logTime;

	// Constructors

	/** default constructor */
	public PositionLog() {
	}

	/** full constructor */
	public PositionLog(Integer positionId, Long stateId, Integer positionState, Integer employerId, Timestamp logTime) {
		this.positionId = positionId;
		this.stateId = stateId;
		this.positionState = positionState;
		this.employerId = employerId;
		this.logTime = logTime;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Long getStateId() {
		return this.stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Integer getPositionState() {
		return this.positionState;
	}

	public void setPositionState(Integer positionState) {
		this.positionState = positionState;
	}

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Timestamp getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

}