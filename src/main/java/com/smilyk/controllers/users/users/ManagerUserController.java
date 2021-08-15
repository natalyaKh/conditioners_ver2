package com.smilyk.controllers.users.users;

import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UpdateUserDto;
import com.smilyk.model.UserEntity;
import com.smilyk.service.general.GeneralUserService;
import com.smilyk.service.valid.ValidationService;
import com.smilyk.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * class Controller for users with Role = ROLE_MANAGER
 * all other users don have access to this EndPoints
 */
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RestController
@RequestMapping("ver1/manager")
public class ManagerUserController {

    @Autowired
    ValidationService validService;
    @Autowired
    GeneralUserService generalService;

    /**
     * method that get specific user from DB
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param userUuid
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
    @GetMapping()
    public ResponseEntity getAllUsers(){
        List<ResponseUserDto>  userEntityList =generalService.getAllUsers();
        return new ResponseEntity(userEntityList, HttpStatus.OK);
    }

    /**
     * method that update specific user
     * method can throw {@link ObjectNotFoundException} with {@link HttpStatus}
     * 410.CONE. if user not exists in DB
     * @param updateUserDto
     * @return
     */
    @PutMapping("/{userUuid}")
    public ResponseEntity updateUserByUuid(@RequestBody @Valid UpdateUserDto updateUserDto){
        UserEntity userEntity = validService.checkIfUserExists(updateUserDto.getUserUuid());
        ResponseUserDto restoredUserDto = generalService.updateUserByUuid(userEntity, updateUserDto);
        return new ResponseEntity(restoredUserDto, HttpStatus.OK);
    }




}
