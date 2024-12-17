package com.app.mapper;

import org.mapstruct.Mapper;

import com.app.base.BaseMapper;
import com.app.dto.PermissionDto;
import com.app.entity.Permission;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission, PermissionDto> {

}
