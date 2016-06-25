package com.ailk.sets.platform.intf.exception;

public class PFServiceException extends Exception {

	private static final long serialVersionUID = 8061350658855068266L;

	public PFServiceException() {
		super();
	}

	public PFServiceException(String message) {
		super(message);
	}

	public PFServiceException(Throwable cause) {
		super(cause);
	}
}
