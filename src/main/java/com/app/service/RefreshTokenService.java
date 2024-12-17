package com.app.service;

import java.util.Optional;

import com.app.entity.RefreshToken;

public interface RefreshTokenService {

	RefreshToken generateRefreshToken(String username);
	
	Optional<RefreshToken> findByToken(String token);
	
	RefreshToken verifyExpiration(RefreshToken refreshToken);

}
