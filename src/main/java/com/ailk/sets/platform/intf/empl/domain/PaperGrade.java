package com.ailk.sets.platform.intf.empl.domain;

/**
 * PaperGrade entity. @author MyEclipse Persistence Tools
 */

public class PaperGrade implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5270165644801180502L;
	private PaperGradeId id;
	private Integer answerTime;
	private Integer questionMissing;
	private Integer questionTotal;
	private Double scoreCorrect;
	private Double scoreTotal;
	private String skillName;
	private Integer questionCorrect;

	// Constructors

	/** default constructor */
	public PaperGrade() {
	}

	/** full constructor */
	public PaperGrade(PaperGradeId id, Integer answerTime, Integer questionMissing, Integer questionTotal,
			Double scoreCorrect, Double scoreTotal, String skillName, Integer questionCorrect) {
		this.id = id;
		this.answerTime = answerTime;
		this.questionMissing = questionMissing;
		this.questionTotal = questionTotal;
		this.scoreCorrect = scoreCorrect;
		this.scoreTotal = scoreTotal;
		this.skillName = skillName;
		this.questionCorrect = questionCorrect;
	}

	// Property accessors

	public PaperGradeId getId() {
		return this.id;
	}

	public void setId(PaperGradeId id) {
		this.id = id;
	}

	public Integer getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getQuestionMissing() {
		return this.questionMissing;
	}

	public void setQuestionMissing(Integer questionMissing) {
		this.questionMissing = questionMissing;
	}

	public Integer getQuestionTotal() {
		return this.questionTotal;
	}

	public void setQuestionTotal(Integer questionTotal) {
		this.questionTotal = questionTotal;
	}

	public Double getScoreCorrect() {
		return this.scoreCorrect;
	}

	public void setScoreCorrect(Double scoreCorrect) {
		this.scoreCorrect = scoreCorrect;
	}

	public Double getScoreTotal() {
		return this.scoreTotal;
	}

	public void setScoreTotal(Double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getQuestionCorrect() {
		return this.questionCorrect;
	}

	public void setQuestionCorrect(Integer questionCorrect) {
		this.questionCorrect = questionCorrect;
	}

}