package com.ailk.sets.platform.intf.domain;

/**
 * InvitationCopytoId entity. @author MyEclipse Persistence Tools
 */

public class InvitationCopytoId implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7207791051985083308L;
	private Long testId;
	private String email;

	// Constructors

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	/** default constructor */
	public InvitationCopytoId() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((testId == null) ? 0 : testId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvitationCopytoId other = (InvitationCopytoId) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (testId == null) {
			if (other.testId != null)
				return false;
		} else if (!testId.equals(other.testId))
			return false;
		return true;
	}

	

}