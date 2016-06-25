package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
/**
 * 难度对应技能关系
 * @author panyl
 *
 */
public class DegreeToSkills  implements Serializable{
	private static final long serialVersionUID = -2502093512221659297L;
	@Override
	public String toString() {
		return "DegreeToSkillLabels [labelDegree=" + labelDegree + ", selectedDegreeName=" + selectedDegreeName
				+ ", skillLabelStr=" + skillLabelStr + ", skillLabels=" + skills + "]";
	}
	private QbSkillDegree labelDegree;
	private List<QbSkill> skills;
	private String selectedDegreeName;
	public String getSelectedDegreeName() {
		return selectedDegreeName;
	}
	public void setSelectedDegreeName(String selectedDegreeName) {
		this.selectedDegreeName = selectedDegreeName;
	}
	private String skillLabelStr;
	public String getSkillLabelStr() {
		return skillLabelStr;
	}
	public void setSkillLabelStr(String skillLabelStr) {
		this.skillLabelStr = skillLabelStr;
	}
	public QbSkillDegree getLabelDegree() {
		return labelDegree;
	}
	public void setLabelDegree(QbSkillDegree labelDegree) {
		this.labelDegree = labelDegree;
	}
	public List<QbSkill> getSkills() {
		return skills;
	}
	public void setSkills(List<QbSkill> skills) {
		this.skills = skills;
	}
}
