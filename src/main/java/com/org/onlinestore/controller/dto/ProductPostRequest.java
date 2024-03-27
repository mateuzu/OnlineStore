package com.org.onlinestore.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductPostRequest(
		@NotBlank String name,
		@Size(max = 70, message = "Descrição excedeu o limite de caracteres") String description,
		@PositiveOrZero(message = "O preço deve ser maior que zero!") BigDecimal price,
		@PositiveOrZero(message = "A quantidade deve ser maior que zero!") Integer quantity,
		String urlImg) {

}
