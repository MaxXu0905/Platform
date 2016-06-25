package com.ailk.sets.platform.intf.cand.domain;

/**
 * CandidateInfoExt entity. @author MyEclipse Persistence Tools
 */

public class CandidateInfoExt implements java.io.Serializable {
	private static final long serialVersionUID = 2828404620683543382L;
	private CandidateInfoExtId id;
	private String value;
	private String realValue;

    public String getRealValue() {
		return realValue;
	}

	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}

	/** default constructor */
	public CandidateInfoExt() {
	}

	/** minimal constructor */
	public CandidateInfoExt(CandidateInfoExtId id) {
		this.id = id;
	}

	/** full constructor */
	public CandidateInfoExt(CandidateInfoExtId id, String value) {
		this.id = id;
		this.value = value;
	}

	// Property accessors

	public CandidateInfoExtId getId() {
		return this.id;
	}

	public void setId(CandidateInfoExtId id) {
		this.id = id;
	}

	// Constructors

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}