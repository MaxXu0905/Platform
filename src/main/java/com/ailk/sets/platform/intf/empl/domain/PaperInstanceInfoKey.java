package com.ailk.sets.platform.intf.empl.domain;

import com.ailk.sets.platform.domain.QbDifficultyLevel;

public class PaperInstanceInfoKey {
	private String skillId;
	private String skillName;
	private QbDifficultyLevel level;
	
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
	public QbDifficultyLevel getLevel() {
		return level;
	}
	public void setLevel(QbDifficultyLevel level) {
		this.level = level;
	}
}
