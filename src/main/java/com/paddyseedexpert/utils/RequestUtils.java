package com.paddyseedexpert.utils;

import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR;
import static com.paddyseedexpert.userprofile.constant.AppConstants.SUCCESS;
import static com.paddyseedexpert.utils.DataUtils.getTimestamp;

import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
	
	private Map<String, String> requestMap;
	
	public RequestUtils() {
		this.requestMap = new HashMap<>(3);
		addTimestamp();
	}
	
	public RequestUtils addTimestamp() {
		this.requestMap.put("timestamp", getTimestamp());
		return this;
	}
	
	public RequestUtils addCustomProperty(String propertyName, String propertyValue) {
		this.requestMap.put(propertyName, propertyValue);
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