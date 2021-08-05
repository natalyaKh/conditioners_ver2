package com.smilyk.cond.service.general;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.service.admin.AdminUserServiceImpl;

import java.util.List;

/**
 *  * Service for all users.
 *  This is interface, his impl -> {@link GeneralUserServiceImpl}
 *  in this interface exists methods where permit for some roles
 */
public interface GeneralUserService {
    ResponseUserDto getUserByUuid(UserEntity userEntity);

    ResponseUserDto updateUserByUuid(UserEntity userEntity, UpdateUserDto updateUserDto);

    List<ResponseUserDto> getAllUsers();
}
