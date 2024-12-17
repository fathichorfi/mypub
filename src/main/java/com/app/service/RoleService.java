package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.dto.RoleDto;

public interface RoleService {

	RoleDto createRole(RoleDto roleDto);

	RoleDto updateRole(RoleDto roleDto);

	void deleteRole(Long id);

	Optional<RoleDto> getRoleById(Long id);

	Optional<RoleDto> getRoleByName(String name);

	List<RoleDto> getAllRoles();

}
