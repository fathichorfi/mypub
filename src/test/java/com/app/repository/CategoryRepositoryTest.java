package com.app.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.entity.Category;

@SpringBootTest
public class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//@Test
	void findByCodeNotFoundTest() {
		Optional<Category> category = categoryRepository.findByLabel("oioioio");
		assertEquals(false, category.isPresent());		
	}
	//@Test
	void findByCodeFoundTest() {
		Optional<Category> category = categoryRepository.findByLabel("bois");
		assertEquals(true, category.isPresent());		
	}

}
