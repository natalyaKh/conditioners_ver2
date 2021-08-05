package com.smilyk.cond.controllers;

import com.smilyk.cond.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.service.admin.AdminUserService;
import com.smilyk.cond.service.general.GeneralUserService;
import com.smilyk.cond.service.valid.ValidationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ver1/admin")
public class AdminUserController {

    @Autowired
    AdminUserService adminService;
    @Autowired
    GeneralUserService generalService;
    @Autowired
    ValidationService validService;

    // TODO: 01/08/2021 Admin only
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        validService.checkUniqueUser(userDto.getUserEmail());
        ResponseUserDto restoredUserDto = adminService.createUser(userDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity getUserByUuid(@PathVariable String userUuid){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = generalService.getUserByUuid(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllUsers(){
        List<ResponseUserDto> userEntityList =generalService.getAllUsers();
        return new ResponseEntity(userEntityList, HttpStatus.OK);
    }

    @DeleteMapping("/{userUuid}")
    public ResponseEntity deleteUser(@PathVariable String userUuid) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = adminService.deleteUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/{userUuid}")
    public ResponseEntity blockUser(@PathVariable String userUuid) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = adminService.blockUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/role/add/{userUuid}/{role}")
    public ResponseEntity addRoleToUser(@PathVariable String userUuid, @PathVariable Roles role) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = adminService.addRoleToUser(userEntity, role);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("role/delete/{userUuid}/{role}")
    public ResponseEntity deleteRoleFromUser(@PathVariable String userUuid,
                                             @PathVariable Roles role) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = adminService.deleteRoleFromUser(userEntity, role);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/{userUuid}")
    public ResponseEntity updateUserByUuid(@RequestBody @Valid UpdateUserDto updateUserDto){
        UserEntity userEntity = validService.checkIfUserExists(updateUserDto.getUserUuid());
        ResponseUserDto restoredUserDto = generalService.updateUserByUuid(userEntity, updateUserDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }
}
