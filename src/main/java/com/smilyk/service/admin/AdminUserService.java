package com.smilyk.service.admin;

import com.smilyk.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UserDto;
import com.smilyk.enums.Roles;
import com.smilyk.model.UserEntity;

/**
 * Service for Administrator. This is interface, his impl -> {@link AdminUserServiceImpl}
 */
public interface AdminUserService {
    ResponseUserDto createUser(UserDto userDto);

    ResponseDeleteBlockedUserDto deleteUser(UserEntity userUuid);

    ResponseDeleteBlockedUserDto blockUser(UserEntity userEntity);

    ResponseUserDto addRoleToUser(UserEntity userEntity, Roles role);

    ResponseUserDto deleteRoleFromUser(UserEntity userEntity, Roles role);
}
