package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@JsonProperty(value = "username")
	private String username;
	@JsonProperty(value = "password")
	private String password;
	@Builder.Default
	private Boolean withRefreshToken = false;

}
