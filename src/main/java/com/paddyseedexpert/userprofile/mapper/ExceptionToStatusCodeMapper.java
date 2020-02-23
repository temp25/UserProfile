package com.paddyseedexpert.userprofile.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.paddyseedexpert.userprofile.exception.AlreadyRegisteredException;
import com.paddyseedexpert.userprofile.exception.AuthenticationFailureException;
import com.paddyseedexpert.userprofile.exception.InvalidAccessTokenException;
import com.paddyseedexpert.userprofile.exception.InvalidRequestParamException;
import com.paddyseedexpert.userprofile.exception.MailSendException;
import com.paddyseedexpert.userprofile.exception.MissingRequestParamException;
import com.paddyseedexpert.userprofile.exception.MissingUserDetailsException;
import com.paddyseedexpert.userprofile.exception.PasswordDecryptionException;
import com.paddyseedexpert.userprofile.exception.PasswordEncryptionException;
import com.paddyseedexpert.userprofile.exception.PasswordMismatchException;
import com.paddyseedexpert.userprofile.exception.UserExistException;
import com.paddyseedexpert.userprofile.exception.UserNotExistException;
import com.paddyseedexpert.userprofile.exception.UserSecurityException;

import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_USER_EXISTS;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_REGISTRATION;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_PWD_MISMATCH;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_USER_SECURITY;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_USER_NOT_EXISTS;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_JSON_PARSE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_JSON_MAP;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_PARAM_MISSING;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_PARAM_INVALID;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_AUTH_FAILURE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_MAIL_SEND;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_PWD_ENCRYPT;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_PWD_DECRYPT;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_USER_DETAILS;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERR_GENERIC;

public final class ExceptionToStatusCodeMapper {
	
	private ExceptionToStatusCodeMapper() { }
	
	public static String getStatusCode(Exception e) {
		
		if(e instanceof InvalidAccessTokenException) {
			return ERR_ACCESS_TOKEN;
		} else if (e instanceof UserExistException) {
			return ERR_USER_EXISTS;
		} else if (e instanceof AlreadyRegisteredException) {
			return ERR_REGISTRATION;
		}else if (e instanceof PasswordMismatchException) {
			return ERR_PWD_MISMATCH;
		} else if (e instanceof UserSecurityException) {
			return ERR_USER_SECURITY;
		} else if(e instanceof UserNotExistException) {
			return ERR_USER_NOT_EXISTS;
		} else if (e instanceof JsonProcessingException) {
			return ERR_JSON_PARSE;
		} else if (e instanceof JsonMappingException) {
			return ERR_JSON_MAP;
		} else if (e instanceof MissingRequestParamException) {
			return ERR_PARAM_MISSING;
		} else if (e instanceof InvalidRequestParamException) {
			return ERR_PARAM_INVALID;
		} else if (e instanceof AuthenticationFailureException) {
			return ERR_AUTH_FAILURE;
		} else if (e instanceof MailSendException) {
			return ERR_MAIL_SEND;
		} else if (e instanceof PasswordEncryptionException) {
			return ERR_PWD_ENCRYPT;
		} else if (e instanceof PasswordDecryptionException) {
			return ERR_PWD_DECRYPT;
		} else if (e instanceof MissingUserDetailsException) {
			return ERR_USER_DETAILS;
		}
		
		return ERR_GENERIC;
	}
	
}
