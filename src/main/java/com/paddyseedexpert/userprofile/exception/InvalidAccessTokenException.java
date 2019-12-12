package com.paddyseedexpert.userprofile.exception;

public class InvalidAccessTokenException extends RuntimeException {

	private static final long serialVersionUID = -1695713116860858463L;

	public InvalidAccessTokenException() { }

	public InvalidAccessTokenException(String errorMessage) {
		super(errorMessage);
	}

}
