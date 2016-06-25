package com.ailk.sets.platform.intf.model.qb;

import com.ailk.sets.platform.intf.common.PFResponse;

public class QbBaseResponse extends PFResponse {

	private static final long serialVersionUID = 7238890852316430753L;
	private Integer qbId;

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

}
