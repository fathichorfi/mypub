package com.app.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.base.BaseServiceImpl;
import com.app.dto.UserDto;
import com.app.dto.UserFilterDto;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.error.exception.BusinessException;
import com.app.error.exception.ConflictException;
import com.app.error.exception.NotFoundException;
import com.app.error.exception.ResourceAlreadyExistsException;
import com.app.mapper.RoleMapper;
import com.app.mapper.UserMapper;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.repository.spec.UserSpec;
import com.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User, UserDto, Long> implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MessageSource messageSource;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;
	private final RoleRepository roleRepository;

	@Override
	public UserDto create(UserDto userDto) {
		// Check if the username already exists
		if (userRepository.findByUsername(userDto.getUsername()).isPresent())
			throw new ResourceAlreadyExistsException(messageSource.getMessage("app.validation.userExists",
					new Object[] { userDto.getUsername() }, LocaleContextHolder.getLocale()));
		User user = userMapper.toEntity(userDto);
		// Load the roles
		Set<Role> roles = new HashSet<Role>();
		userDto.getRoles().forEach(roleDto -> {
			Role role = roleRepository.findById(roleDto.getId())
					.orElseThrow(() -> new NotFoundException(messageSource.getMessage("app.validation.roleNotFound",
							new Object[] { roleDto.getId() }, LocaleContextHolder.getLocale())));
			roles.add(role);
		});
		user.setRoles(roles);
		// Save the user after password encoding
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEnabled(true);
		return userMapper.toDto(userRepository.save(user));
	}

	@Override
	public UserDto update(Long id, UserDto userDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
				.getMessage("app.validation.userNotFound", new Object[] { id }, LocaleContextHolder.getLocale())));
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		Set<Role> roles = new HashSet<Role>();
		userDto.getRoles().forEach(roleDto -> {
			roles.add(roleMapper.toEntity(roleDto));
		});
		user.setRoles(roles);
		return userMapper.toDto(userRepository.save(user));
	}

	@Override
	public void deleteUser(String username) {
		userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(messageSource.getMessage("app.validation.userNotFound",
						new Object[] { username }, LocaleContextHolder.getLocale())));
		userRepository.deleteByUsername(username);
	}

	@Override
	public void resetPassword(Long id, String oldPassword, String newPassword) {
		// Check if the user exists
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
				.getMessage("app.validation.userNotFound", new Object[] { id }, LocaleContextHolder.getLocale())));
		// Match the old password
		if (!passwordEncoder.matches(oldPassword, user.getPassword()))
			throw new ConflictException(messageSource.getMessage("app.validation.constraints.password.noMaches",
					new Object[] {}, LocaleContextHolder.getLocale()));
		userRepository.updatePassword(id, passwordEncoder.encode(newPassword));
	}

	@Override
	public void resetPassword(String username, String oldPassword, String newPassword) {
		// Check if the user exists
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(messageSource.getMessage("app.validation.userNotFound",
						new Object[] { username }, LocaleContextHolder.getLocale())));
		// Match the old password
		if (!passwordEncoder.matches(oldPassword, user.getPassword()))
			throw new ConflictException(messageSource.getMessage("app.validation.constraints.password.noMaches",
					new Object[] {}, LocaleContextHolder.getLocale()));
		userRepository.updatePassword(user.getId(), passwordEncoder.encode(newPassword));
	}

	@Override
	public UserDto getUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(messageSource.getMessage("app.validation.userNotFound",
						new Object[] { username }, LocaleContextHolder.getLocale())));
		return userMapper.toDto(user);
	}


	@Override
	public List<UserDto> getUsers(UserFilterDto search) {
		return userMapper.toDtoList(userRepository.findAll(new UserSpec(search)));
	}

	@Override
	public Page<UserDto> getUsers(UserFilterDto search, Pageable page) {
		UserSpec spec = new UserSpec(search);
		Page<User> appUserList = userRepository.findAll(spec, page);
		return appUserList.map(userMapper::toDto);
	}

}
