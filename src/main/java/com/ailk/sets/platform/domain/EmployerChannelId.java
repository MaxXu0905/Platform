package com.ailk.sets.platform.domain;

/**
 * EmployerChannelId entity. @author MyEclipse Persistence Tools
 */

public class EmployerChannelId implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6390673628282057542L;
	private String employerAcct;
	private Integer channel;

	// Constructors

	/** default constructor */
	public EmployerChannelId() {
	}

	/** full constructor */
	public EmployerChannelId(String employerAcct, Integer channel) {
		this.employerAcct = employerAcct;
		this.channel = channel;
	}

	// Property accessors

	public String getEmployerAcct() {
		return this.employerAcct;
	}

	public void setEmployerAcct(String employerAcct) {
		this.employerAcct = employerAcct;
	}

	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployerChannelId))
			return false;
		EmployerChannelId castOther = (EmployerChannelId) other;

		return ((this.getEmployerAcct() == castOther.getEmployerAcct()) || (this.getEmployerAcct() != null
				&& castOther.getEmployerAcct() != null && this.getEmployerAcct().equals(castOther.getEmployerAcct())))
				&& ((this.getChannel() == castOther.getChannel()) || (this.getChannel() != null
						&& castOther.getChannel() != null && this.getChannel().equals(castOther.getChannel())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEmployerAcct() == null ? 0 : this.getEmployerAcct().hashCode());
		result = 37 * result + (getChannel() == null ? 0 : this.getChannel().hashCode());
		return result;
	}

}