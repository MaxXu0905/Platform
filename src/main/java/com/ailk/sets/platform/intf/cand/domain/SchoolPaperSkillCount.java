package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class SchoolPaperSkillCount implements Serializable {
	
	private static final long serialVersionUID = 5395633031640615877L;
	private String skillName;
	private Integer questionLength;
	private String skillId;
	
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public Integer getQuestionLength() {
		return questionLength;
	}
	public void setQuestionLength(Integer questionLength) {
		this.questionLength = questionLength;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchoolPaperSkillCount other = (SchoolPaperSkillCount) obj;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}

	
	
}
