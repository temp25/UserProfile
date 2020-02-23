package com.paddyseedexpert.userprofile.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.paddyseedexpert.userprofile.model.User;
import com.paddyseedexpert.userprofile.service.UserService;
import com.paddyseedexpert.utils.ResponseBuilder;

import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_ROOT;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_STATUS;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_CREATE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_UPDATE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_FETCH;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_DELETE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_AUTHENTICATE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_GET;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_RESET_PASSWORD_REST;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_FORGOT_CREDENTIALS_REST;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_FORGOT_CREDENTIALS_WEB;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_RESET_PASSWORD_WEB;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_RESET_STATUS;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_SWAGGER;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESOURCE_API;
import static com.paddyseedexpert.userprofile.constant.AppConstants.SUCCESS_STATUS_CODE;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR_STATUS_CODE;
import static com.paddyseedexpert.userprofile.mapper.ExceptionToStatusCodeMapper.getStatusCode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.util.FileCopyUtils.copyToByteArray;

@Controller
@RequestMapping(RESOURCE_ROOT)
public class UserProfileController {
	
	private UserService userService;
	
	@Autowired
	public UserProfileController(final UserService userService) {
		this.userService = userService;
	}
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
	
	@RequestMapping(value = RESOURCE_STATUS, method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> status() {
		return getRequest()
				.addStatusCode(SUCCESS_STATUS_CODE)
				.addMessage("UP")
				.path(RESOURCE_ROOT + RESOURCE_STATUS)
				.build();
	}
	
	@RequestMapping(value = RESOURCE_CREATE, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		try{
			String userId = userService.createUser(user, accessToken);
			message = "User created with id, "+userId;
			LOGGER.info(message);
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE);
		}catch(RuntimeException e){
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in saving user. Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_CREATE)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_UPDATE, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		try {
			String userId = userService.updateUser(user, accessToken);
			message = "User with id, "+userId+" updated successfully";
			LOGGER.info(message);
			responseBuilder = responseBuilder
									.addStatusCode(SUCCESS_STATUS_CODE);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in updating user. Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
									.addStatus(getStatusCode(e))
									.addStatusCode(ERROR_STATUS_CODE);
		}
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_UPDATE)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_FETCH, method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getUsers(@RequestHeader("X-Access-Token") String accessToken) {
		
		String users = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			users = userService.fetchUsers(accessToken);
			LOGGER.info("Users fetched successfully");
			responseBuilder  = responseBuilder
										.addCustomProperty("users", users)
										.addStatusCode(SUCCESS_STATUS_CODE);
		}catch(RuntimeException | JsonProcessingException e) {
			users = "Error: "+e.getMessage();
			LOGGER.error("Error in fetching users. Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addMessage(users)
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		
		
		return responseBuilder
						.path(RESOURCE_ROOT + RESOURCE_FETCH)
						.build();
		
	}
	
	@RequestMapping(value = RESOURCE_DELETE, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> deleteUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			String userId = userService.deleteUser(user, accessToken);
			message = "User with id, "+userId+" deleted successfully";
			LOGGER.info(message);
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in deleting user. Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_DELETE)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_AUTHENTICATE, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> authenticateUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			message = userService.authenticateUser(user, accessToken);
			LOGGER.info(message);
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE);
		}catch(RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in authenticating user. Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_AUTHENTICATE)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_GET, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> checkUser(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			String userJson = userService.getUser(user, accessToken);
			message = "User with id, "+user.getId()+" fetched successfully";
			LOGGER.info(message);
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE)
										.addCustomProperty("user", userJson);
		}catch(RuntimeException | JsonProcessingException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in fetching user with id, "+user.getId()+". Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_GET)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_RESET_PASSWORD_REST, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			message = userService.resetPasswordRest(user, accessToken);
			LOGGER.info(message+"  for user" + user.getUserName());
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE);
		} catch (JsonProcessingException | RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in resetting password for the user with id, "+user.getId()+". Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_RESET_PASSWORD_REST)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_FORGOT_CREDENTIALS_REST, method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> forgotCredentials(@RequestHeader("X-Access-Token") String accessToken, @RequestBody User user) {
		
		String message = "";
		ResponseBuilder responseBuilder = getRequest();
		
		try {
			message = userService.forgotUsernameOrPasswordRest(user, accessToken);
			LOGGER.info(message+"  for user" + user.getUserName());
			responseBuilder = responseBuilder
										.addStatusCode(SUCCESS_STATUS_CODE);
		} catch (JsonProcessingException | RuntimeException e) {
			message = "Error: "+e.getMessage();
			LOGGER.error("Error in retrieving credentials for the user with id, "+user.getId()+". Error: "+e.getMessage(), e);
			responseBuilder = responseBuilder
										.addStatus(getStatusCode(e))
										.addStatusCode(ERROR_STATUS_CODE);
		}
		return responseBuilder
						.addMessage(message)
						.path(RESOURCE_ROOT + RESOURCE_FORGOT_CREDENTIALS_REST)
						.build();
	}
	
	@RequestMapping(value = RESOURCE_FORGOT_CREDENTIALS_WEB, method = GET, produces = TEXT_HTML_VALUE)
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
	
	@RequestMapping(value = RESOURCE_RESET_PASSWORD_WEB, method = GET, produces = TEXT_HTML_VALUE)
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
	
	@RequestMapping(value = RESOURCE_RESET_STATUS, method = POST, produces = TEXT_HTML_VALUE)
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
	
	@RequestMapping(value = RESOURCE_SWAGGER, method = GET, produces = TEXT_HTML_VALUE)
	public String swaggerDoc() {
		return "swagger/swagger-ui";
	}
	
	@RequestMapping(value = RESOURCE_API, method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object swaggerApi() {
		
		try {
			ClassPathResource classPathResource = new ClassPathResource("classpath:static/swagger/swagger.json");
			byte[] byteArraySwaggerFileContent = copyToByteArray(classPathResource.getInputStream());
			String swaggerFileContent = new String(byteArraySwaggerFileContent, UTF_8);
			return new ObjectMapper().readTree(swaggerFileContent.substring(1));
		} catch (IOException e) {
			StringWriter stackTraceWriter=new StringWriter();
			e.printStackTrace(new PrintWriter(stackTraceWriter));
			return ImmutableMap.of(
					"isError", "true",
					"errorMessage", e.getMessage(),
					"stackTrace", stackTraceWriter.toString()
			);
			
		}
		
	}

	private ResponseBuilder getRequest() {
		return new ResponseBuilder();
	}
	
}
