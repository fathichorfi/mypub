package com.app.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class })
public abstract class BaseEntity<ID> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	@LastModifiedBy
	private String lastModifiedBy;

}
