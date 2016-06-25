package com.ailk.sets.platform.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
/**
 * 难度对应技能关系  DegreeToSkills为对外接口，此类为内部
 * @author panyl
 *
 */
public class DegreeToSkillLabels  implements Serializable{
	private static final long serialVersionUID = -2502093512221659297L;
	@Override
	public String toString() {
		return "DegreeToSkillLabels [labelDegree=" + labelDegree + ", selectedDegreeName=" + selectedDegreeName
				+ ", skillLabelStr=" + skillLabelStr + ", skillLabels=" + skillLabels + "]";
	}
	private QbSkillDegree labelDegree;
	private List<PositionSkillScopeView> skillLabels;
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
	public List<PositionSkillScopeView> getSkillLabels() {
		return skillLabels;
	}
	public void setSkillLabels(List<PositionSkillScopeView> skillLabels) {
		this.skillLabels = skillLabels;
	}
	
}
