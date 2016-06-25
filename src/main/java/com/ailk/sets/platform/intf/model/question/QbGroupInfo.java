package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.Date;

import com.ailk.sets.grade.intf.GetGroupResponse;

@SuppressWarnings("serial")
public class QbGroupInfo implements Serializable {

	private long questionId;
	private String questionType;
	private String questionDesc;
	private int suggestTime;
	private Date modifyDate;
	private Double avgScore;
	private int answerNumber;
	private int rightAnswerNumber;
	private int discussNumber;
	private int badDiscussNumber;

	private GetGroupResponse groupQuestion;
	
	public GetGroupResponse getGroupQuestion() {
		return groupQuestion;
	}

	public void setGroupQuestion(GetGroupResponse groupQuestion) {
		this.groupQuestion = groupQuestion;
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

}
