package com.ailk.sets.platform.intf.empl.domain;

import java.util.Date;

/**
 * ConfigReport entity. @author MyEclipse Persistence Tools
 */

public class ConfigReport implements java.io.Serializable {
	private static final long serialVersionUID = 8289377599330689641L;
	private String configId;
	private String configName;
	private Integer createBy;
	private Date createDate;

	// Constructors

	/** default constructor */
	public ConfigReport() {
	}

	/** minimal constructor */
	public ConfigReport(String configId) {
		this.configId = configId;
	}

	/** full constructor */
	public ConfigReport(String configId, String configName, Integer createBy, Date createDate) {
		this.configId = configId;
		this.configName = configName;
		this.createBy = createBy;
		this.createDate = createDate;
	}

	// Property accessors

	public String getConfigId() {
		return this.configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}