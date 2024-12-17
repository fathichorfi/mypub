package com.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.dto.UserFilterDto;
import com.app.entity.User;
import com.app.repository.spec.UserSpec;

@SpringBootTest
public class AppUserTest {

	@Autowired
	UserRepository appUserRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	//@Test
	void findByUsernameFoundTest() {
		Optional<User> appUser = appUserRepository.findByUsername("fathi");
		assertEquals(true, appUser.isPresent());
	}

	//@Test
	void findByUsernameNotFoundTest() {
		Optional<User> appUser = appUserRepository.findByUsername("xxxx");
		assertEquals(true, appUser.isEmpty());
	}

	//@Test
	void updatePasswordTest() {
		Long userId = 3l;
		String newPassword = "newPassword";
		appUserRepository.updatePassword(userId, newPassword);
		Optional<User> appUser = appUserRepository.findById(userId);
		assertEquals(true, appUser.isPresent());
		assertEquals(true, appUser.get().getPassword().equals(newPassword));
	}

	//@Test
	void deleteById() {
		Long userId = 3l;
		appUserRepository.deleteById(userId);
		Optional<User> appUser = appUserRepository.findById(userId);
		assertEquals(true, !appUser.isPresent());
	}
	
	//@Test
	void findAllWithCriteria() {
		Pageable page = PageRequest.of(0, 10, Sort.by("email"));
		UserFilterDto search = UserFilterDto.builder().email("test1@gmail.com").build();
		UserSpec spec = new UserSpec(search);
		Page<User> appUserList = appUserRepository.findAll(spec, page);
		assertEquals(true, appUserList.getSize()>0);
	}

}
