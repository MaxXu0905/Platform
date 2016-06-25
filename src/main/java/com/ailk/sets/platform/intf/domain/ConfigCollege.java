package com.ailk.sets.platform.intf.domain;

/**
 * ConfigCollege entity. @author MyEclipse Persistence Tools
 */

public class ConfigCollege implements java.io.Serializable {
	private static final long serialVersionUID = 8158966562857349325L;
	private Integer collegeId;
	private String collegeName;
	private String collegeLevel;
	private String catalog;
	private String abbreviation;
	private String matchName;
	private Integer regionId;
	private String regionName;

	// Constructors

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/** default constructor */
	public ConfigCollege() {
	}

	/** minimal constructor */
	public ConfigCollege(Integer collegeId, String collegeName, String collegeLevel, Integer regionId) {
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.collegeLevel = collegeLevel;
		this.regionId = regionId;
	}

	/** full constructor */
	public ConfigCollege(Integer collegeId, String collegeName, String collegeLevel, String catalog,
			String abbreviation, String matchName, Integer regionId) {
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.collegeLevel = collegeLevel;
		this.catalog = catalog;
		this.abbreviation = abbreviation;
		this.matchName = matchName;
		this.regionId = regionId;
	}

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

	public String getCollegeLevel() {
		return this.collegeLevel;
	}

	public void setCollegeLevel(String collegeLevel) {
		this.collegeLevel = collegeLevel;
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getMatchName() {
		return this.matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

}