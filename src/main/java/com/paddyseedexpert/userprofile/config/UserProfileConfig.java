package com.paddyseedexpert.userprofile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class UserProfileConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**", "/js/**", "/image/**")
		.addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/static/image/");
		
		registry.addResourceHandler("/swagger/css/**", "/swagger/js/**", "/swagger/image/**", "/swagger/**")
		.addResourceLocations("classpath:/static/swagger/css/", "classpath:/static/swagger/js/", "classpath:/static/swagger/image/", "classpath:/static/swagger/");
	}
	
}
