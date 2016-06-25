package com.ailk.sets.platform.domain;

import java.util.List;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;

public class DegreeSkillSearchCondition {
	
	private int degree;//难度
	private List<String> skillIds;//技能
	private PaperPartSeqEnum paperType;//类型
	private List<PaperQuestionToSkills> hasFoundQuestions;//已找到题目
//	Map<String,List<PaperQuestionToSkills>> degreeSkillToQuestions;//按难度_类型_技能的题目缓存， 防止每次读取数据库
//	public Map<String, List<PaperQuestionToSkills>> getDegreeSkillToQuestions() {
//		return degreeSkillToQuestions;
//	}
//	public void setDegreeSkillToQuestions(Map<String, List<PaperQuestionToSkills>> degreeSkillToQuestions) {
//		this.degreeSkillToQuestions = degreeSkillToQuestions;
//	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public List<String> getSkillIds() {
		return skillIds;
	}
	public void setSkillIds(List<String> skillIds) {
		this.skillIds = skillIds;
	}
	public PaperPartSeqEnum getPaperType() {
		return paperType;
	}
	public void setPaperType(PaperPartSeqEnum paperType) {
		this.paperType = paperType;
	}
	public List<PaperQuestionToSkills> getHasFoundQuestions() {
		return hasFoundQuestions;
	}
	public void setHasFoundQuestions(List<PaperQuestionToSkills> hasFoundQuestions) {
		this.hasFoundQuestions = hasFoundQuestions;
	}
}
