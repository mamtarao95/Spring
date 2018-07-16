package com.bridgelabz.fundoonoteapp.user.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bridgelabz.fundoonoteapp.user.interceptor.LoggerInterceptor;
import com.bridgelabz.fundoonoteapp.user.interceptor.RequestProcessingTimeInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.bridgelabz.fundoonoteapp.user.controllers" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
		registry.addInterceptor(new RequestProcessingTimeInterceptor());

	}
}
