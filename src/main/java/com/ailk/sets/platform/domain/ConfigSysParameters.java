package com.ailk.sets.platform.domain;

/**
 * ConfigSysParameters entity. @author MyEclipse Persistence Tools
 */

public class ConfigSysParameters implements java.io.Serializable {

	private static final long serialVersionUID = 4534679976331922965L;
	private Integer paramId;
	private String paramName;
	private String paramEname;
	private String paramDesc;
	private String paramValue;

	// Constructors

	/** default constructor */
	public ConfigSysParameters() {
	}

	/** minimal constructor */
	public ConfigSysParameters(Integer paramId) {
		this.paramId = paramId;
	}

	/** full constructor */
	public ConfigSysParameters(Integer paramId, String paramName, String paramEname, String paramDesc, String paramValue) {
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramEname = paramEname;
		this.paramDesc = paramDesc;
		this.paramValue = paramValue;
	}

	// Property accessors

	public Integer getParamId() {
		return this.paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamEname() {
		return this.paramEname;
	}

	public void setParamEname(String paramEname) {
		this.paramEname = paramEname;
	}

	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}