package com.product.hub.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.hub.apiresponse.ApiResponse;
import com.product.hub.apiresponse.ErrorResponse;



@RestControllerAdvice
public class CustomExceptionAdvice {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<?> productNotPresent(ProductNotFoundException e){
		
		
		ApiResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, LocalDateTime.now(),"Product not Found", e.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
		
		
		
		
		
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> productNotPresent(UserNotFoundException e){
		
		
		ApiResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), false, LocalDateTime.now(),"User not Found", e.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
		
		
		
		
		
	}
	
	   @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<?> handleDataIntegrityViolation(
	            DataIntegrityViolationException ex) {

	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(Map.of(
	                    "status", 409,
	                    "error", "Conflict",
	                    "message", "Username already exists"
	                ));
	        
	     
	    }
	

}
