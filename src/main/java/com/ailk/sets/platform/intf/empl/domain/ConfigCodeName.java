package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigCodeName entity. @author MyEclipse Persistence Tools
 */

public class ConfigCodeName implements java.io.Serializable {

	// Fields

	private ConfigCodeNameId id;
	private String codeName;
	private Integer seq;
	private String codeDesc;

	// Constructors

	/** default constructor */
	public ConfigCodeName() {
	}

	/** minimal constructor */
	public ConfigCodeName(ConfigCodeNameId id) {
		this.id = id;
	}

	/** full constructor */
	public ConfigCodeName(ConfigCodeNameId id, String codeName, Integer seq, String codeDesc) {
		this.id = id;
		this.codeName = codeName;
		this.seq = seq;
		this.codeDesc = codeDesc;
	}

	// Property accessors

	public ConfigCodeNameId getId() {
		return this.id;
	}

	public void setId(ConfigCodeNameId id) {
		this.id = id;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

}