package com.ailk.sets.platform.domain;

/**
 * EmployerAuthorizationId entity. @author MyEclipse Persistence Tools
 */

public class EmployerAuthorizationId implements java.io.Serializable {

	private static final long serialVersionUID = -4066824180931720195L;
	private Integer employerId;
	private Integer employerGranted;
    private Integer positionGranted;
    
    
	public Integer getPositionGranted() {
		return positionGranted;
	}

	public void setPositionGranted(Integer positionGranted) {
		this.positionGranted = positionGranted;
	}

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Integer getEmployerGranted() {
		return this.employerGranted;
	}

	public void setEmployerGranted(Integer employerGranted) {
		this.employerGranted = employerGranted;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployerAuthorizationId))
			return false;
		EmployerAuthorizationId castOther = (EmployerAuthorizationId) other;

		return ((this.getEmployerId() == castOther.getEmployerId()) || (this.getEmployerId() != null && castOther.getEmployerId() != null && this.getEmployerId().equals(castOther.getEmployerId())))
				&& ((this.getEmployerGranted() == castOther.getEmployerGranted()) || (this.getEmployerGranted() != null && castOther.getEmployerGranted() != null && this.getEmployerGranted().equals(
						castOther.getEmployerGranted())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEmployerId() == null ? 0 : this.getEmployerId().hashCode());
		result = 37 * result + (getEmployerGranted() == null ? 0 : this.getEmployerGranted().hashCode());
		return result;
	}

}