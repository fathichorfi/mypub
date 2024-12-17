package com.app.repository;

import java.util.Optional;

import com.app.base.BaseRepository;
import com.app.entity.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {

	Optional<Category> findByLabel(String label);

}
