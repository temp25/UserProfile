package com.paddyseedexpert.userprofile.exception;

public class MailSendException extends RuntimeException {

	private static final long serialVersionUID = 2926552917746534751L;

	public MailSendException() { }

	public MailSendException(String errorMessage) {
		super(errorMessage);
	}

}
