package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.util.List;

public class InvitationOutInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781621607118202867L;

	private List<InvitationOutEmployer> employers;
	private List<InvitationOutCandidate> candidateList;

	private String realPositionName;
	private String emailtitle;
	private String emailContent;
	private Integer employerId;
	private int testWithoutCamera;

	public int getTestWithoutCamera() {
		return testWithoutCamera;
	}

	public void setTestWithoutCamera(int testWithoutCamera) {
		this.testWithoutCamera = testWithoutCamera;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getRealPositionName() {
		return realPositionName;
	}

	public void setRealPositionName(String realPositionName) {
		this.realPositionName = realPositionName;
	}

	public String getEmailtitle() {
		return emailtitle;
	}

	public void setEmailtitle(String emailtitle) {
		this.emailtitle = emailtitle;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public List<InvitationOutEmployer> getEmployers() {
		return employers;
	}

	public void setEmployers(List<InvitationOutEmployer> employers) {
		this.employers = employers;
	}

	public List<InvitationOutCandidate> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<InvitationOutCandidate> candidateList) {
		this.candidateList = candidateList;
	}

	@Override
	public String toString() {
		return "InvitationOutInfo [employers=" + employers + ", candidateList=" + candidateList + ", realPositionName="
				+ realPositionName + ", emailtitle=" + emailtitle + ", emailContent=" + emailContent + ", employerId="
				+ employerId + ", testWithoutCamera=" + testWithoutCamera + "]";
	}

	
}
