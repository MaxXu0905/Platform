package com.ailk.sets.platform.domain;

/**
 * CompanyInfoExt entity. @author MyEclipse Persistence Tools
 */

public class PositionInfoExt implements java.io.Serializable {

	private static final long serialVersionUID = -585750175289601096L;
	private PositionInfoExtId id;
	private String infoName;
	private Integer seq;
	private String dataType;
	private String valueType;
	private String valueSql;
	private Integer valueLength;
	private String verifyExp;
	private Integer mandatory;

	// Constructors

	/** default constructor */
	public PositionInfoExt() {
	}

	/** minimal constructor */
	public PositionInfoExt(PositionInfoExtId id) {
		this.id = id;
	}

	/** full constructor */
	public PositionInfoExt(PositionInfoExtId id, String infoName, Integer seq, String dataType, String valueType, String valueSql, Integer valueLength, String verifyExp, Integer mandatory) {
		this.id = id;
		this.infoName = infoName;
		this.seq = seq;
		this.dataType = dataType;
		this.valueType = valueType;
		this.valueSql = valueSql;
		this.valueLength = valueLength;
		this.verifyExp = verifyExp;
		this.mandatory = mandatory;
	}

	// Property accessors

	public PositionInfoExtId getId() {
		return this.id;
	}

	public void setId(PositionInfoExtId id) {
		this.id = id;
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

	public Integer getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(Integer mandatory) {
		this.mandatory = mandatory;
	}

}