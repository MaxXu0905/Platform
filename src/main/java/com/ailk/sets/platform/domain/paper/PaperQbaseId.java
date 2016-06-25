package com.ailk.sets.platform.domain.paper;

/**
 * PaperQbaseId entity. @author MyEclipse Persistence Tools
 */

public class PaperQbaseId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3712975336478348100L;
	private Integer paperId;
	private Integer qbId;

	// Constructors

	/** default constructor */
	public PaperQbaseId() {
	}

	/** full constructor */
	public PaperQbaseId(Integer paperId, Integer qbId) {
		this.paperId = paperId;
		this.qbId = qbId;
	}

	// Property accessors

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getQbId() {
		return this.qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PaperQbaseId))
			return false;
		PaperQbaseId castOther = (PaperQbaseId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this.getPaperId() != null
				&& castOther.getPaperId() != null && this.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getQbId() == castOther.getQbId()) || (this.getQbId() != null && castOther.getQbId() != null && this
						.getQbId().equals(castOther.getQbId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result + (getQbId() == null ? 0 : this.getQbId().hashCode());
		return result;
	}

}