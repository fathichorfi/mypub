package com.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
@OpenAPIDefinition
public class OpenAPIconfig {

	private static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";

	@Bean
	public OpenAPI appOpenAPI(@Value("${app.title}") String title, @Value("${app.description}") String appDescription,
			@Value("${app.version}") String appVersion) {

		return new OpenAPI()
				.info(new Info().title(title).version(appVersion).contact(getContact()).license(getLicense())
						.description(appDescription));
//				.addSecurityItem(
//						new SecurityRequirement().addList(SECURITY_SCHEME_NAME, Arrays.asList("read", "write")))
//				.components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
//						.name(SECURITY_SCHEME_NAME).type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));

	}

	private Contact getContact() {
		return new Contact().name("admin yassif").email("admin@admin.com").url("https://poc.site.com");
	}

	private License getLicense() {
		return new License().name("License of my app").url("https://poc.site.com/license");
	}

}
