package com.ailk.sets.platform.domain.paper;

/**
 * PaperPartId entity. @author MyEclipse Persistence Tools
 */

public class PaperPartId implements java.io.Serializable {
	private static final long serialVersionUID = 3912917587085546248L;
	private Integer paperId;
	private Integer partSeq;

	// Constructors

	/** default constructor */
	public PaperPartId() {
	}

	/** full constructor */
	public PaperPartId(Integer paperId, Integer partSeq) {
		this.paperId = paperId;
		this.partSeq = partSeq;
	}

	// Property accessors

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

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
		if (!(other instanceof PaperPartId))
			return false;
		PaperPartId castOther = (PaperPartId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this.getPaperId() != null
				&& castOther.getPaperId() != null && this.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getPartSeq() == castOther.getPartSeq()) || (this.getPartSeq() != null
						&& castOther.getPartSeq() != null && this.getPartSeq().equals(castOther.getPartSeq())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result + (getPartSeq() == null ? 0 : this.getPartSeq().hashCode());
		return result;
	}

}