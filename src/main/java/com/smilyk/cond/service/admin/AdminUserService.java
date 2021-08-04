package com.smilyk.cond.service.admin;

import com.smilyk.cond.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.UserEntity;

public interface AdminUserService {
    ResponseUserDto createUser(UserDto userDto);

    ResponseDeleteBlockedUserDto deleteUser(UserEntity userUuid);

    ResponseDeleteBlockedUserDto blockUser(UserEntity userEntity);

    ResponseUserDto addRoleToUser(UserEntity userEntity, Roles role);
}
