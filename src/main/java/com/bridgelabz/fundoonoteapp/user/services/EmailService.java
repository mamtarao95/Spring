package com.bridgelabz.fundoonoteapp.user.services;

import com.bridgelabz.fundoonoteapp.user.models.User;

public interface EmailService {
	void sendActivationEmail(User user) throws Exception ;
}
