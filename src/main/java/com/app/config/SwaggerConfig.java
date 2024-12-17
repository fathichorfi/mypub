package com.app.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi userMangementApi() {
		String pakagesToScan[] = { "com.app.controller.v1" };
		return GroupedOpenApi.builder().group("User Management API").packagesToScan(pakagesToScan)
				.addOperationCustomizer(appTokenHeaderParam()).build();
	}
	@Bean
	public GroupedOpenApi setUpApi() {
		String pakagesToScan[] = { "com.app.controller.v1" };
		return GroupedOpenApi.builder().group("Set up API").packagesToScan(pakagesToScan)
				.addOperationCustomizer(appTokenHeaderParam()).build();
	}

	@Bean
	public OperationCustomizer appTokenHeaderParam() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).required(false).schema(
					new StringSchema()._default("app_token_header").description("App token header description"));
			operation.addParametersItem(headerParameter);
			return operation;
		};
	}

}
