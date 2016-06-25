package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class InvitationOutEmployer implements Serializable{
	private static final long serialVersionUID = 7109010321021678871L;
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
		return "InvitationOutEmployer [id=" + id + ", email=" + email + ", name=" + name + "]";
	}

}
