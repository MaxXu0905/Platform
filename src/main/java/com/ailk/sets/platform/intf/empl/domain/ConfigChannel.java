package com.ailk.sets.platform.intf.empl.domain;

/**
 * ConfigChannel entity. @author MyEclipse Persistence Tools
 */

public class ConfigChannel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1027750359723994026L;
	private Integer id;
	private String name;
	private String secret;

	// Constructors

	/** default constructor */
	public ConfigChannel() {
	}

	/** minimal constructor */
	public ConfigChannel(Integer id, String name, String secret) {
		this.id = id;
		this.name = name;
		this.secret = secret;
	}


	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}


}