package com.ailk.sets.platform.intf.cand.domain;

/**
 * ConfigRegion entity. @author MyEclipse Persistence Tools
 */

public class ConfigRegion implements java.io.Serializable {

	private static final long serialVersionUID = 2483081532870728480L;
	private Integer regionId;
	private Integer parentId;
	private String regionCode;
	private String regionName;
	private Integer regionLevel;

	// Constructors

	/** default constructor */
	public ConfigRegion() {
	}

	/** minimal constructor */
	public ConfigRegion(Integer regionId, Integer parentId) {
		this.regionId = regionId;
		this.parentId = parentId;
	}

	/** full constructor */
	public ConfigRegion(Integer regionId, Integer parentId, String regionCode, String regionName, Integer regionLevel) {
		this.regionId = regionId;
		this.parentId = parentId;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.regionLevel = regionLevel;
	}

	// Property accessors

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getRegionLevel() {
		return this.regionLevel;
	}

	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}

}