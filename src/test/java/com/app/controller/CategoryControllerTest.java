package com.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.dto.CategoryDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	//@Test
	void find() {
		ResponseEntity<CategoryDto> responseEntity = restTemplate.getForEntity("/api/v1/category/2", CategoryDto.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
