package com.org.onlinestore.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UserPostRequest(
		@NotBlank String username, 
		@NotBlank @Email String email, 
		String password,
		@Positive @Max(value = 2L, message = "Não é possível criar um usuário com essas permissões!") Long userType) {

}
