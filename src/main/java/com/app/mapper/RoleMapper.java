package com.app.mapper;

import org.mapstruct.Mapper;

import com.app.base.BaseMapper;
import com.app.dto.RoleDto;
import com.app.entity.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role, RoleDto> {

}
