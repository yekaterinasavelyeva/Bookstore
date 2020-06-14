package com.bookstore.config;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bookstore.api.ApiError;
import com.bookstore.exceptions.NotFoundException;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String VALIDATION_ERROR = "Validation errors occurred";

	@ExceptionHandler(InvalidPropertyException.class)
	public final ResponseEntity<Object> handleInvalidPropertyException(InvalidPropertyException ex) {
		log.info(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, ex.getLocalizedMessage(), "Couldn't process the request");
		return getObjectResponseEntity(apiError);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex) {
		log.info(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Information not found");
		return getObjectResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), VALIDATION_ERROR);
		return getObjectResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), VALIDATION_ERROR);
		return getObjectResponseEntity(apiError);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolations(final ConstraintViolationException ex) {
		log.info(ex.getMessage());
		final Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
		String message = set.stream().map(c -> c.getMessageTemplate().concat(", " + c.getPropertyPath())).collect(Collectors.joining("; "));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, VALIDATION_ERROR);
		return getObjectResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleConflict(MethodArgumentTypeMismatchException ex) {
		log.info(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), VALIDATION_ERROR);
		return getObjectResponseEntity(apiError);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAll(Exception ex) {
		log.info(ex.getMessage());
		String message = ex.getMessage();
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, "error occurred");
		return getObjectResponseEntity(apiError);
	}

	private ResponseEntity<Object> getObjectResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
