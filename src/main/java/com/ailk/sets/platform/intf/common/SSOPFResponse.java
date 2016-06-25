package com.ailk.sets.platform.intf.common;

import com.ailk.sets.platform.intf.cand.domain.Employer;

public class SSOPFResponse extends PFResponse {

	private static final long serialVersionUID = -4106553425373073498L;
	private Employer employer ;

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

}
