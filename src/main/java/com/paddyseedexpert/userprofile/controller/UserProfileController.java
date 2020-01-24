package com.paddyseedexpert.userprofile.controller;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.paddyseedexpert.userprofile.model.User;
import com.paddyseedexpert.userprofile.service.UserService;
import com.paddyseedexpert.utils.RequestUtils;

@Controller
@RequestMapping("/userProfileController")
public class UserProfileController {
	
	@Autowired
	UserService userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
	
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> status() {
		return getRequest().addStatus("UP").build();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> createUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try{
			String userId = userService.createUser(user, accessToken);
			message = "User created with id, "+userId;
			LOGGER.info(message);
		}catch(RuntimeException e){
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in saving user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> updateUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			String userId = userService.updateUser(user, accessToken);
			message = "User with id, "+userId+" updated successfully";
			LOGGER.info(message);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in updating user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> getUsers(@RequestHeader("X-Access-Token") String accessToken) {
		
		String users = "";
		try {
			users = userService.fetchUsers(accessToken);
			LOGGER.info("Users fetched successfully");
			return getRequest().addCustomProperty("users", users).build();
		}catch(RuntimeException | JsonProcessingException e) {
			users = "Error: "+e.getMessage();
			LOGGER.error("Error in fetching users. Error: "+e.getMessage(), e);
			return getRequest().addMessage(users).build();
		}
			
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> deleteUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			String userId = userService.deleteUser(user, accessToken);
			message = "User with id, "+userId+" deleted successfully";
			LOGGER.info(message);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in deleting user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> authenticateUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			message = userService.authenticateUser(user, accessToken);
			LOGGER.info(message);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in authenticating user. Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> checkUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			String userJson = userService.getUser(user, accessToken);
			message = "User with id, "+user.getId()+" fetched successfully";
			LOGGER.info(message);
			return getRequest().addMessage(message).addCustomProperty("user", userJson).build();
		}catch(RuntimeException | JsonProcessingException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in fetching user with id, "+user.getId()+". Error: "+e.getMessage(), e);
			return getRequest().addMessage(message).build();
		}
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> resetPassword(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			message = userService.resetPasswordRest(user, accessToken);
			LOGGER.info(message+"  for user" + user.getUserName());
		} catch (JsonProcessingException | RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in resetting password for the user with id, "+user.getId()+". Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/forgotCredentials", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> forgotCredentials(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		try {
			message = userService.forgotUsernameOrPasswordRest(user, accessToken);
			LOGGER.info(message+"  for user" + user.getUserName());
		} catch (JsonProcessingException | RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in retrieving credentials for the user with id, "+user.getId()+". Error: "+e.getMessage(), e);
		}
		return getRequest().addMessage(message).build();
	}
	
	@RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
	public String forgotPassword(Model model, @RequestParam("userId") String userId, @RequestParam("resetPass") String resetPass) {
		try{
			User user = userService.forgotPasswordWeb(userId, resetPass);
			model.addAttribute("user", user);
			model.addAttribute("isResetPasswordScenario", false);
			model.addAttribute("isError", false);
		}catch(RuntimeException e){
			LOGGER.error("Error in fetching forgot password link", e);
			model.addAttribute("isError", true);
		}
		return "forgot_or_reset_password";
	}
	
	@RequestMapping(value = "/reset_password", method = RequestMethod.GET)
	public String resetPassword(Model model, @RequestParam("userId") String userId, @RequestParam("resetPass") String resetPass) {
		try{
			User user = userService.resetPasswordWeb(userId, resetPass);
			model.addAttribute("user", user);
			model.addAttribute("isResetPasswordScenario", true);
			model.addAttribute("isError", false);
		}catch(RuntimeException e){
			LOGGER.error("Error in fetching reset password link", e);
			model.addAttribute("isError", true);
		}
		return "forgot_or_reset_password";
	}
	
	@RequestMapping(value = "/reset_status", method = RequestMethod.POST)
	public String updatePassword(Model model, @ModelAttribute(value="user") User user) {
		
		try {
			UUID userId = user.getId();
			int updateCount = userService.updatePassword(userId, user.getPassword(), user.getConfirmPassword());
			
			if(updateCount > 0){
				model.addAttribute("resetStatus", "Password reset successful");
				LOGGER.info("Password updated for user with id, "+userId);
			}else{
				throw new RuntimeException("Error in updating passwords for user with id, "+userId);
			}
			
		} catch (RuntimeException e) {
			model.addAttribute("resetStatus", "Password reset failed. Error: "+e.getMessage());
			LOGGER.error("Error occurred : "+e.getMessage(), e);
		}
		
		return "reset_status";
	}

	private RequestUtils getRequest() {
		return new RequestUtils();
	}
	
}
