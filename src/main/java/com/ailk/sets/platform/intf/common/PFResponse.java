package com.ailk.sets.platform.intf.common;

import java.io.Serializable;

public class PFResponse implements Serializable {

	private static final long serialVersionUID = 345740484421665891L;

	private String code;
	private String message;

	public PFResponse(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public PFResponse()
    {
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return "{\"code\":" + code + ",\"message\":" + message + "}";
	}
}
