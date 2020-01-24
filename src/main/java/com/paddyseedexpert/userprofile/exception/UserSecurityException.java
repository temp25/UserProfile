package com.paddyseedexpert.userprofile.exception;

public class UserSecurityException extends RuntimeException {

	private static final long serialVersionUID = 5906324253091491914L;

	public UserSecurityException() { }

	public UserSecurityException(String errorMessage) {
		super(errorMessage);
	}

}
