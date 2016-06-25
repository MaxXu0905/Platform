package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.domain.QbDifficultyLevel;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;

public class PaperInstanceInfo implements Serializable {
	private static final long serialVersionUID = -2060340431072998770L;
	private String skillId;
	private String skillName;
	private QbDifficultyLevel level;
	private String qType;
	private QbSkillDegree qbSkillDegree;// 掌握程度,如精通,了解等
	private List<Integer> skillDegrees;// 算出来的难度区间值，如实习精通java 则可能为1,2,3
	private Integer totalPoints;
	private Integer totalTime;
	private Integer totalNum;

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

	public String getqType() {
		return qType;
	}

	public void setqType(String qType) {
		this.qType = qType;
	}

	public QbSkillDegree getQbSkillDegree() {
		return qbSkillDegree;
	}

	public void setQbSkillDegree(QbSkillDegree qbSkillDegree) {
		this.qbSkillDegree = qbSkillDegree;
	}

	public List<Integer> getSkillDegrees() {
		return skillDegrees;
	}

	public void setSkillDegrees(List<Integer> skillDegrees) {
		this.skillDegrees = skillDegrees;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
}
