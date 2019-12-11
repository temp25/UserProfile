package com.paddyseedexpert.userprofile.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paddyseedexpert.utils.RequestUtils;

@RestController
@RequestMapping("/userProfileController")
public class UserProfileController {
	
	@RequestMapping("/status")
	public Map<String, String> status() {
		return getRequest().addStatus("UP").build();
	}

	private RequestUtils getRequest() {
		return new RequestUtils();
	}
	
}
