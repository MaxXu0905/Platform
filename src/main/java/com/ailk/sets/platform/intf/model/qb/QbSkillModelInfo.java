package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;

public class QbSkillModelInfo implements Serializable{
	private static final long serialVersionUID = -2598715684379345401L;
	private String skillId;
	private String skillName;
	private Integer qbId;
	
	private List<QbSkillDegree> degreeToQuestionNum;//程度与题数对应关系
	public Integer getQbId() {
		return qbId;
	}
	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}
	public List<QbSkillDegree> getDegreeToQuestionNum() {
		return degreeToQuestionNum;
	}
	public void setDegreeToQuestionNum(List<QbSkillDegree> degreeToQuestionNum) {
		this.degreeToQuestionNum = degreeToQuestionNum;
	}
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
}
