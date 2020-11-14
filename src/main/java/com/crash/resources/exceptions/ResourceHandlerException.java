package com.crash.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crash.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceHandlerException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> notFound (ResourceNotFoundException ex, HttpServletRequest request){
		StandardError err = new StandardError
				(Instant.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Not Found", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
