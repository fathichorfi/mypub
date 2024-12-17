package com.app.service.impl;

import org.springframework.stereotype.Service;

import com.app.base.BaseServiceImpl;
import com.app.dto.PermissionDto;
import com.app.entity.Permission;
import com.app.service.PermissionService;

@Service
public class PermissionImpl extends BaseServiceImpl<Permission, PermissionDto, Long> implements PermissionService {

}
