package com.ailk.sets.platform.intf.model.candidateTest;

/**
 * CandidateTestSwitchPage entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestSwitchPage implements java.io.Serializable {

	// Fields

	private CandidateTestSwitchPageId id;
	private Integer partSeq;
	private Long questionId;

	// Constructors

	/** default constructor */
	public CandidateTestSwitchPage() {
	}

	/** full constructor */
	public CandidateTestSwitchPage(CandidateTestSwitchPageId id, Integer partSeq, Long questionId) {
		this.id = id;
		this.partSeq = partSeq;
		this.questionId = questionId;
	}

	// Property accessors

	public CandidateTestSwitchPageId getId() {
		return this.id;
	}

	public void setId(CandidateTestSwitchPageId id) {
		this.id = id;
	}

	public Integer getPartSeq() {
		return this.partSeq;
	}

	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}