package com.ailk.sets.platform.intf.model.employer;

import java.io.Serializable;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponse;

public class RegisterInfo extends PFResponse implements Serializable {
	private static final long serialVersionUID = -4406159649833556375L;
	private Employer employer;

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
}
