package com.app.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtClaimsSet.Builder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.app.service.JWTService;

@Service
public class JWTServiceImpl implements JWTService {

	@Value("${app.jwt.expiration-delay-min}")
	private int jwtExpiresAt;
	@Autowired
	private JwtEncoder jwtEncoder;

	@Override
	public String generateJWT(Map<String, Object> claims, String subject) {
		Builder builder = JwtClaimsSet.builder();
		Instant instant = Instant.now();
		builder.issuedAt(instant).expiresAt(instant.plus(5, ChronoUnit.MINUTES)).subject(subject);
		configureClaims(builder, claims);
		JwtClaimsSet jwtClaimsSet = builder.build();
		return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet))
				.getTokenValue();
	}

	private static void configureClaims(JwtClaimsSet.Builder builder, Map<String, Object> claims) {
		claims.forEach(builder::claim);
	}

}