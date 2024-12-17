package com.app.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.app.controller.v1.RequestPath;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	@Value("${app.jwt.key}")
	private String secretKey;

	private final String[] WHITE_LIST = { RequestPath.LOGIN, RequestPath.REFRESH_TOKEN, "/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST).permitAll())
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(or -> or.jwt(Customizer.withDefaults())).httpBasic(Customizer.withDefaults())
				.build();

	}

	// @Bean
	// @Primary
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				User.withUsername("user").password(passwordEncoder().encode("user")).authorities("USER").build(),
				User.withUsername("admin").password(passwordEncoder().encode("admin")).authorities("USER", "ADMIN")
						.build());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(daoAuthenticationProvider);
	}

}
