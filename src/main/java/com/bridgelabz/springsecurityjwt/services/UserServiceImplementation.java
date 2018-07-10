package com.bridgelabz.springsecurityjwt.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bridgelabz.springsecurityjwt.models.User;
import com.bridgelabz.springsecurityjwt.repositories.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository userRepository;

	public int loginUser(User user) throws ClassNotFoundException, SQLException {
		Optional<User> userReturn;
	
			System.out.println("user found");
			userReturn = userRepository.findByUserName(user.getUserName());
			
		
		if (userReturn.isPresent()) {
			if (userReturn.get().getPassword().equals(user.getPassword())) {
				System.out.println("pass match");
				return 1;
			} else {
				return 0;
			}
		}
		return -1;

	}

	
	public boolean verifyEmail(User user) throws ClassNotFoundException, SQLException {
	if (userRepository.findByEmail(user.getEmail()).isPresent()) {
		return true;
	}
		return false;
	}

	
	public void saveUser(User user) throws ClassNotFoundException, SQLException {
		userRepository.save(user);
	}

}
