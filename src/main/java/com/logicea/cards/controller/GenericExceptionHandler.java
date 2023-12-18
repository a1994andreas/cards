package com.logicea.cards.controller;

import com.logicea.cards.exception.CardNotFoundException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log
public class GenericExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String handleBadRequestError(BadCredentialsException ex){
		return "Invalid Credentials";
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleBadRequestError(CardNotFoundException ex){
		return "Card not found for user";
	}

	/*
	* Convert validation errors to proper error messages
	* */
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationError(MethodArgumentNotValidException ex){
		log.warning(ex.getMessage());
		Map<String, String> validationErrors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			validationErrors.put(fieldName, errorMessage);
		});
		return validationErrors;
	}

}
