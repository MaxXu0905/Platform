package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigReportPart entity. @author MyEclipse Persistence Tools
 */

public class ConfigReportPart implements java.io.Serializable {

	// Fields

	private ConfigReportPartId id;
	private String partTitle;
	private String partDesc;

	// Constructors

	/** default constructor */
	public ConfigReportPart() {
	}

	/** minimal constructor */
	public ConfigReportPart(ConfigReportPartId id) {
		this.id = id;
	}

	/** full constructor */
	public ConfigReportPart(ConfigReportPartId id, String partTitle, String partDesc) {
		this.id = id;
		this.partTitle = partTitle;
		this.partDesc = partDesc;
	}

	// Property accessors

	public ConfigReportPartId getId() {
		return this.id;
	}

	public void setId(ConfigReportPartId id) {
		this.id = id;
	}

	public String getPartTitle() {
		return this.partTitle;
	}

	public void setPartTitle(String partTitle) {
		this.partTitle = partTitle;
	}

	public String getPartDesc() {
		return this.partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

}