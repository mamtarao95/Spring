package com.bridgelabz.springbootrestweb.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bridgelabz.springbootrestweb.model.User;

public interface UserRepository {
	/**
	 * Method to establish and return the connection
	 * 
	 * @return connection JDBC Connection
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws ClassNotFoundException;

	/**
	 * Method to close all the JDBC connection
	 * 
	 * @param resultSet
	 *            ResultSet
	 * @param preparedStatement
	 *            PreparedStatement
	 * @param connection
	 *            Connection
	 */
	public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection);

	/**
	 * Method to verify whether a user is present in database or not
	 * 
	 * @param uname
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User getUserByUserName(String uname) throws ClassNotFoundException, SQLException;

	/**
	 * Function to save user in the database
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean saveUser(User user) throws SQLException, ClassNotFoundException;

	public boolean getUserByUserEmail(String email) throws SQLException, ClassNotFoundException;
}
