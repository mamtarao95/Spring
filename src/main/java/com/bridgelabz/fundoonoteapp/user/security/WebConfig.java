package com.bridgelabz.fundoonoteapp.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.bridgelabz.fundoonoteapp.user.interceptor.LoggerInterceptor;
import com.bridgelabz.fundoonoteapp.user.interceptor.RequestProcessingTimeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new RequestProcessingTimeInterceptor()).addPathPatterns("/**");

	}
}
