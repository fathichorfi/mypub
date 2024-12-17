package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.app.base.BaseServiceImpl;
import com.app.dto.CategoryDto;
import com.app.entity.Category;
import com.app.error.exception.NotFoundException;
import com.app.mapper.CategoryMapper;
import com.app.repository.CategoryRepository;
import com.app.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final MessageSource messageSource;
	private final CategoryMapper categoryMapper;

	@Override
	@Cacheable(value = "categories")
	public List<CategoryDto> findAll() {
		List<Category> entityList = categoryRepository.findAll();
		List<CategoryDto> dtoList = new ArrayList<CategoryDto>();
		entityList.forEach(entity -> dtoList.add(categoryMapper.toDto(entity)));
		return dtoList;
	}

	@Override
	@CacheEvict(value = "categories", allEntries = true)
	public CategoryDto create(CategoryDto categoryDto) {
		Category category = categoryMapper.toEntity(categoryDto);
		return categoryMapper.toDto(categoryRepository.save(category));
	}

	@Override
	@Cacheable(value = "category", key = "#id")
	public CategoryDto findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isEmpty())
			throw new NotFoundException(messageSource.getMessage("app.validation.userNotFound", new Object[] { id },
					LocaleContextHolder.getLocale()));
		return categoryMapper.toDto(category.get());
	}

	@Override
	public CategoryDto findByCode(String code) {
		Optional<Category> category = categoryRepository.findByLabel(code);
		if (category.isEmpty())
			throw new NotFoundException(messageSource.getMessage("app.validation.userNotFound", new Object[] { code },
					LocaleContextHolder.getLocale()));
		return categoryMapper.toDto(category.get());
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "categories", allEntries = true),
			@CacheEvict(value = "category", key = "#categoryDto.id") })
	public CategoryDto update(CategoryDto categoryDto) {
		Optional<Category> category = categoryRepository.findById(categoryDto.getId());
		category.get().setId(categoryDto.getId());
		category.get().setLabel(categoryDto.getLabel());
		Category saveCategory = categoryRepository.save(category.get());
		return categoryMapper.toDto(saveCategory);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "categories", allEntries = true),
			@CacheEvict(value = "category", key = "#id") })
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

}
