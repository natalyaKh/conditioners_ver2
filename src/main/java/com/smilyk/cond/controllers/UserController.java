package com.smilyk.cond.controllers;

import com.smilyk.cond.InitialRolesAuthoritiesSetup;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Authorities;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.AuthorityEntity;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("ver1/users")
public class UserController {

    @Autowired
    UserService userService;

    //Admin only
    // TODO: 31/07/2021 Validation - check if user exist in DB - return error
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto){
        ResponseUserDto restoredUserDto = userService.createUser(userDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.CREATED);
    }
}
