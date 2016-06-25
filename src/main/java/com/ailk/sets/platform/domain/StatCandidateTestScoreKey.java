package com.ailk.sets.platform.domain;

public class StatCandidateTestScoreKey {

	private QbDifficultyLevel level;
	private String skillId;
	private String qType;
	
	public StatCandidateTestScoreKey(QbDifficultyLevel level, String skillId, String qType) {
		this.level = level;
		this.skillId = skillId;
		this.qType = qType;
	}
	@Override
	public String toString() {
		return "StatCandidateTestScoreKey [level=" + level + ", qType=" + qType + ", skillId=" + skillId + "]";
	}
	public QbDifficultyLevel getLevel() {
		return level;
	}

	public void setLevel(QbDifficultyLevel level) {
		this.level = level;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getqType() {
		return qType;
	}

	public void setqType(String qType) {
		this.qType = qType;
	}

}
