package com.ailk.sets.platform.intf.common;

import java.io.Serializable;

public class OutResponse implements Serializable {

	private static final long serialVersionUID = 345740484421665891L;

	private int status;
	private String message;


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String toString() {
		return "{\"status\":" + status + ",\"message\":" + message + "}";
	}
}
