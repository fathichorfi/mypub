package com.app.repository.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.dto.UserFilterDto;
import com.app.entity.Role;
import com.app.entity.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSpec implements Specification<User> {

	private UserFilterDto search;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		Join<User, Role> joinRole = root.join("roles", JoinType.LEFT);
		if (search.getEnabled() != null)
			predicates.add(criteriaBuilder.equal(root.get("enabled"), search.getEnabled()));
		if (search.getEmail() != null)
			predicates.add(criteriaBuilder.like(root.get("email"), "%" + search.getEmail() + "%"));
		if (search.getRoleLabel() != null)
			predicates.add(criteriaBuilder.like(joinRole.get("label"), "%" + search.getRoleLabel() + "%"));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

}
