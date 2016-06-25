package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "stat_question_test")
public class StatQuestionTest implements Serializable, Cloneable {

	@EmbeddedId
	private StatQuestionTestPK statQuestionTestPK;

	@Column(name = "total_num", nullable = false)
	private int totalNum;

	@Column(name = "answer_num", nullable = false)
	private int answerNum;

	@Column(name = "correct_num", nullable = false)
	private int correctNum;

	@Column(name = "total_spend_time", nullable = false)
	private int totalSpendTime;

	@Column(name = "total_score", nullable = false)
	private double totalScore;

	public StatQuestionTestPK getStatQuestionTestPK() {
		return statQuestionTestPK;
	}

	public void setStatQuestionTestPK(StatQuestionTestPK statQuestionTestPK) {
		this.statQuestionTestPK = statQuestionTestPK;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

	public int getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(int correctNum) {
		this.correctNum = correctNum;
	}

	public int getTotalSpendTime() {
		return totalSpendTime;
	}

	public void setTotalSpendTime(int totalSpendTime) {
		this.totalSpendTime = totalSpendTime;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

}
