package com.bridgelabz.fundoonoteapp.user.services;

import java.util.Optional;
import javax.security.auth.login.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonoteapp.user.exceptions.ForgotPasswordException;
import com.bridgelabz.fundoonoteapp.user.exceptions.RegisterationException;
import com.bridgelabz.fundoonoteapp.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonoteapp.user.models.EmailDTO;
import com.bridgelabz.fundoonoteapp.user.models.LoginDTO;
import com.bridgelabz.fundoonoteapp.user.models.RegistrationDTO;
import com.bridgelabz.fundoonoteapp.user.models.SetPasswordDTO;
import com.bridgelabz.fundoonoteapp.user.models.User;
import com.bridgelabz.fundoonoteapp.user.repositories.UserRepository;
import com.bridgelabz.fundoonoteapp.user.utility.Utility;
import io.jsonwebtoken.Claims;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String loginUser(LoginDTO loginDTO) throws LoginException, UserActivationException {

		Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
		if (!optionalUser.isPresent()) {
			throw new LoginException("User is not present");
		}
		if (!optionalUser.get().isActivated()) {
			throw new UserActivationException("User is not activated");
		}
		if (!passwordEncoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
			throw new LoginException("Password is incorrect");
		}
		return Utility.tokenGenerator(optionalUser.get().getId());
	}

	@Override
	public void registerUser(RegistrationDTO registrationDTO) throws Exception {
		Utility.validateUserInformation(registrationDTO);
		Optional<User> optionalUser = userRepository.findByEmail(registrationDTO.getEmail());
		if (optionalUser.isPresent()) {
			System.out.println("user present");
			throw new RegisterationException("User with same email-id already exists!!");
		}

		User user = new User();
		user.setFirstName(registrationDTO.getFirstName());
		user.setLastName(registrationDTO.getLastName());
		user.setEmail(registrationDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		user.setMobileNumber(registrationDTO.getMobileNumber());
		userRepository.save(user);
		Optional<User> optionalUser1 = userRepository.findByEmail(user.getEmail());

		String token = Utility.tokenGenerator(optionalUser1.get().getId());
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setMessage(
				"To activate your account, click the following link : http://localhost:8080/user/activateaccount?token="
						+ token);
		emailDTO.setSubject("Confirm registeration.");
		emailDTO.setTo(user.getEmail());
		emailService.sendActivationEmail(emailDTO);
	}

	@Override
	public void activateAccount(String token) throws UserActivationException {
		if (Utility.isTokenExpired(token)) {
			throw new UserActivationException("Token is expired and is no longer valid");
		}
		Claims claims = Utility.parseJWT(token);
		Optional<User> optionalUser = userRepository.findById(claims.getId());
		if (!optionalUser.isPresent()) {
			throw new UserActivationException("Activation failed since user is not registered!!");
		}
		User user = optionalUser.get();
		user.setActivated(true);
		userRepository.save(user);
		System.out.println("user set to true");

	}

	@Override
	public void forgotPassword(String email) throws Exception {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			String token = Utility.tokenGenerator(optionalUser.get().getId());
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setTo(email);
			emailDTO.setSubject("Forgot Password Link");
			emailDTO.setMessage(
					"To reset your password,click the following link : http://localhost:8080/user/setpassword?token="
							+ token);
			emailService.sendActivationEmail(emailDTO);
		}

	}

	@Override
	public void setPassword(SetPasswordDTO setPasswordDTO, String token) throws ForgotPasswordException {
		if (!setPasswordDTO.getConfirmPassword().equals(setPasswordDTO.getNewPassword())) {
			throw new ForgotPasswordException("New password and confirm password does not matches!!");
		}
		Claims claims = Utility.parseJWT(token);
		Optional<User> optionalUser = userRepository.findById(claims.getId());
		if (!optionalUser.isPresent()) {
			throw new ForgotPasswordException("Email id doesn't exists");
		}
		User user = optionalUser.get();
		user.setPassword(passwordEncoder.encode(setPasswordDTO.getNewPassword()));
		userRepository.save(user);

	}

}
