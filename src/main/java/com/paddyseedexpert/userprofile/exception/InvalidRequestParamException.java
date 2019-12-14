package com.paddyseedexpert.userprofile.exception;

public class InvalidRequestParamException extends RuntimeException {

	private static final long serialVersionUID = -5289758458397053941L;

	public InvalidRequestParamException() { }

	public InvalidRequestParamException(String errorMessage) {
		super(errorMessage);
	}

}
