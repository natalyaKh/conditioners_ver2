package com.smilyk.cond.service;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;

public interface UserService {
    ResponseUserDto createUser(UserDto userDto);
}
