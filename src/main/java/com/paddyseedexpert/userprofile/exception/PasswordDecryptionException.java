package com.paddyseedexpert.userprofile.exception;

public class PasswordDecryptionException extends RuntimeException {

	private static final long serialVersionUID = 4228336466352138213L;

	public PasswordDecryptionException() { }

	public PasswordDecryptionException(String errorMessage) {
		super(errorMessage);
	}

}
