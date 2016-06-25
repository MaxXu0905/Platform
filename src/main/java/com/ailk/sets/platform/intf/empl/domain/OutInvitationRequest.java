package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

public class OutInvitationRequest implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -263275097706386205L;
	private String outId; //外部调用的id
    private String candidateName;
	private String candidateEmail;
	private Integer paperId; //试卷id  本版本指测评id
	private String redirectUrl;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
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
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	@Override
	public String toString() {
		return "OutInvitationRequest [outId=" + outId + ", candidateName=" + candidateName + ", candidateEmail="
				+ candidateEmail + ", paperId=" + paperId + ", redirectUrl=" + redirectUrl + "]";
	}
	
	

}
