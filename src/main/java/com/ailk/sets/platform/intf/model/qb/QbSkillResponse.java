package com.ailk.sets.platform.intf.model.qb;

import com.ailk.sets.platform.intf.common.PFResponse;

public class QbSkillResponse extends PFResponse {

	private static final long serialVersionUID = 2324094357760353571L;
	private String skillId;

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

}
