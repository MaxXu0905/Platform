package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigReportPartId entity. @author MyEclipse Persistence Tools
 */

public class ConfigReportPartId implements java.io.Serializable {

	// Fields

	private String configId;
	private String partCode;

	// Constructors

	/** default constructor */
	public ConfigReportPartId() {
	}

	/** full constructor */
	public ConfigReportPartId(String configId, String partCode) {
		this.configId = configId;
		this.partCode = partCode;
	}

	// Property accessors

	public String getConfigId() {
		return this.configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getPartCode() {
		return this.partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ConfigReportPartId))
			return false;
		ConfigReportPartId castOther = (ConfigReportPartId) other;

		return ((this.getConfigId() == castOther.getConfigId()) || (this.getConfigId() != null && castOther.getConfigId() != null && this.getConfigId().equals(castOther.getConfigId())))
				&& ((this.getPartCode() == castOther.getPartCode()) || (this.getPartCode() != null && castOther.getPartCode() != null && this.getPartCode().equals(castOther.getPartCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getConfigId() == null ? 0 : this.getConfigId().hashCode());
		result = 37 * result + (getPartCode() == null ? 0 : this.getPartCode().hashCode());
		return result;
	}

}