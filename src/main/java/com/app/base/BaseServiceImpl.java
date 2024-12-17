package com.app.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.error.exception.NotFoundException;

public abstract class BaseServiceImpl<E extends BaseEntity<ID>, D, ID> implements BaseService<E, D, ID> {

	@Autowired
	private BaseRepository<E, ID> repository;

	@Autowired
	private BaseMapper<E, D> mapper;

	@Autowired
	public MessageSource messageSource;

	@Override
	public D create(D dto) {
		E entity = mapper.toEntity(dto);
		return mapper.toDto(repository.save(entity));
	}

	@Override
	public D update(ID id, D dto) {
		repository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
				.getMessage("app.validation.entityNotFound", new Object[] { id }, LocaleContextHolder.getLocale())));
		E entity  = mapper.toEntity(dto);
		entity.setId(id);		
		return mapper.toDto(repository.save(entity));
	}

	@Override
	public void deleteById(ID id) {
		repository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
				.getMessage("app.validation.entityNotFound", new Object[] { id }, LocaleContextHolder.getLocale())));
		repository.deleteById(id);
	}

	@Override
	public D findById(ID id) {
		E entity = repository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
				.getMessage("app.validation.entityNotFound", new Object[] { id }, LocaleContextHolder.getLocale())));
		return mapper.toDto(entity);
	}

	@Override
	public List<D> findAll() {
		return mapper.toDtoList(repository.findAll());
	}

	@Override
	public Page<D> findAll(Pageable page) {
		Page<E> entityList = repository.findAll(page);
		return entityList.map(mapper::toDto);
	}

}
