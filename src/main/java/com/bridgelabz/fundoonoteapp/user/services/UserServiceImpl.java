package com.bridgelabz.fundoonoteapp.user.services;

import java.util.Optional;
import javax.security.auth.login.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonoteapp.user.exceptions.RegisterationException;
import com.bridgelabz.fundoonoteapp.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonoteapp.user.models.LoginDTO;
import com.bridgelabz.fundoonoteapp.user.models.RegistrationDTO;
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

	public String loginUser(LoginDTO loginDTO) throws LoginException {
		Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
		if (!optionalUser.isPresent()) {
			throw new LoginException("User is not present");
		}

		if (!passwordEncoder.matches(loginDTO.getPassword(),optionalUser.get().getPassword())) {
			throw new LoginException("Password is incorrect");
		}
		User user=new User();
		user.setEmail(loginDTO.getEmail());
		return Utility.tokenGenerator(user);
	}

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
		emailService.sendActivationEmail(user);

	}
	
	public void activateAccount(String token) throws UserActivationException {
		Claims claims =Utility.parseJWT(token) ;
		Optional<User> optionalUser = userRepository.findByEmail(claims.getId());
		if(!optionalUser.isPresent()) {
			throw new UserActivationException("Activation failed since user is not registered!!");
		}
		User user=optionalUser.get();
		user.setActivated(true);
		userRepository.save(user);
		System.out.println("user set to true");
	

}
}
