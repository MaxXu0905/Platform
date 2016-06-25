package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigInfoExt entity. @author MyEclipse Persistence Tools
 */

public class ConfigInfoExt implements java.io.Serializable {

	// Fields

	private String infoId;
	private String infoName;
	private Integer seq;
	private String dataType;
	private String valueType;
	private String valueSql;
	private Integer valueLength;
	private String verifyExp;
	private Integer isDefault;
	private Integer isMandatory;

	// Constructors

	/** default constructor */
	public ConfigInfoExt() {
	}

	/** minimal constructor */
	public ConfigInfoExt(String infoId) {
		this.infoId = infoId;
	}

	/** full constructor */
	public ConfigInfoExt(String infoId, String infoName, Integer seq, String dataType, String valueType, String valueSql, Integer valueLength, String verifyExp, Integer isDefault) {
		this.infoId = infoId;
		this.infoName = infoName;
		this.seq = seq;
		this.dataType = dataType;
		this.valueType = valueType;
		this.valueSql = valueSql;
		this.valueLength = valueLength;
		this.verifyExp = verifyExp;
		this.isDefault = isDefault;
	}

	// Property accessors

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoName() {
		return this.infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValueSql() {
		return this.valueSql;
	}

	public void setValueSql(String valueSql) {
		this.valueSql = valueSql;
	}

	public Integer getValueLength() {
		return this.valueLength;
	}

	public void setValueLength(Integer valueLength) {
		this.valueLength = valueLength;
	}

	public String getVerifyExp() {
		return this.verifyExp;
	}

	public void setVerifyExp(String verifyExp) {
		this.verifyExp = verifyExp;
	}

	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Integer isMandatory) {
		this.isMandatory = isMandatory;
	}

}