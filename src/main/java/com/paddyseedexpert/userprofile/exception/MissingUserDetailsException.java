package com.paddyseedexpert.userprofile.exception;

public class MissingUserDetailsException extends RuntimeException {
	
	private static final long serialVersionUID = -6556348941191944021L;

	public MissingUserDetailsException() { }

	public MissingUserDetailsException(String message) {
		super(message);
	}
	
}
