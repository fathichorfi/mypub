package com.app.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.context.i18n.LocaleContextHolder;

import com.app.base.BaseMapper;
import com.app.dto.CategoryDto;
import com.app.entity.Category;

//@Mapper(imports= {LocaleContextHolder.class})
@Mapper
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {

	//@Mapping(source = "code", target = "code")
//	Category toEntity(CategoryDtoo categoryDto);

	//@Mappings({ @Mapping(source = "code", target = "code") })
//	@Mapping(target = "label", expression = "java(LocalContextHolder.getLocale() == \"ar\" ? entity.getArLabel() : entity.getFRLabel())")
//	CategoryDto toDto(Category category);
	
//	@AfterMapping
//	default void codeMap(Category category, @MappingTarget CategoryDto categoryDto) {
//		if(LocaleContextHolder.getLocale().equals("ar" ))
//			categoryDto.setCode(category.getCode());
//	}

}
