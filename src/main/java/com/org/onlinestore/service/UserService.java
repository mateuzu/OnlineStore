package com.org.onlinestore.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.org.onlinestore.controller.dto.UserPostRequest;
import com.org.onlinestore.model.User;
import com.org.onlinestore.repository.RoleRepository;
import com.org.onlinestore.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	public UUID postUser(UserPostRequest userPostRequest ) {
		var user = fromDto(userPostRequest);
		return userRepository.save(user).getUserId();
	}
	
	public User getUserById(String id) {
		var user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
		return user;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	private User fromDto(UserPostRequest userPostRequest) {	
		
		var role = roleRepository.findById(userPostRequest.userType()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inexistente"));
		
		var user = new User(
				UUID.randomUUID(),
				userPostRequest.username(), 
				userPostRequest.email(), 
				userPostRequest.password());
		
		user.setRoles(Set.of(role));
		
		return user;
	}
}
