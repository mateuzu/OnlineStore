package com.org.onlinestore.configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey; //chave púlbica
	
	@Value("${jwt.private.key}")
	private RSAPrivateKey privateKey; //chave privada
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
						.requestMatchers(HttpMethod.POST, "/login").permitAll() //liberando rota de login
						.requestMatchers(HttpMethod.GET, "/v1/product/all").permitAll()
						.anyRequest().authenticated()) //especifica que qualquer requisição exige autenticação do usuário
				.csrf(csrf -> csrf.disable()) //desativa o csrf, obs: deixar desativado apenas LOCALMENTE
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) //utiliza as condfigurações padrões de autenticação do OAuth2
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //indica que nenhuma informação sobre autennticação será armazenada em sessão
		
		return http.build();
		
		/*
		 * CSRF: Tipo de ateque em que um invasor pode enganar um usuário autenticado a realizações ações não intencionais.
		 * Session HTTP: Mantém o estado entre requisições do cliente para o servidor em aplicacões WEB. Permite que o servidor associe informações de estado com usuário expecífico.
		 */
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(publicKey).build(); //Decodificar token com a chave pública
	}
	
	@Bean
	public JwtEncoder jwtEncoder() { //Codificar e assinar token com as chaves pública/privada
		JWK jkw = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jkw));
		return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { //Criptografar senhas
		return new BCryptPasswordEncoder();
	}
	
}
