package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ailk.sets.platform.intf.model.qb.QbSkill;

@SuppressWarnings("serial")
public class QbQuestionInfo implements Serializable {

	private long questionId;
	private String questionType;
	private String questionDesc;
	private int suggestTime;
	private Date modifyDate;
	private Double avgScore;
	private Double avgTime;
	private int answerNumber;
	private int rightAnswerNumber;
	private int discussNumber;
	private int badDiscussNumber;
	private List<QbSkill> skills;
	private List<String> answers;
	private String optAnswers; // 选择题答案
	private String mode; // / 模式
	private String refAnswer; // 参考答案
	private Integer category;

	public Double getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(Double avgTime) {
		this.avgTime = avgTime;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public int getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(int suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}

	public int getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(int answerNumber) {
		this.answerNumber = answerNumber;
	}

	public int getRightAnswerNumber() {
		return rightAnswerNumber;
	}

	public void setRightAnswerNumber(int rightAnswerNumber) {
		this.rightAnswerNumber = rightAnswerNumber;
	}

	public int getDiscussNumber() {
		return discussNumber;
	}

	public void setDiscussNumber(int discussNumber) {
		this.discussNumber = discussNumber;
	}

	public int getBadDiscussNumber() {
		return badDiscussNumber;
	}

	public void setBadDiscussNumber(int badDiscussNumber) {
		this.badDiscussNumber = badDiscussNumber;
	}

	public List<QbSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<QbSkill> skills) {
		this.skills = skills;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public String getOptAnswers() {
		return optAnswers;
	}

	public void setOptAnswers(String optAnswers) {
		this.optAnswers = optAnswers;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRefAnswer() {
		return refAnswer;
	}

	public void setRefAnswer(String refAnswer) {
		this.refAnswer = refAnswer;
	}

}
