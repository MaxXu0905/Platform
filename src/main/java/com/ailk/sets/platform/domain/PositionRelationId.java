package com.ailk.sets.platform.domain;

/**
 * PositionRelationId entity. @author MyEclipse Persistence Tools
 */

public class PositionRelationId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4130416870193195458L;
	private Integer positionGroupId;
	private Integer positionId;

	// Constructors

	/** default constructor */
	public PositionRelationId() {
	}

	/** full constructor */
	public PositionRelationId(Integer positionGroupId, Integer positionId) {
		this.positionGroupId = positionGroupId;
		this.positionId = positionId;
	}

	// Property accessors

	public Integer getPositionGroupId() {
		return this.positionGroupId;
	}

	public void setPositionGroupId(Integer positionGroupId) {
		this.positionGroupId = positionGroupId;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionRelationId))
			return false;
		PositionRelationId castOther = (PositionRelationId) other;

		return ((this.getPositionGroupId() == castOther.getPositionGroupId()) || (this.getPositionGroupId() != null
				&& castOther.getPositionGroupId() != null && this.getPositionGroupId().equals(
				castOther.getPositionGroupId())))
				&& ((this.getPositionId() == castOther.getPositionId()) || (this.getPositionId() != null
						&& castOther.getPositionId() != null && this.getPositionId().equals(castOther.getPositionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPositionGroupId() == null ? 0 : this.getPositionGroupId().hashCode());
		result = 37 * result + (getPositionId() == null ? 0 : this.getPositionId().hashCode());
		return result;
	}

}