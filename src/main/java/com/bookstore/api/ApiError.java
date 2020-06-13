package com.bookstore.api;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	private final HttpStatus status;
	private final String message;
	private final List<String> errors;

	public ApiError(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Collections.singletonList(error);
	}
}
