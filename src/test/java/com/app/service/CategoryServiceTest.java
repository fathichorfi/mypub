package com.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.dto.CategoryDto;
import com.app.entity.Category;
import com.app.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {

//	@InjectMocks
	@Autowired
	private CategoryService categoryService;

//	@Mock
	@MockBean
	CategoryRepository categoryRepository;

	//@Test
	void loadCategoryFoundTest() {
		Optional<Category> categoryParam = Optional.of(Category.builder().label("ioioioioio").build());
		Mockito.when(categoryRepository.findByLabel(Mockito.anyString())).thenReturn(categoryParam);
		CategoryDto categoryDto = categoryService.findByCode("ioioioioio");
		assertEquals(true, categoryDto!=null);
		assertEquals("ioioioioio", categoryDto.getLabel());
	}

}
