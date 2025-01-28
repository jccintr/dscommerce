package com.jcsoftware.dscommerce.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jcsoftware.dscommerce.services.exceptions.IntegrityViolationException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class IntegrityViolationHandler {

	@ExceptionHandler(IntegrityViolationException.class)
	public ResponseEntity<StandardError> integrityViolation(IntegrityViolationException e, HttpServletRequest request) {

		String error = "Data Integrity Violation";
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}
