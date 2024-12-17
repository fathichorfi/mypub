package com.app.dto;

import java.util.Set;

import com.app.validator.Login;
import com.app.validator.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private Long id;
	@Login
	@NotBlank
	@Size(min = 4, max = 50)
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Password
	@NotBlank
	@Size(min = 6, max = 30)
	private String password;
	@Email
	private String email;
	@NotBlank
	@Size(max = 50)
	private String firstName;
	@NotBlank
	@Size(max = 50)
	private String lastName;
	@NotEmpty
	private Set<RoleDto> roles;

}
