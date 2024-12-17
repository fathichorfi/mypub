package com.app.controller.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ResetPasswordDto;
import com.app.dto.Response;
import com.app.dto.UserDto;
import com.app.dto.UserFilterDto;
import com.app.error.ErrorResponse;
import com.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User controller", description = "Users application managment")
@RestController
@RequestMapping(RequestPath.APPUSER)
@RequiredArgsConstructor
public class UserController {

	private final UserService accountService;

	/**
	 * Create a new application user
	 * 
	 * @param userDto
	 * @return
	 */
	@Operation(summary = "Create user", description = "Create a new application user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Resource created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
			@ApiResponse(responseCode = "409", description = "User already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not create the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@PostMapping
	ResponseEntity<Response> createUser(@RequestBody @Valid UserDto userDto) {
		UserDto createdUser = accountService.create(userDto);
		Response response = Response.builder().data(createdUser).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Update a user by id
	 * 
	 * @param userId
	 * @return
	 */
	@Operation(summary = "Update user", description = "Update an application user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resource created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not update the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))

	)
	@PutMapping("/{userId}")
	public ResponseEntity<Response> update(@PathVariable("userId") Long userId,
			@RequestBody @Valid UserDto appUserDto) {
		UserDto saveAppUserDto = accountService.update(userId, appUserDto);
		Response response = Response.builder().data(saveAppUserDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Delete a user by id
	 * 
	 * @param userId
	 * @return
	 */
	@Operation(summary = "Delete user", description = "Delete an application user by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Resource deleted", content = @Content()),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(@PathVariable("userId") Long userId) {
		accountService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Delete a user by username
	 * 
	 * @param appUserId
	 * @return
	 */
	@Operation(summary = "Delete user by username", description = "Delete an application user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Resource deleted", content = @Content()),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@DeleteMapping("/username/{username}")
	public ResponseEntity<Void> delete(@PathVariable("username") String username) {
		accountService.deleteUser(username);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Change user password
	 * 
	 * @param userId
	 * @param resetPasswordDto
	 * @return
	 */
	@Operation(summary = "Reset password", description = "Change the user password")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Password reset", content = @Content()),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can reset the password", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@PutMapping("/password/{userId}")
	public ResponseEntity<Void> resetPassword(@PathVariable("userId") Long userId,
			@RequestBody ResetPasswordDto resetPasswordDto) {
		accountService.resetPassword(userId, resetPasswordDto.getOldPassword(), resetPasswordDto.getNewPassword());
		return ResponseEntity.noContent().build();
	}

	/**
	 * Find user by id
	 * 
	 * @param userId
	 * @return
	 */
	@Operation(summary = "Find by id", description = "Search a user by identifier")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping("/{userId}")
	public ResponseEntity<Response> findById(
			@Parameter(name = "User id", description = "The user identifier") @PathVariable("userId") Long userId) {
		UserDto appUserDto = accountService.findById(userId);
		Response response = Response.builder().data(appUserDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Find user by username
	 * 
	 * @param username
	 * @return
	 */
	@Operation(summary = "Find by username", description = "Search a user by the username")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping("/username/{username}")
	public ResponseEntity<Response> findByUsername(
			@Parameter(name = "username", description = "The username") @PathVariable("username") String username) {
		UserDto appUserDto = accountService.getUser(username);
		Response response = Response.builder().data(appUserDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Find all users
	 * 
	 * @return
	 */
	@Operation(summary = "Find all users", description = "Return the all users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping
	public ResponseEntity<Response> findAll() {
		List<UserDto> list = accountService.findAll();
		Response response = Response.builder().data(list).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Find users with a spécified page
	 * 
	 * @param pageable
	 * @return
	 */
	@Operation(summary = "Find users", description = "Return a specific page of the users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping("/page")
	public ResponseEntity<Response> findPage(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<UserDto> pageUserDto = accountService.findAll(pageable);
		Response response = Response.builder().data(pageUserDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Search users
	 * 
	 * @return
	 */
	@Operation(summary = "Filter users", description = "Search all users with a specific criterias")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping("/filter")
	public ResponseEntity<Response> findAll(@RequestBody UserFilterDto filter) {
		List<UserDto> list = accountService.getUsers(filter);
		Response response = Response.builder().data(list).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Search users page
	 * 
	 * @return
	 */
	@Operation(summary = "Filter users", description = "Search a page of users with a specific criterias")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success result", content = @Content()),
			@ApiResponse(responseCode = "200", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Can not delete the resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@GetMapping("/filter/page")
	public ResponseEntity<Response> findAll(@RequestBody UserFilterDto filter,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<UserDto> list = accountService.getUsers(filter, pageable);
		Response response = Response.builder().data(list).build();
		return ResponseEntity.ok(response);
	}

}
