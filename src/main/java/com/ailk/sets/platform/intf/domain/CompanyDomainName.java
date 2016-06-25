package com.ailk.sets.platform.intf.domain;

public class CompanyDomainName implements java.io.Serializable  {
	private static final long serialVersionUID = -4730689896031655933L;
	private String domainName;
	private String companyName;
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
