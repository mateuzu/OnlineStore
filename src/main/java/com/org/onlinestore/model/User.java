package com.org.onlinestore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.onlinestore.controller.dto.LoginRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "TB_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "userId")
	private UUID userId;
	private String username;
	
	@Email
	private String email;
	
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(UUID userId, String username, String email, String password) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
		//Verifica se a senha descriptografada (raw) é igual a senha criptografada(encode)
		var encodedPassword = passwordEncoder.encode(loginRequest.password());
		return passwordEncoder.matches(this.password, encodedPassword);
	}
}
