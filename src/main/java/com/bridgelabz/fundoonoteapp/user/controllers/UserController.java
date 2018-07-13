package com.bridgelabz.fundoonoteapp.user.controllers;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonoteapp.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonoteapp.user.models.LoginDTO;
import com.bridgelabz.fundoonoteapp.user.models.RegistrationDTO;
import com.bridgelabz.fundoonoteapp.user.models.Response;
import com.bridgelabz.fundoonoteapp.user.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse res)
			throws LoginException {
		String token = userService.loginUser(loginDTO);
		Response responseDTO = new Response();
		responseDTO.setMessage("Login Successfull!!");
		responseDTO.setStatus(2);
		res.setHeader("jwt", token);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)

	public ResponseEntity<Response> registerUser(@RequestBody RegistrationDTO registrationDto) throws Exception {
		System.out.println("into reg");
		userService.registerUser(registrationDto);
		Response responseDTO = new Response();
		responseDTO.setMessage("Registeration Successfull!!");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/activateaccount", method = RequestMethod.GET)
	public ResponseEntity<Response> activateAccount(@RequestParam("token") String token)
			throws UserActivationException {
		System.out.println("into activation controller");
		System.out.println("token: " + token);
		userService.activateAccount(token);
		Response responseDTO = new Response();
		responseDTO.setMessage("account activated Successfull!!");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
	}

}
