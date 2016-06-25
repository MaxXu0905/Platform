package com.ailk.sets.platform.domain;

/**
 * CompanyInfoExtId entity. @author MyEclipse Persistence Tools
 */

public class PositionInfoExtId implements java.io.Serializable {

	private static final long serialVersionUID = -2457823326993793643L;
	private Integer employerId;
	private Integer positionId;

	private String infoId;

	// Constructors

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	/** default constructor */
	public PositionInfoExtId() {
	}

	/** full constructor */
	public PositionInfoExtId(Integer employerId,  Integer positionId,String infoId) {
		this.employerId = employerId;
		this.infoId = infoId;
		this.positionId = positionId;
	}

	// Property accessors

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionInfoExtId))
			return false;
		PositionInfoExtId castOther = (PositionInfoExtId) other;

		return ((this.getEmployerId() == castOther.getEmployerId()) || (this.getEmployerId() != null && castOther.getEmployerId() != null && this.getEmployerId().equals(castOther.getEmployerId())))
				&& ((this.getInfoId() == castOther.getInfoId()) || (this.getInfoId() != null && castOther.getInfoId() != null && this.getInfoId().equals(castOther.getInfoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEmployerId() == null ? 0 : this.getEmployerId().hashCode());
		result = 37 * result + (getInfoId() == null ? 0 : this.getInfoId().hashCode());
		return result;
	}

}