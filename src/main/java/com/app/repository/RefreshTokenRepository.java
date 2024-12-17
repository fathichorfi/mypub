package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.base.BaseRepository;
import com.app.entity.RefreshToken;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	Optional<RefreshToken> findByUserUsername(String username);

}
