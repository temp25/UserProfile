package com.paddyseedexpert.userprofile.exception;

public class AuthenticationFailureException extends RuntimeException {

	private static final long serialVersionUID = -3487735097139206561L;

	public AuthenticationFailureException() { }

	public AuthenticationFailureException(String errorMessage) {
		super(errorMessage);
	}

}
