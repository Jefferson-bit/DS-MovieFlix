package com.crash.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crash.services.exceptions.ForbiddenException;
import com.crash.services.exceptions.ResourceNotFoundException;
import com.crash.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceHandlerException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> notFound (ResourceNotFoundException ex, HttpServletRequest request){
		StandardError err = new StandardError
				(Instant.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Not Found", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OauthException> unauthorized (UnauthorizedException ex, HttpServletRequest request){
		OauthException oauth = new OauthException("Unauthorized", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(oauth);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OauthException> forbidden (ForbiddenException ex, HttpServletRequest request){
		OauthException oauth = new OauthException("Access  denied", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(oauth);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidatorError> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidatorError err = new ValidatorError(
			Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), "UNPROCESSABLE_ENTITY",  request.getRequestURI());
		
		for(FieldError f: e.getBindingResult().getFieldErrors()) {
			err.getError(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
}













