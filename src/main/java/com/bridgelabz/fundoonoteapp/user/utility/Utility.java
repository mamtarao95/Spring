package com.bridgelabz.fundoonoteapp.user.utility;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.bridgelabz.fundoonoteapp.user.exceptions.RegisterationException;
import com.bridgelabz.fundoonoteapp.user.models.RegistrationDTO;
import com.bridgelabz.fundoonoteapp.user.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utility {
	
	private Utility() {}
	
	final static String KEY = "mamta";

	public static void validateUserInformation(RegistrationDTO registrationDTO) throws RegisterationException {
		
		if (!validateEmail(registrationDTO.getEmail())) {
			throw new RegisterationException("Invalid Email-id");
		}
		if (!validatePassword(registrationDTO.getPassword()) || registrationDTO.getPassword() == "") {
			throw new RegisterationException(
					"(1)-Password must be must be atleast 8 characters long" + "(2)- Must have numbers and letters"
					+ "(3)- Must have at least a one special characters- “!,@,#,$,%,&,*,(,),+");
		}
		if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
			throw new RegisterationException("PASSWORD and CONFIRM PASSWORD fields are not matching!!");
		}
		if (registrationDTO.getMobileNumber().length() != 10 || registrationDTO.getMobileNumber() == "") {
			throw new RegisterationException("Mobile number should be 10 digits long");
		}

	}

	public static boolean validatePassword(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return password.matches(pattern);
	}

	public static boolean validateEmail(String email) {
		String pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
		return email.matches(pattern);
	}

	

	public static String tokenGenerator(User user) {
		String email = user.getEmail();
		long timeMillis = System.currentTimeMillis() + (20 * 60 * 60 * 1000);
		Date date = new Date(timeMillis);
		JwtBuilder builder = Jwts.builder().setId(email).setIssuedAt(date).setSubject(email)
				.signWith(SignatureAlgorithm.HS256, KEY);
		return builder.compact();
	}

	
	public static Claims parseJWT(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwt)
				.getBody();
		
		System.out.println("The details of claims: ");
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		return claims;
	}
}
