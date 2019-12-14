package com.paddyseedexpert.userprofile.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paddyseedexpert.userprofile.exception.AlreadyRegisteredException;
import com.paddyseedexpert.userprofile.exception.InvalidAccessTokenException;
import com.paddyseedexpert.userprofile.exception.InvalidRequestParamException;
import com.paddyseedexpert.userprofile.exception.MissingRequestParamException;
import com.paddyseedexpert.userprofile.exception.PasswordMismatchException;
import com.paddyseedexpert.userprofile.exception.UserExistException;
import com.paddyseedexpert.userprofile.exception.UserNotExistException;
import com.paddyseedexpert.userprofile.model.User;
import com.paddyseedexpert.userprofile.service.UserService;
import com.paddyseedexpert.utils.RequestUtils;

@RestController
@RequestMapping("/userProfileController")
public class UserProfileController {
	
	@Autowired
	UserService userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
	
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> status() {
		return getRequest().addStatus("UP").build();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> createUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try{
			String userId = userService.createUser(user, accessToken);
			message = "User created with id, "+userId;
			LOGGER.info(message);
		}catch(PasswordMismatchException |AlreadyRegisteredException | UserExistException | InvalidAccessTokenException e){
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in saving user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> updateUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			String userId = userService.updateUser(user, accessToken);
			message = "User with id, "+userId+" updated successfully";
			LOGGER.info(message);
		}catch(UserNotExistException | PasswordMismatchException | InvalidAccessTokenException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in updating user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getUsers(@RequestHeader("X-Access-Token") String accessToken) {
		
		String users = "";
		try {
			users = userService.fetchUsers(accessToken);
			LOGGER.info("Users fetched successfully");
			return getRequest().addCustomProperty("users", users).build();
		}catch(InvalidAccessTokenException | JsonProcessingException e) {
			users = "Error: "+e.getMessage();
			LOGGER.error("Error in fetching users. Error: "+e.getMessage(), e);
			return getRequest().addMessage(users).build();
		}
			
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> deleteUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			String userId = userService.deleteUser(user, accessToken);
			message = "User with id, "+userId+" deleted successfully";
			LOGGER.info(message);
		}catch(InvalidAccessTokenException | MissingRequestParamException | InvalidRequestParamException | UserNotExistException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in deleting user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}

	private RequestUtils getRequest() {
		return new RequestUtils();
	}
	
}
