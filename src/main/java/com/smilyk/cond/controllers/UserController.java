package com.smilyk.cond.controllers;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ver1/admin")
public class UserController {

    @Autowired
    AdminUserService userService;

    // TODO: 01/08/2021 Admin only
    // TODO: 31/07/2021 Validation - check if user exist in DB - return error
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto){

        ResponseUserDto restoredUserDto = userService.createUser(userDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.CREATED);
    }
}
