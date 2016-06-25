package com.ailk.sets.grade.intf;


@SuppressWarnings("serial")
public class HasErrorQbResponse extends BaseResponse {

	private boolean empty;

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
