package com.app.entity;

import java.util.HashSet;
import java.util.Set;

import com.app.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
public class Role extends BaseEntity<Long> {

	@Column(length = 100, unique = true, nullable = false)
	private String label;
	private String description;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Permission> permissions = new HashSet<Permission>();

}
