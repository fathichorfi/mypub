package com.app.entity;

import java.util.Set;

import com.app.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
public class User extends BaseEntity<Long> {

	@Column(length = 50)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	@Column(length = 50, unique = true,nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private String email;
	private Boolean enabled;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private RefreshToken refreshToken;

}
