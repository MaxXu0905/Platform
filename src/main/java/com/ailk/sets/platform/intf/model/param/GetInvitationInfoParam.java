package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;
import java.util.Date;

import com.ailk.sets.platform.intf.model.Page;

public class GetInvitationInfoParam implements Serializable {

	private static final long serialVersionUID = -2068350038885850465L;

	private Integer positionId;//不能为空
	private Integer employerId;//不能为空
	private Integer invitationState;
	private Date lowerDate;
	private Page page;

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getInvitationState() {
		return invitationState;
	}

	public void setInvitationState(Integer invitationState) {
		this.invitationState = invitationState;
	}

	public Date getLowerDate() {
		return lowerDate;
	}

	public void setLowerDate(Date lowerDate) {
		this.lowerDate = lowerDate;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

}
