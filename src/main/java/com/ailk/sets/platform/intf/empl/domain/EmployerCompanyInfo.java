package com.ailk.sets.platform.intf.empl.domain;

import com.ailk.sets.platform.intf.common.PFResponse;

public class EmployerCompanyInfo   extends PFResponse{
	private static final long serialVersionUID = -483540325737576344L;
    private String companyName;
    
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
