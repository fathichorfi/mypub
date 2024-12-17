package com.app.repository;

import com.app.base.BaseRepository;
import com.app.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

	Role findByLabel(String label);

}
