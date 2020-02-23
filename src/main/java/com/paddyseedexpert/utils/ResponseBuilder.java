package com.paddyseedexpert.utils;

import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR;
import static com.paddyseedexpert.userprofile.constant.AppConstants.SUCCESS;
import static com.paddyseedexpert.utils.DataUtils.getTimestamp;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
	
	private Map<String, Object> requestMap;
	
	public ResponseBuilder() {
		this.requestMap = new HashMap<>(3);
		addTimestamp();
	}
	
	public ResponseBuilder addTimestamp() {
		this.requestMap.put("timestamp", getTimestamp());
		return this;
	}
	
	public ResponseBuilder addCustomProperty(String propertyName, String propertyValue) {
		this.requestMap.put(propertyName, propertyValue);
		return this;
	}
	
	public ResponseBuilder addMessage(String message) {
		this.requestMap.put("message", message);
		if(this.requestMap.containsKey("status")){
			return this;
		}
		return message.startsWith("Error:") ? addStatus(ERROR) : addStatus(SUCCESS);
	}
	
	public ResponseBuilder addStatus(String status) {
		this.requestMap.put("status", status);
		return this;
	}
	
	public ResponseBuilder addStatusCode(int statusCode) {
		this.requestMap.put("statusCode", statusCode);
		return this;
	}
	
	public ResponseBuilder path(String path) {
		this.requestMap.put("path", path);
		return this;
	}
	
	public Map<String, Object> build() {
		return this.requestMap;
	}
}