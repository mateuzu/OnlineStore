package com.org.onlinestore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.onlinestore.controller.dto.UserPostRequest;
import com.org.onlinestore.model.User;
import com.org.onlinestore.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<Void> postUser(@RequestBody @Valid UserPostRequest userPostRequest){
		var userId = userService.postUser(userPostRequest);
		return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
	}
	
	@GetMapping("/get/{userId}")
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
		var user = userService.getUserById(userId);
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	public ResponseEntity<List<User>> getAllUsers(){
		var list = userService.getAllUsers();
		return ResponseEntity.ok().body(list);
	}
}
