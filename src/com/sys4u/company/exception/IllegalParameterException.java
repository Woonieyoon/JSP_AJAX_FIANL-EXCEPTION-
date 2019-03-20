package com.sys4u.company.exception;

public class IllegalParameterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8707297970025402812L;

	public IllegalParameterException() {
		super();
	}

	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(Throwable cause) {
		super(cause);
	}

}
