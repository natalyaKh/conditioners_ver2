package com.smilyk.controllers.users.users;

import com.smilyk.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UpdateUserDto;
import com.smilyk.dto.UserDto;
import com.smilyk.enums.Roles;
import com.smilyk.exceptions.InvalidUserException;
import com.smilyk.model.UserEntity;
import com.smilyk.exceptions.ObjectNotFoundException;
import com.smilyk.service.admin.AdminUserService;
import com.smilyk.service.general.GeneralUserService;
import com.smilyk.service.valid.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * class Controller for users with Role = ROLE_ADMIN
 * all other users don have access to this EndPoints
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("ver1/admin")
public class AdminUserController {

    @Autowired
    AdminUserService adminService;
    @Autowired
    GeneralUserService generalService;
    @Autowired
    ValidationService validService;

    /**
     * methods that create {@link UserEntity}
     * method can throw {@link InvalidUserException} with {@link HttpStatus}
     * 409.CONFLICT if user exists in DB
     * @param userDto {@link UserDto}
     * @return {@link ResponseUserDto}
     */
    // TODO: 01/08/2021 Admin only
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        validService.checkUniqueUser(userDto.getUserEmail());
        ResponseUserDto restoredUserDto = adminService.createUser(userDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.CREATED);
    }

    /**
     * method that get specific user from DB
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid unique users id
     * @return {@link ResponseUserDto} by uuid of user
     */
    @GetMapping("/{userUuid}")
    public ResponseEntity getUserByUuid(@PathVariable String userUuid){
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = generalService.getUserByUuid(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    /**
     * method that return all users from DB
     * @return  list of {@link ResponseUserDto}
     */
    @GetMapping
    public ResponseEntity getAllUsers(){
        List<ResponseUserDto> userEntityList =generalService.getAllUsers();
        return new ResponseEntity(userEntityList, HttpStatus.OK);
    }

    /**
     * method that delete specific user and return {@link ResponseDeleteBlockedUserDto}
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid
     * @return {@link ResponseDeleteBlockedUserDto}
     */
    @DeleteMapping("/{userUuid}")
    public ResponseEntity deleteUser(@PathVariable String userUuid) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = adminService.deleteUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    /**
     * method that block specific user
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid
     * @return {@link ResponseDeleteBlockedUserDto}
     */
    @PutMapping("/{userUuid}")
    public ResponseEntity blockUser(@PathVariable String userUuid) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseDeleteBlockedUserDto restoredUserDto = adminService.blockUser(userEntity);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    /**
     * method that add role to specific user
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid
     * @param role
     * @return {@link ResponseUserDto}
     */
    @PutMapping("/role/add/{userUuid}/{role}")
    public ResponseEntity addRoleToUser(@PathVariable String userUuid, @PathVariable Roles role) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = adminService.addRoleToUser(userEntity, role);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }


    /**
     * method that delete role from specific user
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid {@link UserDto}
     * @param role {@link Roles}
     * @return {@link ResponseUserDto}
     */
    @PutMapping("role/delete/{userUuid}/{role}")
    public ResponseEntity deleteRoleFromUser(@PathVariable String userUuid,
                                             @PathVariable Roles role) {
        UserEntity userEntity = validService.checkIfUserExists(userUuid);
        ResponseUserDto restoredUserDto = adminService.deleteRoleFromUser(userEntity, role);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }

    /**
     * method that update specific user
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param updateUserDto
     * @return {@link ResponseUserDto}
     */
    @PutMapping("update/{userUuid}")
    public ResponseEntity updateUserByUuid(@RequestBody @Valid UpdateUserDto updateUserDto){
        UserEntity userEntity = validService.checkIfUserExists(updateUserDto.getUserUuid());
        ResponseUserDto restoredUserDto = generalService.updateUserByUuid(userEntity, updateUserDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }
}
