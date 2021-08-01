package com.smilyk.cond.service.admin;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;

public interface AdminUserService {
    ResponseUserDto createUser(UserDto userDto);
}
