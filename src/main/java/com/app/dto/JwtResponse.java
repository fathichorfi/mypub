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
public class JwtResponse {

	@JsonProperty(value = "accessToken")
	private String accessToken;
	@JsonProperty(value = "refreshToken")
	private String refreshToken;
}
