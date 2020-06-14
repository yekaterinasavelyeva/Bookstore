package com.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class InvalidPropertyException extends RuntimeException{

	public InvalidPropertyException(String message) {
		super(message);
	}
}

