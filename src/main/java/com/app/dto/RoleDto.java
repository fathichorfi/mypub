package com.app.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

	private Long id;
	private String label;
	private String description;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Schema(hidden = true)
	private Set<PermissionDto> permissions;

}
