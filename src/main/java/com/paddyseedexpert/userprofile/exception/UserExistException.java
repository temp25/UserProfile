package com.paddyseedexpert.userprofile.exception;

public class UserExistException extends RuntimeException {
	
	private static final long serialVersionUID = 3219654377320195970L;

	public UserExistException() { }

	public UserExistException(String arg0) {
		super(arg0);
	}
}
