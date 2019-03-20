package com.sys4u.company.exception;

public class FailToCloseResourceException extends RuntimeException{

	private static final long serialVersionUID = -2410510357878445572L;

	public FailToCloseResourceException() {
		super();
	}

	public FailToCloseResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailToCloseResourceException(String message) {
		super(message);
	}

	public FailToCloseResourceException(Throwable cause) {
		super(cause);
	}

	
}
