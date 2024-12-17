package com.app.entity;

import java.time.Instant;

import com.app.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken extends BaseEntity<Long> {

	private String token;
	private Instant expiryDate;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
