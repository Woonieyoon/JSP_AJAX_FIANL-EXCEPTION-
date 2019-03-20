package com.sys4u.company.exception;

public class NoSuchResourceException extends RuntimeException {
	private static final long serialVersionUID = 6703256960467753048L;

	public NoSuchResourceException() {
		super();
	}

	public NoSuchResourceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoSuchResourceException(String arg0) {
		super(arg0);
	}

	public NoSuchResourceException(Throwable arg0) {
		super(arg0);
	}
	
}
