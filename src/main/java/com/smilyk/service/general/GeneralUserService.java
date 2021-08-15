package com.smilyk.service.general;

import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UpdateUserDto;
import com.smilyk.model.UserEntity;

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
