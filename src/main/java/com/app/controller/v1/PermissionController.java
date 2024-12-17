package com.app.controller.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PermissionDto;
import com.app.dto.Response;
import com.app.mapper.PermissionMapper;
import com.app.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Permission controller")
@Slf4j
@RestController
@RequestMapping(RequestPath.PERMISSION)
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionService permissionService;
	private final PermissionMapper permissionMapper;

	/**
	 * Add a new permission
	 * 
	 * @param permissionDto
	 * @return
	 */
	@Operation(summary = "Create a new permission")
	@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "201", description = "User created"),
			@ApiResponse(responseCode = "500", description = "Can not create the permission") })
	@PostMapping
	ResponseEntity<Response> createPermission(@RequestBody @Valid PermissionDto permissionDto) {
		PermissionDto createdPermission = permissionService.create(permissionDto);
		Response response = Response.builder().data(createdPermission).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Update a permission by id
	 * 
	 * @param permissionId
	 * @return
	 */
	@PutMapping("/{permissionId}")
	public ResponseEntity<Response> update(@PathVariable("permissionId") Long permissionId,
			@RequestBody @Valid PermissionDto permissionDto) {
		PermissionDto saveApppermissionDto = permissionService.update(permissionId, permissionDto);
		Response response = Response.builder().data(saveApppermissionDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Delete a permission by id
	 * 
	 * @param permissionId
	 * @return
	 */
	@DeleteMapping("/{permissionId}")
	public ResponseEntity<Void> delete(@PathVariable("permissionId") Long permissionId) {
		permissionService.deleteById(permissionId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Find permission by id
	 * 
	 * @param permissionId
	 * @return
	 */
	@GetMapping("/{permissionId}")
	public ResponseEntity<Response> findById(
			@Parameter(name = "Json stream of user", description = "llllllllllllllll") @PathVariable("permissionId") Long permissionId) {
		PermissionDto permissionDto = permissionService.findById(permissionId);
		Response response = Response.builder().data(permissionDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Find all permissions
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response> findAll() {
		List<PermissionDto> list = permissionService.findAll();
		Response response = Response.builder().data(list).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Find permissions with a spécified page
	 * 
	 * @param pageable
	 * @return
	 */
	@GetMapping("/page")
	public ResponseEntity<Response> findPage(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<PermissionDto> pageUserDto = permissionService.findAll(pageable);
		Response response = Response.builder().data(pageUserDto).build();
		return ResponseEntity.ok(response);
	}

}
