package com.org.onlinestore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.onlinestore.controller.dto.UserPostRequest;
import com.org.onlinestore.model.User;
import com.org.onlinestore.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<Void> postUser(@RequestBody UserPostRequest userPostRequest){
		var userId = userService.postUser(userPostRequest);
		return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers(){
		var list = userService.getAllUsers();
		return ResponseEntity.ok().body(list);
	}
}
