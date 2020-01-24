package com.paddyseedexpert.userprofile.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.paddyseedexpert.userprofile.model.User;

public interface UserService {

	public String createUser(User user, String accessToken) throws RuntimeException;
	public String updateUser(User user, String accessToken) throws RuntimeException;
	public String fetchUsers(String accessToken) throws RuntimeException, JsonProcessingException;
	public String deleteUser(User user, String accessToken) throws RuntimeException;
	public String authenticateUser(User user, String accessToken) throws RuntimeException;
	public String resetPasswordRest(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException;
	public String forgotUsernameOrPasswordRest(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException;
	public String getUser(User user, String accessToken) throws RuntimeException, JsonProcessingException;
	public User forgotPasswordWeb(String userName, String resetPass) throws RuntimeException;
	public User resetPasswordWeb(String userName, String resetPass) throws RuntimeException;
	public int updatePassword(UUID userId, String password, String confirmPassword) throws RuntimeException;
	
}
