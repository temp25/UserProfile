package com.paddyseedexpert.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

	private static final String TIMESTAMP_FORMAT = "HH:mm:ss.SSS dd-MM-yyyy zzz";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	private Map<String, String> requestMap;
	
	public RequestUtils() {
		this.requestMap = new HashMap<>(3);
		addTimestamp();
	}
	
	public RequestUtils addTimestamp() {
		this.requestMap.put("timestamp", new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date()));
		return this;
	}
	
	public RequestUtils addMessage(String message) {
		this.requestMap.put("message", message);
		return message.startsWith("Error:") ? isSuccess(ERROR) : isSuccess(SUCCESS);
	}
	
	public RequestUtils addStatus(String status) {
		this.requestMap.put("status", status);
		return this;
	}
	
	public RequestUtils isSuccess(String isSuccess) {
		this.requestMap.put("isSuccess", isSuccess);
		return this;
	}
	
	public Map<String, String> build() {
		return this.requestMap;
	}
}