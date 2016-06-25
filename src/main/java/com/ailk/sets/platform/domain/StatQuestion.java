package com.ailk.sets.platform.domain;

import com.ailk.sets.grade.utils.MathUtils;

/**
 * StatQuestion entity. @author MyEclipse Persistence Tools
 */

public class StatQuestion implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2165399619888112517L;
	private Long questionId;
	private Integer totalNum;
	private Integer answerNum;
	private Integer correctNum;
	private Integer totalSpendTime;
	private Integer totalCorrectTime;
	private Integer totalScore;
	private Integer praiseNum;
	private Integer negNum;
	private Integer answerNumAdded;
	private Integer correctNumAdded;
	private Integer degreeOld;
	private Integer degreeNew;
	private Integer adjustedTimes;
	private Integer statId;

	// Constructors

	/** default constructor */
	public StatQuestion() {
	}

	/** minimal constructor */
	public StatQuestion(Long questionId) {
		this.questionId = questionId;
	}

	/** full constructor */
	public StatQuestion(Long questionId, Integer totalNum, Integer answerNum,
			Integer correctNum, Integer totalSpendTime,
			Integer totalCorrectTime, Integer totalScore, Integer praiseNum,
			Integer negNum, Integer answerNumAdded, Integer correctNumAdded,
			Integer degreeOld, Integer degreeNew, Integer adjustedTimes,
			Integer statId) {
		this.questionId = questionId;
		this.totalNum = totalNum;
		this.answerNum = answerNum;
		this.correctNum = correctNum;
		this.totalSpendTime = totalSpendTime;
		this.totalCorrectTime = totalCorrectTime;
		this.totalScore = totalScore;
		this.praiseNum = praiseNum;
		this.negNum = negNum;
		this.answerNumAdded = answerNumAdded;
		this.correctNumAdded = correctNumAdded;
		this.degreeOld = degreeOld;
		this.degreeNew = degreeNew;
		this.adjustedTimes = adjustedTimes;
		this.statId = statId;
	}

	// Property accessors

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getAnswerNum() {
		return this.answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public Integer getCorrectNum() {
		return this.correctNum;
	}

	public void setCorrectNum(Integer correctNum) {
		this.correctNum = correctNum;
	}

	public Integer getTotalSpendTime() {
		return this.totalSpendTime;
	}

	public void setTotalSpendTime(Integer totalSpendTime) {
		this.totalSpendTime = totalSpendTime;
	}

	public Integer getTotalCorrectTime() {
		return this.totalCorrectTime;
	}

	public void setTotalCorrectTime(Integer totalCorrectTime) {
		this.totalCorrectTime = totalCorrectTime;
	}

	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getPraiseNum() {
		return this.praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getNegNum() {
		return this.negNum;
	}

	public void setNegNum(Integer negNum) {
		this.negNum = negNum;
	}

	public Integer getAnswerNumAdded() {
		return this.answerNumAdded;
	}

	public void setAnswerNumAdded(Integer answerNumAdded) {
		this.answerNumAdded = answerNumAdded;
	}

	public Integer getCorrectNumAdded() {
		return this.correctNumAdded;
	}

	public void setCorrectNumAdded(Integer correctNumAdded) {
		this.correctNumAdded = correctNumAdded;
	}

	public Integer getDegreeOld() {
		return this.degreeOld;
	}

	public void setDegreeOld(Integer degreeOld) {
		this.degreeOld = degreeOld;
	}

	public Integer getDegreeNew() {
		return this.degreeNew;
	}

	public void setDegreeNew(Integer degreeNew) {
		this.degreeNew = degreeNew;
	}

	public Integer getAdjustedTimes() {
		return this.adjustedTimes;
	}

	public void setAdjustedTimes(Integer adjustedTimes) {
		this.adjustedTimes = adjustedTimes;
	}

	public Integer getStatId() {
		return this.statId;
	}

	public void setStatId(Integer statId) {
		this.statId = statId;
	}

	public Double getAvgTime(){
		if(this.getCorrectNum() == null || this.getCorrectNum() == 0)
			return null;
		return MathUtils.round((double) this.getTotalCorrectTime()
				/ this.getCorrectNum(),2);
	}
}