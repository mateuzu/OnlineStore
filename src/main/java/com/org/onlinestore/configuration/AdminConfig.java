package com.org.onlinestore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.org.onlinestore.model.User;
import com.org.onlinestore.repository.UserRepository;

import jakarta.transaction.Transactional;

/*
 * CommandLineRunner executa o código no momneto que o projeto for executado
 * 
 * Fluxo de criação do User Admin
 * 1. Verifica se existe um usuário com o username "admin"
 * 2. Se sim, indica no console
 * 3. Se não, cria um usuário
 * 3.1. Salve o usuário no banco de dados
 */

@Configuration
public class AdminConfig implements CommandLineRunner {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public AdminConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		var userAdmin = userRepository.findByEmail("admin@email.com");

		//1. verifica se existe um usuário com username admin
		userAdmin.ifPresentOrElse(
				user -> { //2. se sim, indica no console
					System.out.println("Usuário já existe");
				}, 
				() -> { //3. se não, cria o usuário
					var user = new User();
					
					user.setUsername("admin");
					user.setEmail("admin@email.com");
					user.setPassword(passwordEncoder.encode("123amdmin")); //seta a senha criptografada do usuário
					
					userRepository.save(user);//3.1 salve o usuário no banco
				}
			);
	}

}
