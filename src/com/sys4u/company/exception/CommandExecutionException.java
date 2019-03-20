package com.sys4u.company.exception;

public class CommandExecutionException extends RuntimeException {
	private static final long serialVersionUID = 6703256960467753048L;

	public CommandExecutionException() {
		super();
	}

	public CommandExecutionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CommandExecutionException(String arg0) {
		super(arg0);
	}

	public CommandExecutionException(Throwable arg0) {
		super(arg0);
	}
	
}
