package com.ailk.sets.platform.intf.cand.domain;

/**
 * ConfigCollege entity. @author MyEclipse Persistence Tools
 */

public class ConfigCollegeInfo implements java.io.Serializable {
	private static final long serialVersionUID = 8158966562857349325L;
	private Integer collegeId;
	private String collegeName;
	private String matchName;

	// Property accessors

	public Integer getCollegeId() {
		return this.collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return this.collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}


	public String getMatchName() {
		return this.matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

}