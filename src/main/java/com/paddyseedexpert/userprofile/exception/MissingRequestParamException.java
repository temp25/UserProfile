package com.paddyseedexpert.userprofile.exception;

public class MissingRequestParamException extends RuntimeException {

	private static final long serialVersionUID = 7094981941771806934L;

	public MissingRequestParamException() { }

	public MissingRequestParamException(String errorMessage) {
		super(errorMessage);
	}

}
