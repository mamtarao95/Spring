package com.bridgelabz.fundoonoteapp.user.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonoteapp.user.models.User;
import com.bridgelabz.fundoonoteapp.user.utility.Utility;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public void sendActivationEmail(User user) throws Exception {
		System.out.println("into mail sender");
		String token = Utility.tokenGenerator(user);
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setTo(user.getEmail());
			helper.setSubject("Confirm registeration.");
			helper.setText("To activate your account, click the following link : http://localhost:8080/user/activateaccount?token="+ token);

			emailSender.send(message);
		
	}

}