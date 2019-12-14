package com.paddyseedexpert.userprofile.exception;

public class AlreadyRegisteredException extends RuntimeException {
	
	private static final long serialVersionUID = 8741794878374491444L;

	public AlreadyRegisteredException() { }

	public AlreadyRegisteredException(String errorMessage) {
		super(errorMessage);
	}
	
}
