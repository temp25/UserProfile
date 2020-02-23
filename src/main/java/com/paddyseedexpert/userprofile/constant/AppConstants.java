package com.paddyseedexpert.userprofile.constant;

import java.util.Calendar;

public final class AppConstants {

	private AppConstants() { }
	
	//Rest end-point resources
	public static final String RESOURCE_ROOT = "/userProfileController";
	public static final String RESOURCE_STATUS = "/status";
	public static final String RESOURCE_CREATE = "/create";
	public static final String RESOURCE_UPDATE = "/update";
	public static final String RESOURCE_FETCH = "/fetch";
	public static final String RESOURCE_DELETE = "/delete";
	public static final String RESOURCE_AUTHENTICATE = "/auth";
	public static final String RESOURCE_GET = "/get";
	public static final String RESOURCE_RESET_PASSWORD_REST = "/resetPassword";
	public static final String RESOURCE_FORGOT_CREDENTIALS_REST = "/forgotCredentials";
	public static final String RESOURCE_FORGOT_CREDENTIALS_WEB = "/forgot_password";
	public static final String RESOURCE_RESET_PASSWORD_WEB = "/reset_password";
	public static final String RESOURCE_RESET_STATUS = "/reset_status";
	public static final String RESOURCE_SWAGGER = "/swagger";
	public static final String RESOURCE_API = "/api";
	
	Calendar c = Calendar.getInstance();
	
	//Custom Error Codes
	private static final String ERR_CODE_PREFIX = "pse-error-";
	public static final String ERR_ACCESS_TOKEN		=	ERR_CODE_PREFIX + "670";
	public static final String ERR_AUTH_FAILURE		=	ERR_CODE_PREFIX + "671";
	public static final String ERR_REGISTRATION		=	ERR_CODE_PREFIX + "672";
	public static final String ERR_USER_EXISTS		=	ERR_CODE_PREFIX + "403";
	public static final String ERR_USER_NOT_EXISTS	=	ERR_CODE_PREFIX + "404";
	public static final String ERR_USER_SECURITY	=	ERR_CODE_PREFIX + "405";
	public static final String ERR_USER_DETAILS		=	ERR_CODE_PREFIX + "406";
	public static final String ERR_JSON_PARSE		=	ERR_CODE_PREFIX + "767";
	public static final String ERR_JSON_MAP			=	ERR_CODE_PREFIX + "768";
	public static final String ERR_PARAM_MISSING	=	ERR_CODE_PREFIX + "353";
	public static final String ERR_PARAM_INVALID	=	ERR_CODE_PREFIX + "354";
	public static final String ERR_MAIL_SEND		=	ERR_CODE_PREFIX + "505";
	public static final String ERR_PWD_ENCRYPT		=	ERR_CODE_PREFIX + "750";
	public static final String ERR_PWD_DECRYPT		=	ERR_CODE_PREFIX + "751";
	public static final String ERR_PWD_MISMATCH		=	ERR_CODE_PREFIX + "753";
	public static final String ERR_GENERIC			=	ERR_CODE_PREFIX + "501";
	
	//Access Tokens to interact with API
	public static final String CREATE_ACCESS_TOKEN    =  System.getenv("CREATE_ACCESS_TOKEN");
	public static final String UPDATE_ACCESS_TOKEN    =  System.getenv("UPDATE_ACCESS_TOKEN");
	public static final String FETCH_ACCESS_TOKEN     =  System.getenv("FETCH_ACCESS_TOKEN");
	public static final String DELETE_ACCESS_TOKEN    =  System.getenv("DELETE_ACCESS_TOKEN");
	public static final String AUTH_ACCESS_TOKEN      =  System.getenv("AUTH_ACCESS_TOKEN");
	public static final String RESET_ACCESS_TOKEN     =  System.getenv("RESET_ACCESS_TOKEN");
	public static final String RETRIEVE_ACCESS_TOKEN  =  System.getenv("RETRIEVE_ACCESS_TOKEN");
	
	public static final String FROM_ADDRESS = "no-reply@cocypher.com";
	public static final String ERROR = "error";
	public static final String ERROR_CODE = "errorCode";
	public static final String SUCCESS = "success";
	public static final int SUCCESS_STATUS_CODE = 200;
	public static final int ERROR_STATUS_CODE = 501;
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String TABLE_NAME = "user_profile";
	public static final String RESET_PASSWORD_COLUMN_NAME = "reset_password";
	public static final String APP_URL = System.getenv("APP_URL");
	
	public static final String TURBO_SMTP_AUTH_USERNAME = System.getenv("TURBO_SMTP_AUTH_USERNAME");
	public static final String TURBO_SMTP_AUTH_PASSWORD = System.getenv("TURBO_SMTP_AUTH_PASSWORD");
	
	public static final String ENCRYPTION_DECRYPTION_ALGORITHM = "AES";
	public static final String SECRET_KEY = System.getenv("SECRET_KEY"); //128-bit
	
}
