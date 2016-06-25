package com.ailk.sets.platform.intf.school.domain;

import java.io.Serializable;

/**
 * 报告技能分数
 * 
 * @author panyl
 * 
 */
public class ReportSkillsScoreInfo implements Serializable {
	private static final long serialVersionUID = 7646037670054423229L;

	private String skillId;
	private String skillName;
	private Double score;

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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
