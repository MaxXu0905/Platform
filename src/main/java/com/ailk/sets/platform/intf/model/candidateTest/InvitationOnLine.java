package com.ailk.sets.platform.intf.model.candidateTest;

import java.io.Serializable;

import com.ailk.sets.platform.intf.common.PFResponse;
public class InvitationOnLine extends PFResponse implements Serializable {
	private static final long serialVersionUID = -8584564188474794186L;
	private String invitationUrl;
	public String getInvitationUrl() {
		return invitationUrl;
	}
	public void setInvitationUrl(String invitationUrl) {
		this.invitationUrl = invitationUrl;
	}
}
