package com.paddyseedexpert.userprofile.exception;

public class PasswordEncryptionException extends RuntimeException {

	private static final long serialVersionUID = -957703397600255940L;

	public PasswordEncryptionException() { }
	
	public PasswordEncryptionException(String errorMessage) {
		super(errorMessage);
	}
	
}
