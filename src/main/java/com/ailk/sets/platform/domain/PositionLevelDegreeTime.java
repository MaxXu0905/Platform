package com.ailk.sets.platform.domain;

/**
 * PositionLevelDegreeTime entity. @author MyEclipse Persistence Tools
 */

public class PositionLevelDegreeTime implements java.io.Serializable {
	private static final long serialVersionUID = -4651085850217618882L;
	private PositionLevelDegreeTimeId id;
	private Integer suggestTime;

	// Constructors

	/** default constructor */
	public PositionLevelDegreeTime() {
	}

	/** full constructor */
	public PositionLevelDegreeTime(PositionLevelDegreeTimeId id, Integer suggestTime) {
		this.id = id;
		this.suggestTime = suggestTime;
	}

	// Property accessors

	public PositionLevelDegreeTimeId getId() {
		return this.id;
	}

	public void setId(PositionLevelDegreeTimeId id) {
		this.id = id;
	}

	public Integer getSuggestTime() {
		return this.suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

}