package com.ailk.sets.platform.intf.model.employer;

import java.io.Serializable;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponse;

/**
 * 登录信息
 * 
 * @author 毕希研
 * 
 */
public class LoginInfo extends PFResponse implements Serializable {
	private static final long serialVersionUID = 1227118692589679046L;
	private Employer employer;

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

}
