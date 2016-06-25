package com.ailk.sets.platform.domain;

import java.util.List;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

public class PaperQuestionSearchCondition {
	private QbQuestion qbQuestion;//原题目
	private List<String> skillIds;//查找技能id集合
	private List<String> relSkillIds;//原技能id集合
	private PaperPartSeqEnum paperType;//试卷类型
	private List<PaperQuestionToSkills> hasFoundQuestions;//已找到题目

    public QbQuestion getQbQuestion() {
		return qbQuestion;
	}
	public void setQbQuestion(QbQuestion qbQuestion) {
		this.qbQuestion = qbQuestion;
	}
	public List<String> getSkillIds() {
		return skillIds;
	}
	public void setSkillIds(List<String> skillIds) {
		this.skillIds = skillIds;
	}
	public List<String> getRelSkillIds() {
		return relSkillIds;
	}
	public void setRelSkillIds(List<String> relSkillIds) {
		this.relSkillIds = relSkillIds;
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
