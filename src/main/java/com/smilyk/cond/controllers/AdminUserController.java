package com.smilyk.cond.controllers;

import com.smilyk.cond.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.service.admin.AdminUserService;
import com.smilyk.cond.service.valid.ValidationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ver1/admin")
public class AdminUserController {

    @Autowired
    AdminUserService userService;
    @Autowired
    ValidationService validService;

    // TODO: 01/08/2021 Admin only
    @SneakyThrows
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto){
        validService.checkUniqueUser(userDto.getUserEmail());
        ResponseUserDto restoredUserDto = userService.createUser(userDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userUuid}")
    public ResponseEntity deleteUser(@PathVariable String userUuid){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = userService.deleteUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/{userUuid}")
    public ResponseEntity blockUser(@PathVariable String userUuid){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = userService.blockUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/role/add/{userUuid}/{role}")
    public ResponseEntity addRoleToUser(@PathVariable String userUuid, @PathVariable Roles role){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = userService.addRoleToUser(userEntity, role);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }
}
