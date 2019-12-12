package com.paddyseedexpert.userprofile.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paddyseedexpert.userprofile.exception.AlreadyRegisteredException;
import com.paddyseedexpert.userprofile.exception.InvalidAccessTokenException;
import com.paddyseedexpert.userprofile.exception.PasswordMismatchException;
import com.paddyseedexpert.userprofile.exception.UserExistException;
import com.paddyseedexpert.userprofile.exception.UserNotExistException;
import com.paddyseedexpert.userprofile.model.User;
import com.paddyseedexpert.userprofile.repository.UserRepository;
import com.paddyseedexpert.utils.DataUtils;

import static com.paddyseedexpert.userprofile.constant.AppConstants.CREATE_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.UPDATE_ACCESS_TOKEN;
import static com.paddyseedexpert.userprofile.constant.AppConstants.FETCH_ACCESS_TOKEN;

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
		
		return userRepository.save(user).getId().toString();
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
		
		return DataUtils.getJSONString(users);
	}

}
