package com.bridgelabz.fundoonoteapp.note.exceptionhandler;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonoteapp.note.exceptions.NoteException;
import com.bridgelabz.fundoonoteapp.user.globalexceptionhandler.GlobalExceptionHandler;
import com.bridgelabz.fundoonoteapp.user.models.Response;

@ControllerAdvice
public class NoteExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> genericExceptionhandler(HttpServletRequest request, Exception exception) {
		logger.info("Generic Exception Occured: URL=" + request.getRequestURL());
		Response response = new Response();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> noteException(NoteException exception,HttpServletRequest request) {
		logger.info("Note Exception Occured: URL=" + request.getRequestURL());
		Response response = new Response();
		response.setMessage(exception.getMessage());
		response.setStatus(-2);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
