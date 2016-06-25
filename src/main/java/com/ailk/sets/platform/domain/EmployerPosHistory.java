package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * EmployerPosHistory entity. @author MyEclipse Persistence Tools
 */

public class EmployerPosHistory implements java.io.Serializable {

	private static final long serialVersionUID = -4106325587694282226L;
	private EmployerPosHistoryId id;
	private String category;
	private Timestamp lastUse;
	private Integer frequency;

	public EmployerPosHistoryId getId() {
		return this.id;
	}

	public void setId(EmployerPosHistoryId id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Timestamp getLastUse() {
		return this.lastUse;
	}

	public void setLastUse(Timestamp lastUse) {
		this.lastUse = lastUse;
	}

	public Integer getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

}