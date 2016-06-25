package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class PaperQuestionInfo implements Serializable {
	private static final long serialVersionUID = 5163861551634588016L;
	private Long questionId;
	private Integer answered;
	private Integer marked;
	private Integer suggestTime;

	public Integer getAnswered() {
		return answered;
	}

	public void setAnswered(Integer answered) {
		this.answered = answered;
	}

	public Integer getMarked() {
		return marked;
	}

	public void setMarked(Integer marked) {
		this.marked = marked;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}
}
