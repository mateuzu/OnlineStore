package com.org.onlinestore.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserPostRequest(
		@NotBlank String username, 
		@NotBlank @Email String email, 
		@Min(value = 5) String password) {

}
