package com.ailk.sets.platform.domain;

/**
 * CompanyContact entity. @author MyEclipse Persistence Tools
 */

public class CompanyContact implements java.io.Serializable {

	private static final long serialVersionUID = -2280454739547319427L;
	private Integer companyId;
	private String contactName;
	private String contactEmail;
	private String contactPhone;

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}