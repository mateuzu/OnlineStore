package com.org.onlinestore.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.onlinestore.controller.dto.UserPostRequest;
import com.org.onlinestore.model.User;
import com.org.onlinestore.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UUID postUser(UserPostRequest userPostRequest ) {
		var user = fromDto(userPostRequest);
		return userRepository.save(user).getUserId();
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	private User fromDto(UserPostRequest userPostRequest) {
		var user = new User(
				UUID.randomUUID(), 
				userPostRequest.username(), 
				userPostRequest.email(), 
				passwordEncoder.encode(userPostRequest.password())
			);
		return user;
	}
}
