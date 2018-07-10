package com.bridgelabz.springsecurityjwt.services;

import java.sql.SQLException;

import com.bridgelabz.springsecurityjwt.models.User;
public interface UserService {
	public int loginUser(User user) throws ClassNotFoundException, SQLException;
	public  boolean verifyEmail(User user) throws ClassNotFoundException, SQLException;
	public void saveUser(User user) throws ClassNotFoundException, SQLException;
}
