package com.ailk.sets.platform.domain;

import java.util.List;

import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;

/**
 * 技能标签对难度值
 * 
 * @author panyl
 * 
 */
public class SkillToDegrees {
	private QbSkillSubjectView skill;// 技能
	private List<Integer> degrees;// 难度

	public QbSkillSubjectView getSkill() {
		return skill;
	}

	public void setSkill(QbSkillSubjectView skill) {
		this.skill = skill;
	}

	public List<Integer> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<Integer> degrees) {
		this.degrees = degrees;
	}
}
