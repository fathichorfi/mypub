package com.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.mapper.UserMapper;
import com.app.repository.UserRepository;
import com.app.service.impl.UserServiceImpl;

@SpringBootTest
public class AppUserServiceTest {

	@Autowired
	private UserServiceImpl accountService;

	@MockBean
	private UserRepository appUserRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserMapper appUserMapper;

	//@Test
	public void updateUserTest() {

		Long userId = 3l;
		User appUser = User.builder().id(userId).email("test@gmail.com").firstName("myfirstnam").lastName("lastname")
				.password("test").username("myusername").build();
		when(appUserRepository.findById(userId)).thenReturn(Optional.of(appUser));
		when(appUserRepository.save(appUser)).thenReturn(appUser);
		UserDto appUserDto = UserDto.builder().email("test@gmail.com").firstName("myfirstnam").lastName("lastname")
				.password("test").username("myusername").build();
		UserDto savedAppUserDto = accountService.update(userId, appUserDto);
		assertEquals(true, savedAppUserDto != null);
		assertEquals(true, savedAppUserDto.getId() == userId);

	}

	//@Test
	public void getUsersTest() {
		User a = new User();
		User appUser1 = User.builder().id(1l).email("test1@gmail.com").firstName("myfirstnam1").lastName("lastname1")
				.password("test1").username("myusername1").build();
		User appUser2 = User.builder().id(2l).email("test2@gmail.com").firstName("myfirstnam2").lastName("lastname2")
				.password("test2").username("myusername2").build();
		List<User> users = Arrays.asList(appUser1, appUser2);
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> pageUsers = new PageImpl<User>(users, pageable, users.size());
		when(appUserRepository.findAll(pageable)).thenReturn(pageUsers);
		Pageable page = PageRequest.of(1, 10, null);
		Page<UserDto> result = accountService.findAll(page);

		assertEquals(2, result.getContent().size());
		assertEquals(0, pageUsers.getNumber());
		assertEquals("lastname1", result.getContent().get(0).getLastName());
		assertEquals("lastname2", result.getContent().get(1).getLastName());
	}

	//@Test
	public void getUsersSortedTest() {
		// Order
		List<Order> orders = new ArrayList<Order>();
		Order order1 = new Order(Direction.ASC, "lastName");
		orders.add(order1);
		// Mock
		User appUser1 = User.builder().id(1l).email("test1@gmail.com").firstName("myfirstnam1").lastName("lastname1")
				.password("test1").username("myusername1").build();
		User appUser2 = User.builder().id(2l).email("test2@gmail.com").firstName("myfirstnam2").lastName("lastname2")
				.password("test2").username("myusername2").build();
		List<User> users = Arrays.asList(appUser1, appUser2);
		Pageable pageable = PageRequest.of(0, 10, Sort.by(orders));
		Page<User> pageUsers = new PageImpl<User>(users, pageable, users.size());
		when(appUserRepository.findAll(pageable)).thenReturn(pageUsers);
		// Service call
		Pageable page = PageRequest.of(1, 10, Sort.by(orders));
		Page<UserDto> result = accountService.findAll(page);
		// Assertions
		assertEquals(2, result.getContent().size());
		assertEquals(0, pageUsers.getNumber());
		assertEquals("lastname1", result.getContent().get(0).getLastName());
		assertEquals("lastname2", result.getContent().get(1).getLastName());
	}

}
