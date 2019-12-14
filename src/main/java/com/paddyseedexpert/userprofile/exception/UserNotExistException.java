package com.paddyseedexpert.userprofile.exception;

public class UserNotExistException extends RuntimeException {

	private static final long serialVersionUID = -5021002113064743098L;

	public UserNotExistException() { }

	public UserNotExistException(String errorMessage) {
		super(errorMessage);
	}
}
