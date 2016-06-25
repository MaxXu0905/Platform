package com.ailk.sets.platform.intf.domain.skilllabel;

import java.io.Serializable;

public class QbSkillSubjectViewId implements Serializable{
	private static final long serialVersionUID = -4108200868655847822L;
	private String skillId;
	private String programLanguage;
	public QbSkillSubjectViewId(){
		
	}
	public QbSkillSubjectViewId(String skillId, String programLanguage) {
		this.skillId = skillId;
		this.programLanguage = programLanguage;
	}
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	public String getProgramLanguage() {
		return programLanguage;
	}
	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}
	@Override
	public String toString() {
		return "QbSkillId [skillId=" + skillId + ", programLanguage="
				+ programLanguage + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((programLanguage == null) ? 0 : programLanguage.hashCode());
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
		QbSkillSubjectViewId other = (QbSkillSubjectViewId) obj;
		if (programLanguage == null) {
			if (other.programLanguage != null)
				return false;
		} else if (!programLanguage.equals(other.programLanguage))
			return false;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}
	
	
}
