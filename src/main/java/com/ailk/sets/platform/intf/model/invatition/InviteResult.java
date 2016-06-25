package com.ailk.sets.platform.intf.model.invatition;

import com.ailk.sets.platform.intf.common.PFResponse;

public class InviteResult extends PFResponse {

	private static final long serialVersionUID = 2643780070423021995L;
	private long testId;
    private String url;
    private String status;
	private int id;
	
	private String candidateName;
	private String candidateEmail;

	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	@Override
	public String toString() {
		return "InviteResult [testId=" + testId + ", url=" + url + ", status=" + status + "]";
	}

}
