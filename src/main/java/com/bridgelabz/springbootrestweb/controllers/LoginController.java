package com.bridgelabz.springbootrestweb.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.springbootrestweb.model.User;
import com.bridgelabz.springbootrestweb.services.UserServiceImplementation;
import com.bridgelabz.springbootrestweb.utility.CustomErrorType;

@RestController
public class LoginController {

	@Autowired
	UserServiceImplementation userServiceImplementation;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		if (userServiceImplementation.verifyUser(user) == 1) {
			return new ResponseEntity("Welcome " + user.getUserName(),HttpStatus.OK);
		}
		if (userServiceImplementation.verifyUser(user) == 0) {
			return new ResponseEntity(new CustomErrorType("Password is incorrect"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity(new CustomErrorType("Username doesnt not exist"), HttpStatus.CONFLICT);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		if (!userServiceImplementation.verifyEmail(user)) {
			userServiceImplementation.saveUser(user);
			return new ResponseEntity("User successfully registered", HttpStatus.OK);
		}
		return new ResponseEntity(new CustomErrorType("Email-id already exist!!"), HttpStatus.CONFLICT);
		}

}
