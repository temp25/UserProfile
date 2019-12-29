package com.paddyseedexpert.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paddyseedexpert.userprofile.model.User;

import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR_CODE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.TIMESTAMP_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

	public static String getJSONString(Iterable<User> users) throws JsonProcessingException {
		return getJSON(users);
	}

	public static boolean isBlank(String str) {
		return str.trim().isEmpty();
	}
	

	public static String getJSONString(User user) throws JsonProcessingException {
		return getJSON(user);
	}

	private static String getJSON(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(object);
	}

	public static JsonNode stringToJSON(String mailResponse) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(mailResponse);
	}
	
	public static String getMailerErrorMessage(JsonNode response) {
		String errorMsg = response.get(ERROR).asText();
		String errorCode = response.get(ERROR_CODE).asText();
		return "Error occurred in sending mail. "+ERROR+": "+errorMsg+" "+ERROR_CODE+": "+errorCode;
	}

	public static String prepareEmailBody(String mesage) {

		StringBuilder emailBody = new StringBuilder();
		
		emailBody.append("<html>");
		emailBody.append("<br/>");
		emailBody.append("<body>");
		emailBody.append("<br/>");
		emailBody.append(mesage);
		emailBody.append("<br/>");
		emailBody.append("</body>");
		emailBody.append("<br/>");
		emailBody.append("</html>");
		
		return emailBody.toString();
	}

	public static String getTimestamp() {
		return new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
	}

}
