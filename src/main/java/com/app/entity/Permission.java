package com.app.entity;

import com.app.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Permission extends BaseEntity<Long> {

	@Column(length = 50, unique = true, nullable = false)
	private String code;
	@Column(length = 100, nullable = false)
	private String label;
	private String description;

}
