package com.product.hub.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.hub.apiresponse.ApiResponse;
import com.product.hub.apiresponse.SuccessResponse;
import com.product.hub.dtos.request.UserDto;
import com.product.hub.dtos.response.UserDtoResponse;
import com.product.hub.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api-user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/me")
	public ResponseEntity<?> getMe(
	        Authentication authentication) {

	    String username = authentication.getName();

	    return ResponseEntity.ok(
	        userService.viewUser(username)
	    );
	}

	@PostMapping("/register")
	public ResponseEntity<?> add(@Valid @RequestBody UserDto user) {
		System.out.println(user);
		
		ApiResponse response= new SuccessResponse<UserDtoResponse>(HttpStatus.CREATED.value(), true, LocalDateTime.now(), "user added", userService.add(user));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	@GetMapping("/view/{userName}")
	public ResponseEntity<?> viewUser(@PathVariable String userName){
		
		
		ApiResponse response= new SuccessResponse<UserDtoResponse>(HttpStatus.OK.value(), true, LocalDateTime.now(), "user found", userService.viewUser(userName));

		return ResponseEntity.status(HttpStatus.OK).body(response);

		
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> viewUser(@PathVariable Long id){
		
		
		ApiResponse response= new SuccessResponse<String>(HttpStatus.OK.value(), true, LocalDateTime.now(), "user found", userService.deleteUser(id));

		return ResponseEntity.status(HttpStatus.OK).body(response);

		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> viewUser(@PathVariable Long id, @RequestBody UserDto request){
		
		
		ApiResponse response= new SuccessResponse<UserDtoResponse>(HttpStatus.OK.value(), true, LocalDateTime.now(), "user found", userService.updateUser(id, request));

		return ResponseEntity.status(HttpStatus.OK).body(response);

		
	}
	
	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllUser(){
		
		
		ApiResponse response= new SuccessResponse<List<UserDtoResponse>>(HttpStatus.OK.value(), true, LocalDateTime.now(), "user found", userService.viewUsers());

		return ResponseEntity.status(HttpStatus.OK).body(response);

		
	}
	
	
}
