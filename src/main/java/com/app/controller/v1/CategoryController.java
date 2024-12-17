package com.app.controller.v1;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.CategoryDto;
import com.app.dto.Response;
import com.app.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Category controller")
@Slf4j
@RestController
@RequestMapping(RequestPath.CATEGORY)
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/demo")
	@Cacheable("data")
	public String demo() {
		log.info("====================cache===================");
		return "Bonjour";
	}

	/**
	 * Add a new category
	 * 
	 * @param categoryDto
	 * @return
	 */
	@Operation(summary = "Create a new category")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Category created"),
			@ApiResponse(responseCode = "500", description = "Can not create the category") })
	@PostMapping
	ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		CategoryDto newCategory = categoryService.create(categoryDto);
		Response response = Response.builder().data(newCategory).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Get all categories
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response> findAll() {
		List<CategoryDto> list = categoryService.findAll();
		Response response = Response.builder().data(list).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Get category by id
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/{categoryId}")
	public ResponseEntity<Response> find(
			@Parameter(name = "Json stream of user", description = "llllllllllllllll") @PathVariable("categoryId") Long categoryId) {
		CategoryDto categoryDto = categoryService.findById(categoryId);
		Response response = Response.builder().data(categoryDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Update a category by id
	 * 
	 * @param appCategoryId
	 * @return
	 */
	@PutMapping("/{categoryId}")
	public ResponseEntity<Response> update(@RequestBody @Valid CategoryDto categoryDto) {
		CategoryDto saveCategoryDto = categoryService.update(categoryDto);
		Response response = Response.builder().data(saveCategoryDto).build();
		return ResponseEntity.ok(response);
	}

	/**
	 * Delete a cateogry by id
	 * 
	 * @param categoryId
	 * @return
	 */
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Response> delete(@PathVariable("categoryId") Long categoryId) {
		categoryService.deleteById(categoryId);
		Response response = Response.builder().data("Category deleted").build();
		return ResponseEntity.ok(response);
	}

}
