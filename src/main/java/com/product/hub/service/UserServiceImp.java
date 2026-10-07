package com.product.hub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.product.hub.dtos.request.UserDto;
import com.product.hub.dtos.response.UserDtoResponse;
import com.product.hub.entity.User;
import com.product.hub.enums.Roles;
import com.product.hub.exception.UserNotFoundException;
import com.product.hub.repository.UserRepository;



@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encode;
	
	@Override
	public UserDtoResponse add(UserDto user) {
		// TODO Auto-generated method stub
		
		User user1= new User();
		
		BeanUtils.copyProperties(user, user1);
		
		user1.setRole(Roles.USER);
		
		user1.setPassword(encode.encode(user1.getPassword()));
		
	  user1= userRepo.save(user1);
	  
	  UserDtoResponse response= new UserDtoResponse();
	  
	  BeanUtils.copyProperties(user1, response);
		
		
		return response;
	}

	@Override
	public UserDtoResponse viewUser(String userName) {
		
		User user= userRepo.findByUserName(userName);
		
		
		if(user ==null) {
			
			throw new UserNotFoundException("User not there with user name: "+userName);
		}
		
		
		UserDtoResponse response= new UserDtoResponse();
		
		BeanUtils.copyProperties(user, response);
		
		
		return response;
	}

	@Override
	public UserDtoResponse updateUser(Long id, UserDto request) {
		User user=	userRepo.findById(id).orElseThrow(()->new  UserNotFoundException("user not found"));

	    
		BeanUtils.copyProperties(request, user);
		
		userRepo.save(user);
		
		
		UserDtoResponse response= new UserDtoResponse();
		
		BeanUtils.copyProperties(user, response);
		
		return response;
		
		
		
	}

	@Override
	public String deleteUser(Long id) {
		User user=	userRepo.findById(id).orElseThrow(()->new  UserNotFoundException("user not found"));
		
		userRepo.deleteById(id);
			
			return "user deleted by id: "+id;
	}

	@Override
	public List<UserDtoResponse> viewUsers() {
		
		List<User> users= userRepo.findAll();
		
		
		List<UserDtoResponse> reponses= new ArrayList<UserDtoResponse>();
		
		
		
		for(User u: users) {
			UserDtoResponse re= new UserDtoResponse();
			BeanUtils.copyProperties(u, re);
			
		
			reponses.add(re);
			
		}
		
		
		return reponses;
	}

	
}
