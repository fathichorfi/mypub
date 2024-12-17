package com.app.mapper;

import org.mapstruct.Mapper;

import com.app.base.BaseMapper;
import com.app.dto.UserDto;
import com.app.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User, UserDto> {

}
