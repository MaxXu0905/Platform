package com.ailk.sets.platform.intf.model.position;

import java.io.Serializable;

import com.ailk.sets.platform.intf.common.PFResponse;

public class PosResponse extends PFResponse implements Serializable {

	private static final long serialVersionUID = -5878638964017380843L;

	private Integer positionId;

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

}
