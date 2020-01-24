package com.paddyseedexpert.userprofile.service;

import static com.paddyseedexpert.userprofile.constant.AppConstants.AUTH_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.CREATE_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.DELETE_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.ERROR;
import static com.paddyseedexpert.userprofile.constant.AppConstants.FETCH_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESET_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RESET_PASSWORD_COLUMN_NAME;
import static com.paddyseedexpert.userprofile.constant.AppConstants.RETRIEVE_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.TABLE_NAME;
import static com.paddyseedexpert.userprofile.constant.AppConstants.UPDATE_ACCESS_TOKEN;
import static com.paddyseedexpert.utils.AuthenticationUtils.decrypt;
import static com.paddyseedexpert.utils.AuthenticationUtils.encrypt;
import static com.paddyseedexpert.utils.DataUtils.getJSONString;
import static com.paddyseedexpert.utils.DataUtils.getMailerErrorMessage;
import static com.paddyseedexpert.utils.DataUtils.isBlank;
import static com.paddyseedexpert.utils.DataUtils.stringToJSON;
import static com.paddyseedexpert.utils.MailUtils.sendForgotPasswordMail;
import static com.paddyseedexpert.utils.MailUtils.sendForgotUsernameMail;
import static com.paddyseedexpert.utils.MailUtils.sendResetPasswordMail;
import static org.springframework.util.StringUtils.hasText;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.paddyseedexpert.userprofile.exception.AlreadyRegisteredException;
import com.paddyseedexpert.userprofile.exception.AuthenticationFailureException;
import com.paddyseedexpert.userprofile.exception.InvalidAccessTokenException;
import com.paddyseedexpert.userprofile.exception.InvalidRequestParamException;
import com.paddyseedexpert.userprofile.exception.MailSendException;
import com.paddyseedexpert.userprofile.exception.MissingRequestParamException;
import com.paddyseedexpert.userprofile.exception.PasswordEncryptionException;
import com.paddyseedexpert.userprofile.exception.PasswordMismatchException;
import com.paddyseedexpert.userprofile.exception.UserExistException;
import com.paddyseedexpert.userprofile.exception.UserNotExistException;
import com.paddyseedexpert.userprofile.exception.UserSecurityException;
import com.paddyseedexpert.userprofile.model.User;
import com.paddyseedexpert.userprofile.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public String createUser(User user, String accessToken) throws RuntimeException {
		
		String emailAddress = user.getEmailAddress();
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		String userName = user.getUserName();
		String secureQuestion = user.getSecureQuestion();
		String secureAnswer = user.getSecureAnswer();
		Long emailCount = userRepository.countByEmailAddress(emailAddress);
		Long userCount = userRepository.countByUserName(userName);
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Create access token missing in request header");
		}
		
		if(!accessToken.equals(CREATE_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid create access token entered");
		}
		
		if(userCount != null && userCount > 0) {
			throw new UserExistException("User already exists for userName, "+userName);
		}
		
		if(emailCount != null && emailCount > 0) {
			throw new AlreadyRegisteredException("Email id, "+emailAddress+" is already registered");
		}
		
		if(!password.equals(confirmPassword)) {
			throw new PasswordMismatchException("Password and Confirm passwords given don't match");
		}
		
		if(!hasText(secureQuestion) || !hasText(secureAnswer)) {
			throw new UserSecurityException("Secure question/answer not set");
		}
		
		user.setResetPassword(getResetPassword());
		
		return userRepository.save(user).getId().toString();
	}

	private String getResetPassword() {
		return userRepository.GenerateUniqueValue(TABLE_NAME, RESET_PASSWORD_COLUMN_NAME);
	}

	@Override
	public String updateUser(User user, String accessToken) throws RuntimeException {
		
		String userName = user.getUserName();
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		Optional<User> oldUserOptional = userRepository.findByUserName(userName);
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Create access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(UPDATE_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid update access token entered");
		}
		
		if(!oldUserOptional.isPresent()) {
			throw new UserNotExistException("User not exists for userName, "+userName);
		}
		
		if(!password.equals(confirmPassword)) {
			throw new PasswordMismatchException("Password and Confirm passwords given don't match");
		}
		
		User oldUser = oldUserOptional.get();
		
		oldUser.setPassword(password);
		oldUser.setConfirmPassword(confirmPassword);
		
		oldUser = userRepository.save(oldUser);
		
		return oldUser.getId().toString();
	}

	@Override
	public String fetchUsers(String accessToken) throws RuntimeException, JsonProcessingException {
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Fetch access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(FETCH_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid fetch access token entered");
		}
		
		Iterable<User> users = userRepository.findAll();
		
		return getJSONString(users);
	}

	@Override
	public String deleteUser(User user, String accessToken) throws RuntimeException {
		
		
		Optional<User> optionalUser = null;
		String id = "";
		String userName = user.getUserName();
		String emailAddress = user.getEmailAddress();
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Delete access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(DELETE_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid delete access token entered");
		}
		
		if(userName == null && emailAddress == null) {
			throw new MissingRequestParamException("Username (or) Email address missing in request parameter for delete operation");
		}
		
		if(!userName.isEmpty()) {
			if(isBlank(userName)){
				throw new InvalidRequestParamException("Username request parameter specfied has only whitespace");
			}else{
				optionalUser = userRepository.findByUserName(userName);
				if(optionalUser.isPresent()){
					user = optionalUser.get();
				}else{
					throw new UserNotExistException("User doesn't exists for the username, "+userName);
				}
			}
		}else if(!emailAddress.isEmpty()) {
			if(isBlank(emailAddress)){
				throw new InvalidRequestParamException("Email address request parameter specfied has only whitespace");
			}else{
				optionalUser = userRepository.findByEmailAddress(emailAddress);
				if(optionalUser.isPresent()){
					user = optionalUser.get();
				}else{
					throw new UserNotExistException("User doesn't exists for the email address, "+emailAddress);
				}
			}
		}else{
			throw new InvalidRequestParamException("Username/Email address request parameter specfied has only whitespace");
		}
		
		id = user.getId().toString();
		
		userRepository.delete(user);
		
		return id;
	}

	@Override
	public String authenticateUser(User user, String accessToken) throws RuntimeException {
		
		String userName = user.getUserName();
		String password = user.getPassword();
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Check access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(AUTH_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid check access token entered");
		}
		
		if(userName == null || password == null) {
			throw new MissingRequestParamException("Username (or) Password missing in request parameter for check operation");
		}
		
		if(isBlank(userName) || isBlank(password)) {
			throw new InvalidRequestParamException("Username (or) Password request parameter specfied is blank (or) has only whitespace");
		}
		
		Optional<User> optionalUser = userRepository.findByUserName(userName);
		
		if(optionalUser.isPresent()){
			User fetchedUser = optionalUser.get();
			if(fetchedUser.getPassword().equals(user.getPassword())){
				return fetchedUser.getId().toString();
			}else{
				throw new AuthenticationFailureException("Username (or) Password specified is invalid");
			}
		}else{
			throw new AuthenticationFailureException("Username (or) Password specified is invalid");
		}
	}

	@Override
	public String getUser(User user, String accessToken) throws RuntimeException, JsonProcessingException {
		
		UUID id = user.getId();
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Auth access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(FETCH_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid auth access token entered");
		}
		
		if(id == null) {
			throw new MissingRequestParamException("Id missing in request parameter for get operation");
		}
		
		if(isBlank(id.toString())) {
			throw new InvalidRequestParamException("Id request parameter specfied is blank (or) has only whitespace");
		}
		
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(optionalUser.isPresent()){
			return getJSONString(optionalUser.get());
		}else{
			throw new UserNotExistException("Couldn't fetch user with id, "+id.toString()); 
		}
		
	}

	@Override
	public String resetPasswordRest(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException {
		
		UUID id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Check access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(RESET_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid reset access token entered");
		}
		
		if(id == null) {
			throw new MissingRequestParamException("Id missing in request parameter for get operation");
		}
		
		if(isBlank(id.toString())) {
			throw new InvalidRequestParamException("Id request parameter specfied is blank (or) has only whitespace");
		}
		
		if(userName == null || password == null) {
			throw new MissingRequestParamException("Username (or) Password missing in request parameter for check operation");
		}
		
		if(isBlank(userName) || isBlank(password)) {
			throw new InvalidRequestParamException("Username (or) Password request parameter specfied is blank (or) has only whitespace");
		}
		
		try {
			password = encrypt(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			String errorMessage = "Error occurred in encrypting password";
			LOGGER.error(errorMessage, e);
			throw new PasswordEncryptionException(errorMessage);
		}
		
		Optional<User> optionalUser = userRepository.findByIdAndUserNameAndPassword(id, userName, password);
		
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
			String mailResponse = sendResetPasswordMail(user.getEmailAddress(), user.getUserName(), user.getResetPassword());
			JsonNode response = stringToJSON(mailResponse);
			
			if(!mailResponse.contains(ERROR)) {
				return "Password reset successful...";
			} else {
				throw new MailSendException(getMailerErrorMessage(response));
			}
			
		}else {
			throw new AuthenticationFailureException("Couldn't fetch User with given Username and Password");
		}
		
	}

	@Override
	public String forgotUsernameOrPasswordRest(User user, String accessToken) throws RuntimeException, JsonMappingException, JsonProcessingException {
		
		UUID id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		
		if(accessToken==null) {
			throw new InvalidAccessTokenException("Check access token missing in request header");
		}
		
		if(accessToken != null && !accessToken.equals(RETRIEVE_ACCESS_TOKEN)) {
			throw new InvalidAccessTokenException("Invalid retrieve access token entered");
		}
		
		if(id == null) {
			throw new MissingRequestParamException("Id missing in request parameter for retrieve operation");
		}
		
		if(userName == null || isBlank(userName)) {
			Optional<User> optionalUser = userRepository.findById(id);
			
			if(optionalUser.isPresent()) {
				
				user = optionalUser.get();
				
				String mailResponse = sendForgotUsernameMail(user.getEmailAddress(), user.getUserName());
				
				JsonNode response = stringToJSON(mailResponse);
				
				if(!mailResponse.contains(ERROR)) {
					return "Username retrieval successful...";
				} else {
					throw new MailSendException(getMailerErrorMessage(response));
				}
				
			}else {
				throw new UserNotExistException("Couldn't fetch user for the id, "+id.toString());
			}
			
		} else if(password == null || isBlank(password)) {

			Optional<User> optionalUser = userRepository.findByIdAndUserName(id, userName);
			
			if(optionalUser.isPresent()) {
				
				user = optionalUser.get();
				String mailResponse = sendForgotPasswordMail(user.getEmailAddress(), userName, user.getResetPassword());
				
				JsonNode response = stringToJSON(mailResponse);
				
				if(!mailResponse.contains(ERROR)) {
					return "Password retrieval successful...";
				} else {
					throw new MailSendException(getMailerErrorMessage(response));
				}
				
			}else {
				throw new UserNotExistException("Couldn't fetch user for the id, "+id.toString());
			}
			
		}else {
			return "";
		}
		
	}

	@Override
	public User forgotPasswordWeb(String userName, String resetPassword) throws RuntimeException{
		Optional<User> user = userRepository.findByUserNameAndResetPassword(userName, resetPassword);
		return user.orElseThrow(UserNotExistException::new);
	}

	@Transactional
	@Override
	public int updatePassword(UUID userId, String password, String confirmPassword) {
		
		String encryptedPassword;
		String encryptedConfirmPassword;
		
		try {
			encryptedPassword = encrypt(password);
			encryptedConfirmPassword = encrypt(confirmPassword);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return userRepository.updatePasswords(userId, encryptedPassword, encryptedConfirmPassword, getResetPassword());
	}

	@Override
	public User resetPasswordWeb(String userName, String resetPassword) throws RuntimeException {
		
		User user = null;
		
		try {
			Optional<User> optionalUser = userRepository.findByUserNameAndResetPassword(userName, resetPassword);
			user = optionalUser.orElseThrow(UserNotExistException::new);
			user.setPassword(decrypt(user.getPassword()));
		} catch(RuntimeException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e){
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return user;
	}	

}
