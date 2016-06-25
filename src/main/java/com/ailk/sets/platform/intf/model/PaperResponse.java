package com.ailk.sets.platform.intf.model;

import com.ailk.sets.platform.intf.common.PFResponse;

public class PaperResponse extends PFResponse {
	private static final long serialVersionUID = -970155499581468073L;
	private Integer paperId;
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
}
