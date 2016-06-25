package com.ailk.sets.platform.intf.model;

import java.sql.Timestamp;

/**
 * ContactInfo entity. @author MyEclipse Persistence Tools
 */

public class ContactInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7236133052970004251L;
	private Integer id;
	private String name;
	private String company;
	private String email;
	private String phone;
	private String remark;
	private Timestamp contactDate;

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

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getContactDate() {
		return this.contactDate;
	}

	public void setContactDate(Timestamp contactDate) {
		this.contactDate = contactDate;
	}

}