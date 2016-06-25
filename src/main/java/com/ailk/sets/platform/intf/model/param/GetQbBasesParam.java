package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;

import com.ailk.sets.platform.intf.model.Page;

public class GetQbBasesParam implements Serializable {

	private static final long serialVersionUID = -1645240666309337824L;

	private int employerId;
	private String qbName;
	private Page page;

	public int getEmployerId() {
		return employerId;
	}

	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
