package com.ailk.sets.platform.intf.cand.domain;

import java.util.List;

/**
 * ConfigRegion entity. @author MyEclipse Persistence Tools
 */

public class ConfigRegionInfo implements java.io.Serializable {

	private static final long serialVersionUID = 2483081532870728480L;
	private Integer regionId;
	private String regionName;
	private List<ConfigRegionInfo> children;

	// Constructors

	public List<ConfigRegionInfo> getChildren() {
		return children;
	}


	public void setChildren(List<ConfigRegionInfo> children) {
		this.children = children;
	}


	/** default constructor */
	public ConfigRegionInfo() {
	}


	// Property accessors

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}



	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


}