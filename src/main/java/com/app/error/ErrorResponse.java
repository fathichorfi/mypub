package com.app.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	private String success;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@Schema(description = "Date and time off the error", example = "10-12-2024 12:58:44")
	private LocalDateTime dateTime;
	private String path;
	// @JsonIgnore
	private String locale;
	// @JsonIgnore
	private String detail;

}
