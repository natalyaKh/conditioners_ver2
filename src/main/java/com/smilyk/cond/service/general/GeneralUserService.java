package com.smilyk.cond.service.general;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.model.UserEntity;

import java.util.List;

public interface GeneralUserService {
    ResponseUserDto getUserByUuid(UserEntity userEntity);

    ResponseUserDto updateUserByUuid(UserEntity userEntity, UpdateUserDto updateUserDto);

    List<ResponseUserDto> getAllUsers();
}
