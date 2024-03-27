package com.org.onlinestore.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryPostRequest(
		@NotBlank String name,
		@Size(max = 70, message = "Descrição excedeu o limite de caracteres!") String description,
		String urlImg) {

}
