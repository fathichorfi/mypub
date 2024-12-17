package com.app.service;

import java.util.List;

import com.app.dto.CategoryDto;

public interface CategoryService {
	
	public List<CategoryDto> findAll();
	public CategoryDto create(CategoryDto categoryDto);
	public CategoryDto findById(Long id);
	public CategoryDto findByCode(String code);
	public CategoryDto update(CategoryDto categoryDto);
	public void deleteById(Long id);

}
