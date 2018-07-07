package com.bridgelabz.springbootrestweb.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootrestweb.model.User;
import com.bridgelabz.springbootrestweb.repositories.UserRepository;
import com.bridgelabz.springbootrestweb.repositories.UserRepositoryImplementation;

@Service
public class UserServiceImplementation {
	
	@Autowired
	UserRepository userRepository;

	public int verifyUser(User user) throws ClassNotFoundException, SQLException {
		User userReturn = userRepository.getUserByUserName(user.getUserName());
		if (userReturn != null) {
			if (userReturn.getPassword().equals(user.getPassword())) {
				System.out.println("pass match");
				return 1;
			} else {
				return 0;
			}
		}
		return -1;

	}

	
	public  boolean verifyEmail(User user) throws ClassNotFoundException, SQLException {
		if(userRepository.getUserByUserEmail(user.getEmail())) {
			return true;
		}
		return false;
		}


	public void saveUser(User user) throws ClassNotFoundException, SQLException {
		userRepository.saveUser(user);
		
	}

}
