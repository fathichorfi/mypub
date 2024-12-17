package com.app.controller.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.JwtResponse;
import com.app.dto.LoginRequest;
import com.app.dto.RefreshTokenRequest;
import com.app.entity.RefreshToken;
import com.app.service.JWTService;
import com.app.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

	private AuthenticationManager authenticationManager;
	private RefreshTokenService refreshTokenService;
	private JWTService jwtService;

	@PostMapping(path = RequestPath.LOGIN)
	public JwtResponse authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("scope", authentication.getAuthorities().stream().map(auth -> auth.getAuthority())
				.collect(Collectors.joining(" ")));
		return JwtResponse.builder().accessToken(jwtService.generateJWT(claims, authentication.getName()))
				.refreshToken(loginRequest.getWithRefreshToken()
						? refreshTokenService.generateRefreshToken(authentication.getName()).getToken()
						: "")
				.build();
	}

	@PostMapping(path = RequestPath.REFRESH_TOKEN)
	public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return refreshTokenService.findByToken(refreshTokenRequest.getToken())
				.map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser).map(appUser -> {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					Map<String, Object> claims = new HashMap<String, Object>();
					claims.put("scope", authentication.getAuthorities().stream().map(auth -> auth.getAuthority())
							.collect(Collectors.joining(" ")));
					return JwtResponse.builder().accessToken(jwtService.generateJWT(claims, appUser.getUsername()))
							.refreshToken(refreshTokenRequest.getToken()).build();
				}).orElseThrow(() -> new RuntimeException(refreshTokenRequest.getToken() + " token not found!"));
	}

	@GetMapping(path = "/profile")
	public Map<String, Object> profile(Authentication authentication) {
		return Map.of("authentication", authentication, "instanceOf", authentication.getClass().getName());

	}

}
