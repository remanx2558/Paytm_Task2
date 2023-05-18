package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//The @ResponseStatus annotation ensures that the appropriate HTTP response status code (404)
// is returned when this exception is thrown, indicating to the client that the requested resource was not found.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public ResourceNotFoundException(String message){
		super(message);
		
	}
	

}
