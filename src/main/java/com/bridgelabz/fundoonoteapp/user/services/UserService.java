package com.bridgelabz.fundoonoteapp.user.services;


import javax.security.auth.login.LoginException;

import com.bridgelabz.fundoonoteapp.user.exceptions.RegisterationException;
import com.bridgelabz.fundoonoteapp.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonoteapp.user.models.LoginDTO;
import com.bridgelabz.fundoonoteapp.user.models.RegistrationDTO;

public interface UserService {
	public void registerUser(RegistrationDTO registrationDTO)throws  RegisterationException, Exception;

	public String loginUser(LoginDTO loginDTO) throws LoginException;

	public void activateAccount(String token) throws UserActivationException;
}
