package com.ailk.sets.platform.domain;

/**
 * EmployerChannel entity. @author MyEclipse Persistence Tools
 */

public class EmployerChannel implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8945330387675041275L;
	private EmployerChannelId id;
	private String employerName;

	// Constructors

	/** default constructor */
	public EmployerChannel() {
	}

	/** full constructor */
	public EmployerChannel(EmployerChannelId id, String employerName) {
		this.id = id;
		this.employerName = employerName;
	}

	// Property accessors

	public EmployerChannelId getId() {
		return this.id;
	}

	public void setId(EmployerChannelId id) {
		this.id = id;
	}

	public String getEmployerName() {
		return this.employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

}