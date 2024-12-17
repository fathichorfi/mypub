package com.app.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.entity.RefreshToken;
import com.app.repository.UserRepository;
import com.app.repository.RefreshTokenRepository;
import com.app.service.RefreshTokenService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RefreshTokenImpl implements RefreshTokenService {

	@Value("${app.refresh-token.expiration-delay-min}")
	private int refreshTokenExpiresAt;
	@Value("${app.refresh-token.rebuild}")
	private Boolean refreshTokenRebuild;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public RefreshToken generateRefreshToken(String username) {
		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserUsername(username);
		if (refreshToken.isPresent()) {
			if (refreshTokenRebuild || refreshToken.get().getExpiryDate().isBefore(Instant.now()))
				refreshTokenRepository.delete(refreshToken.get());
			else
				return refreshToken.get();
		}
		RefreshToken newRefreshToken = RefreshToken.builder()
				.expiryDate(Instant.now().plus(refreshTokenExpiresAt, ChronoUnit.MINUTES))
				.token(UUID.randomUUID().toString()).user(userRepository.findByUsername(username).get()).build();
		return refreshTokenRepository.save(newRefreshToken);
	}

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(refreshToken);
			throw new RuntimeException(refreshToken.getToken() + " Refresh token was expired!");
		}
		return refreshToken;
	}

}
