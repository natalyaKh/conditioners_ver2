package com.smilyk.cond.controllers;

import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.service.general.GeneralUserService;
import com.smilyk.cond.service.valid.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("ver1/profile")
public class ProfileUserController {
    @Autowired
    ValidationService validService;
    @Autowired
    GeneralUserService generalService;

    @GetMapping("/{userUuid}")
    public ResponseEntity getUserByUuid(@PathVariable String userUuid){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = generalService.getUserByUuid(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    @PutMapping("/{userUuid}")
    public ResponseEntity updateUserByUuid(@RequestBody @Valid UpdateUserDto updateUserDto){
        UserEntity userEntity = validService.checkIfUserExists(updateUserDto.getUserUuid());
        ResponseUserDto restoredUserDto = generalService.updateUserByUuid(userEntity, updateUserDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }
}
