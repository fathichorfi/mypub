package com.app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDto {

	private Long id;
	@NotEmpty
	private String code;
	@NotEmpty
	private String label;
	private String description;

}
