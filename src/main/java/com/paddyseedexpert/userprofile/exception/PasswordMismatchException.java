package com.paddyseedexpert.userprofile.exception;

public class PasswordMismatchException extends RuntimeException {
	
	private static final long serialVersionUID = 1832575026847371499L;

	public PasswordMismatchException() { }

	public PasswordMismatchException(String errorMessage) {
		super(errorMessage);
	}
}
