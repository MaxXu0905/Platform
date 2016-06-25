package com.ailk.sets.platform.intf.model.feedback;

import java.sql.Timestamp;

/**
 * CandidateTestFeedback entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestFeedback implements java.io.Serializable {

	private static final long serialVersionUID = 6400044776207444923L;
	private Integer fbId;
	private Long testId;
	private Long questionId;
	private Integer opinion;
	private String fbItems;
	private String fbMore;
	private Timestamp fbTime;

	public Timestamp getFbTime() {
		return fbTime;
	}

	public void setFbTime(Timestamp fbTime) {
		this.fbTime = fbTime;
	}

	public Integer getFbId() {
		return this.fbId;
	}

	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getFbItems() {
		return this.fbItems;
	}

	public void setFbItems(String fbItems) {
		this.fbItems = fbItems;
	}

	public String getFbMore() {
		return this.fbMore;
	}

	public void setFbMore(String fbMore) {
		this.fbMore = fbMore;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getOpinion() {
		return opinion;
	}

	public void setOpinion(Integer opinion) {
		this.opinion = opinion;
	}

	
}