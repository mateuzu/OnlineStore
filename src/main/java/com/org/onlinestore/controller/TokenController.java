package com.org.onlinestore.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.onlinestore.controller.dto.LoginRequest;
import com.org.onlinestore.controller.dto.LoginResponse;
import com.org.onlinestore.repository.UserRepository;

/* Fluxo de login + autenticação
 * 
 * 1. Recebe um usuário + senha 
 * 2. verifica se o usuário existe no banco de dados e as credenciais estão corretas
 * 3. se sim, monta o TOKEN JWT 
 * 4. assina o TOKEN com a chave privada (encode)
 * 5. Retorna o TOKEN (chave de acesso)
 */

@RestController
public class TokenController {
	
	private final JwtEncoder jwtEncoder;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/login") //1. recebe usuário + senha
	public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
		
		var user = userRepository.findByEmail(loginRequest.email());
		
		//2. verifica o usuário existe e se as credencias estão corretas
		if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
			throw new BadCredentialsException("Email ou senha incorretos!");
		}
		
		var now = Instant.now(); //Determina a hora atual
		var expiresIn = 300L; //Determina o tempo de expiração do TOKEN. Nesse caso 300 segunos = 5 minutos 
		
		//3. se sim, monta o token JWT : Claims = atributos JSON do Token JWT
		var claims = JwtClaimsSet.builder()
				.issuer("onlinestore-backend") //convençao para determinar quem está gerando o token
				.subject(user.get().getUserId().toString()) //convençao para determinar o usuário como subject do JSON e trafegar o usuario no JSON
				.issuedAt(now) //convençao para determinar quando o token foi gerado
				.expiresAt(now.plusSeconds(expiresIn)) //convençao para determinar quando o token irá expirar. nesse caso será a hora atual + 300 segundos
				.build(); //costroi o JWTClaimsSet
		
		//4. assinar o token com a chave privada (encode) utilizando as claims
		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
		//5. retorna o token com a chave de acesso e o tempo de expiração
		return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
	}
}
