package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.base.BaseRepository;
import com.app.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends BaseRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Transactional
	public void deleteByUsername(String username);

	public Optional<User> findByUsername(String username);

	@Modifying
	@Transactional
	@Query(value = "update User a set a.password = :newPassword where a.id = :id")
	public void updatePassword(Long id, String newPassword);

}
