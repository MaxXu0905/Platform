package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class CandidateExamInfo implements Serializable {
	private static final long serialVersionUID = 4293971657961172616L;
	private Long questionId;
	private String partSeq;
	private String timeLeft; //剩余时间 hh:mm:ss格式的时间
	private Integer partIndex;
	private Integer questionIndex;

	public Integer getPartIndex() {
		return partIndex;
	}
	public void setPartIndex(Integer partIndex) {
		this.partIndex = partIndex;
	}

	public Integer getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}
	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getPartSeq() {
		return partSeq;
	}
	public void setPartSeq(String partSeq) {
		this.partSeq = partSeq;
	}
}
