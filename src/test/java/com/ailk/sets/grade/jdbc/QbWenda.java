package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "qb_wenda")
public class QbWenda implements Serializable, Cloneable {

	@Id
	@Column(name = "question_id", nullable = false)
	private String questionId;

	@Column(name = "bank_name", nullable = false)
	private String bankName;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "option1", nullable = false)
	private String option1;

	@Column(name = "option2", nullable = false)
	private String option2;

	@Column(name = "option3", nullable = false)
	private String option3;

	@Column(name = "option4", nullable = false)
	private String option4;

	@Column(name = "option5", nullable = false)
	private String option5;

	@Column(name = "option6", nullable = false)
	private String option6;

	@Column(name = "answer", nullable = false)
	private int answer;

	@Column(name = "grade", nullable = false)
	private String grade;

	@Column(name = "score", nullable = false)
	private double score;

	@Column(name = "total", nullable = false)
	private double total;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	public String getOption6() {
		return option6;
	}

	public void setOption6(String option6) {
		this.option6 = option6;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
