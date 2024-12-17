package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilterDto {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Boolean enabled;
	private String roleLabel;

}
