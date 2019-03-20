package com.sys4u.company.exception;

public class DataDuplicatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataDuplicatedException() {
		super();
	}

	public DataDuplicatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataDuplicatedException(String message) {
		super(message);
	}

	public DataDuplicatedException(Throwable cause) {
		super(cause);
	}
}
