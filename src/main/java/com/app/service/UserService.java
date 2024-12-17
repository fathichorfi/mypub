package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.base.BaseService;
import com.app.dto.UserDto;
import com.app.dto.UserFilterDto;
import com.app.entity.User;

public interface UserService extends BaseService<User, UserDto, Long>  {

	UserDto create(UserDto userDto);

	UserDto update(Long id, UserDto userDto);

	void deleteUser(String username);

	void resetPassword(Long id, String oldPassword, String newPassword);

	void resetPassword(String username, String oldPassword, String newPassword);

	UserDto getUser(String username);

	List<UserDto> getUsers(UserFilterDto search);

	Page<UserDto> getUsers(UserFilterDto search, Pageable page);

}
