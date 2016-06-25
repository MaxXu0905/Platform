package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigCodeNameId entity. @author MyEclipse Persistence Tools
 */

public class ConfigCodeNameId implements java.io.Serializable {

	// Fields

	private String codeType;
	private String codeId;

	// Constructors

	/** default constructor */
	public ConfigCodeNameId() {
	}

	/** full constructor */
	public ConfigCodeNameId(String codeType, String codeId) {
		this.codeType = codeType;
		this.codeId = codeId;
	}

	// Property accessors

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ConfigCodeNameId))
			return false;
		ConfigCodeNameId castOther = (ConfigCodeNameId) other;

		return ((this.getCodeType() == castOther.getCodeType()) || (this.getCodeType() != null
				&& castOther.getCodeType() != null && this.getCodeType().equals(castOther.getCodeType())))
				&& ((this.getCodeId() == castOther.getCodeId()) || (this.getCodeId() != null
						&& castOther.getCodeId() != null && this.getCodeId().equals(castOther.getCodeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		result = 37 * result + (getCodeId() == null ? 0 : this.getCodeId().hashCode());
		return result;
	}

}