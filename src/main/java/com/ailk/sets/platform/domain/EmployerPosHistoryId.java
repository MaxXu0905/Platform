package com.ailk.sets.platform.domain;

/**
 * EmployerPosHistoryId entity. @author MyEclipse Persistence Tools
 */

public class EmployerPosHistoryId implements java.io.Serializable {

	private static final long serialVersionUID = -6002305968149198833L;
	private String historyId;
	private Integer employerId;

	public String getHistoryId() {
		return this.historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployerPosHistoryId))
			return false;
		EmployerPosHistoryId castOther = (EmployerPosHistoryId) other;

		return ((this.getHistoryId() == castOther.getHistoryId()) || (this.getHistoryId() != null && castOther.getHistoryId() != null && this.getHistoryId().equals(castOther.getHistoryId())))
				&& ((this.getEmployerId() == castOther.getEmployerId()) || (this.getEmployerId() != null && castOther.getEmployerId() != null && this.getEmployerId().equals(castOther.getEmployerId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getHistoryId() == null ? 0 : this.getHistoryId().hashCode());
		result = 37 * result + (getEmployerId() == null ? 0 : this.getEmployerId().hashCode());
		return result;
	}

}