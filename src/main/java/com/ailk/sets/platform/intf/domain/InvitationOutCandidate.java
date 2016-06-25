package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class InvitationOutCandidate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171401627851487572L;
	private Integer id;
	private String email;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "InvitationOutCandidate [id=" + id + ", email=" + email + ", name=" + name + "]";
	}
	
	

}
