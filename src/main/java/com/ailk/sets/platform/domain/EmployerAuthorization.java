package com.ailk.sets.platform.domain;

/**
 * EmployerAuthorizationIntf entity. @author MyEclipse Persistence Tools
 */

public class EmployerAuthorization implements java.io.Serializable {

	private static final long serialVersionUID = 2771585193917945496L;

	private EmployerAuthorizationId id;

	private Integer privilege;
	
	public Integer getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Integer privilege) {
		this.privilege = privilege;
	}

	public EmployerAuthorizationId getId() {
		return this.id;
	}

	public void setId(EmployerAuthorizationId id) {
		this.id = id;
	}

}