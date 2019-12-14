package com.paddyseedexpert.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paddyseedexpert.userprofile.model.User;

public class DataUtils {

	public static String getJSONString(Iterable<User> users) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(users);
	}

	public static boolean isBlank(String str) {
		return str.trim().isEmpty();
	}

	

}
