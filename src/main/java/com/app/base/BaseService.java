package com.app.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<E extends BaseEntity<ID>, D, ID> {

	D create(D dto);

	D update(ID id, D dto);

	void deleteById(ID id);

	D findById(ID id);

	List<D> findAll();

	Page<D> findAll(Pageable page);

}
