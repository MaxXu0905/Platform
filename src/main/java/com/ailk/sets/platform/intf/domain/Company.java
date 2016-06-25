package com.ailk.sets.platform.intf.domain;

import java.util.Date;

/**
 * Company entity. @author MyEclipse Persistence Tools
 */

public class Company implements java.io.Serializable {

	private static final long serialVersionUID = 6932092195796705470L;
	private Integer companyId;
	private String companyName;
	private String companyLogo;
	private Integer companyState;
	private Integer industry;
	private Date registerDate;
	private Integer approveBy;
	private Date approveDate;

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogo() {
		return this.companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public Integer getCompanyState() {
		return this.companyState;
	}

	public void setCompanyState(Integer companyState) {
		this.companyState = companyState;
	}

	public Integer getIndustry() {
		return this.industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getApproveBy() {
		return this.approveBy;
	}

	public void setApproveBy(Integer approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

}