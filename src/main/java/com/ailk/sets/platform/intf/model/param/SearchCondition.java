package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SearchCondition implements Serializable {

	private boolean choice; // 是否为选择题
	private boolean program; // 是否为编程题
	private boolean essay; // 是否为问答题
	private String questionDesc; // 标题
	private String programLang; // 编程语言
	private String skillId; // 技能ID

	private Boolean modifyDateAsc; // 是否按最后编辑时间升序
	private Boolean avgScoreAsc; // 是否按平均分升序
	private Boolean answerNumAsc; // 是否按答题数升序
	private Boolean suggestTimeAsc; // 是否按答题时间升序
	private Boolean negNumAsc; // 是否按差评升序

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public boolean isProgram() {
		return program;
	}

	public void setProgram(boolean program) {
		this.program = program;
	}

	public boolean isEssay() {
		return essay;
	}

	public void setEssay(boolean essay) {
		this.essay = essay;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public String getProgramLang() {
		return programLang;
	}

	public void setProgramLang(String programLang) {
		this.programLang = programLang;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public Boolean getModifyDateAsc() {
		return modifyDateAsc;
	}

	public void setModifyDateAsc(Boolean modifyDateAsc) {
		this.modifyDateAsc = modifyDateAsc;
	}

	public Boolean getAvgScoreAsc() {
		return avgScoreAsc;
	}

	public void setAvgScoreAsc(Boolean avgScoreAsc) {
		this.avgScoreAsc = avgScoreAsc;
	}

	public Boolean getAnswerNumAsc() {
		return answerNumAsc;
	}

	public void setAnswerNumAsc(Boolean answerNumAsc) {
		this.answerNumAsc = answerNumAsc;
	}

	public Boolean getSuggestTimeAsc() {
		return suggestTimeAsc;
	}

	public void setSuggestTimeAsc(Boolean suggestTimeAsc) {
		this.suggestTimeAsc = suggestTimeAsc;
	}

	public Boolean getNegNumAsc() {
		return negNumAsc;
	}

	public void setNegNumAsc(Boolean negNumAsc) {
		this.negNumAsc = negNumAsc;
	}

	/**
	 * 是否有排序条件，没有排序条件需要根据modifyDateAsc 降序
	 * @return
	 */
	public boolean hasOrderCondition() {
		return modifyDateAsc != null || avgScoreAsc != null || answerNumAsc != null || suggestTimeAsc != null
				|| negNumAsc != null;
	}

	@Override
	public String toString() {
		return "SearchCondition [choice=" + choice + ", program=" + program + ", essay=" + essay + ", questionDesc="
				+ questionDesc + ", programLang=" + programLang + ", skillId=" + skillId + ", modifyDateAsc="
				+ modifyDateAsc + ", avgScoreAsc=" + avgScoreAsc + ", answerNumAsc=" + answerNumAsc
				+ ", suggestTimeAsc=" + suggestTimeAsc + ", negNumAsc=" + negNumAsc + "]";
	}
	

}
