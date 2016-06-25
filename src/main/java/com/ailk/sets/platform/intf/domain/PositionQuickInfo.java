package com.ailk.sets.platform.intf.domain;

import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;

public class PositionQuickInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7105495971847125244L;
	private List<Integer> paperIds;
	private List<EmployerAuthorizationIntf> employerAuths;// 授权信息

	public List<Integer> getPaperIds() {
		return paperIds;
	}

	public void setPaperIds(List<Integer> paperIds) {
		this.paperIds = paperIds;
	}

	public List<EmployerAuthorizationIntf> getEmployerAuths() {
		return employerAuths;
	}

	public void setEmployerAuths(List<EmployerAuthorizationIntf> employerAuths) {
		this.employerAuths = employerAuths;
	}

	@Override
	public String toString() {
		return "PositionQuickInfo [paperIds=" + paperIds + ", employerAuths=" + employerAuths + "]";
	}

}