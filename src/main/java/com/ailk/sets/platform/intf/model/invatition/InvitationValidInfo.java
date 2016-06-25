package com.ailk.sets.platform.intf.model.invatition;

import java.io.Serializable;
import java.util.Date;

public class InvitationValidInfo implements Serializable {
	private Long testId;
	private String candidateName;
	private String candidateEmail;
	private Date invitationDate;
	private String invitationDateDesc;
	private Integer invitationState;

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

	public String getInvitationDateDesc() {
		return invitationDateDesc;
	}

	public void setInvitationDateDesc(String invitationDateDesc) {
		this.invitationDateDesc = invitationDateDesc;
	}

	public Integer getInvitationState() {
		return invitationState;
	}

	public void setInvitationState(Integer invitationState) {
		this.invitationState = invitationState;
	}
}
