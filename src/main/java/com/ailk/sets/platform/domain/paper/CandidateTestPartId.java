package com.ailk.sets.platform.domain.paper;

/**
 * PaperInstancePartId entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestPartId implements java.io.Serializable {
	private static final long serialVersionUID = -2332185921372920970L;
	private Long testId;

	private Integer partSeq;

	// Constructors

	/** default constructor */
	public CandidateTestPartId() {
	}

	/** full constructor */
	public CandidateTestPartId(Long testId, Integer partSeq) {
		this.testId = testId;
		this.partSeq = partSeq;
	}
	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}
	// Property accessors


	public Integer getPartSeq() {
		return this.partSeq;
	}

	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CandidateTestPartId))
			return false;
		CandidateTestPartId castOther = (CandidateTestPartId) other;

		return ((this.getTestId() == castOther.getTestId()) || (this.getTestId() != null
				&& castOther.getTestId() != null && this.getTestId().equals(castOther.getTestId())))
				&& ((this.getPartSeq() == castOther.getPartSeq()) || (this.getPartSeq() != null
						&& castOther.getPartSeq() != null && this.getPartSeq().equals(castOther.getPartSeq())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTestId() == null ? 0 : this.getTestId().hashCode());
		result = 37 * result + (getPartSeq() == null ? 0 : this.getPartSeq().hashCode());
		return result;
	}

}