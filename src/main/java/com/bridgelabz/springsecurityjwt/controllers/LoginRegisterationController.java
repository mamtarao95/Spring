package com.bridgelabz.springsecurityjwt.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springsecurityjwt.configuration.JwtSecurityConfiguration;
import com.bridgelabz.springsecurityjwt.models.User;
import com.bridgelabz.springsecurityjwt.services.UserServiceImplementation;
import com.bridgelabz.springsecurityjwt.utility.CustomErrorType;

@RestController
public class LoginRegisterationController {

	@Autowired
	UserServiceImplementation userServiceImplementation;

	@Autowired
	JwtSecurityConfiguration jwtSecurityConfiguration;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		System.out.println(user.getUserName() + "iii");
		if (userServiceImplementation.loginUser(user) == 1) {
			String token = jwtSecurityConfiguration.tokenGenerator(user);
			jwtSecurityConfiguration.parseJWT(token);
			return new ResponseEntity("Welcome " + user.getUserName(), HttpStatus.OK);
		
		}
		if (userServiceImplementation.loginUser(user) == 0) {
			return new ResponseEntity(new CustomErrorType("Password is incorrect"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity(new CustomErrorType("Username doesnt not exist"), HttpStatus.CONFLICT);
		}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		if (!userServiceImplementation.verifyEmail(user)) {
			userServiceImplementation.saveUser(user);
			String token = jwtSecurityConfiguration.tokenGenerator(user);
			jwtSecurityConfiguration.parseJWT(token);
			return new ResponseEntity("User successfully registered", HttpStatus.OK);
			
		}
		return new ResponseEntity(new CustomErrorType("Email-id already exist!!"), HttpStatus.CONFLICT);
	}

}
