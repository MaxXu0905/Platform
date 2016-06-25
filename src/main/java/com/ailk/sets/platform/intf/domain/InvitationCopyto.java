package com.ailk.sets.platform.intf.domain;

/**
 * InvitationCopyto entity. @author MyEclipse Persistence Tools
 */

public class InvitationCopyto implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1535542826065366365L;
	private InvitationCopytoId id;
	private String name;

	// Constructors

	/** default constructor */
	public InvitationCopyto() {
	}

	/** minimal constructor */
	public InvitationCopyto(InvitationCopytoId id) {
		this.id = id;
	}

	/** full constructor */
	public InvitationCopyto(InvitationCopytoId id, String name) {
		this.id = id;
		this.name = name;
	}

	// Property accessors

	public InvitationCopytoId getId() {
		return this.id;
	}

	public void setId(InvitationCopytoId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}