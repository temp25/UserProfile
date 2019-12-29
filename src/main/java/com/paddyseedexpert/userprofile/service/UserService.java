package com.paddyseedexpert.userprofile.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.paddyseedexpert.userprofile.model.User;

public interface UserService {

	public String createUser(User user, String accessToken) throws RuntimeException;
	public String updateUser(User user, String accessToken) throws RuntimeException;
	public String fetchUsers(String accessToken) throws RuntimeException, JsonProcessingException;
	public String deleteUser(User user, String accessToken) throws RuntimeException;
	public String authenticateUser(User user, String accessToken) throws RuntimeException;
	public String resetPassword(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException;
	public String forgotUsernameOrPassword(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException;
	public String getUser(User user, String accessToken) throws RuntimeException, JsonProcessingException;
	
}
