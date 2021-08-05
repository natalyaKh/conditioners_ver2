package com.smilyk.cond.service.general;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.model.UserEntity;

public interface GeneralUserService {
    ResponseUserDto getUserByUuid(UserEntity userEntity);

    ResponseUserDto updateUserByUuid(UserEntity userEntity, UpdateUserDto updateUserDto);
}
