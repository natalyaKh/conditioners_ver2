package com.smilyk.cond.service.admin;

import com.smilyk.cond.InitialRolesAuthoritiesSetup;
import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.exceptions.ObjectNotFoundException;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.RoleRepository;
import com.smilyk.cond.repo.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    final UserEntityRepository userRepository;
    final RoleRepository roleRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final InitialRolesAuthoritiesSetup initialUsersSetup;

    public AdminUserServiceImpl(UserEntityRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, InitialRolesAuthoritiesSetup initialUsersSetup) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.initialUsersSetup = initialUsersSetup;
    }

    @Override
    public ResponseUserDto createUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUuidUser(UUID.randomUUID().toString());
        RoleEntity roleEntity = roleRepository.findByName(userDto.getRoles().name());
        if(roleEntity.equals(null)){
            LOGGER.warn(LoggerConstants.ROLE + userDto.getRoles().name() +
               LoggerConstants.NOT_FOUND_IN_DB);
           throw new ObjectNotFoundException(LoggerConstants.ROLE + userDto.getRoles().name() +
               LoggerConstants.NOT_FOUND_IN_DB);
        }
        ArrayList<RoleEntity> roles = new ArrayList<>();
        roles.add(roleEntity);
        userEntity.setRoles(roles);
        UserEntity restoredUser = userRepository.save(userEntity);
        if(restoredUser.equals(null)){
            throw new ObjectNotFoundException(LoggerConstants.SOMETHING_WAS_WRONG + " during saving user");
        }
        LOGGER.info(LoggerConstants.USER_WITH_NAME + userDto.getFirstName() + " and "  +
            LoggerConstants.USER_WITH_ROLES +
            userDto.getRoles() + LoggerConstants.CREATED);
        ResponseUserDto responseUserDto = modelMapper.map(restoredUser, ResponseUserDto.class);
        responseUserDto.setRoles(restoredUser.getRoles().iterator().next().getName());
        return responseUserDto;
    }

    @Override
    public ResponseDeleteBlockedUserDto deleteUser(UserEntity userEntity) {
        userEntity.setDeleted(true);
        UserEntity restoredUser = userRepository.save(userEntity);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail()+
            LoggerConstants.DELETED);
      return  modelMapper.map(restoredUser, ResponseDeleteBlockedUserDto.class);
    }

    @Override
    public ResponseDeleteBlockedUserDto blockUser(UserEntity userEntity) {
        userEntity.setBlocked(true);
        UserEntity restoredUser = userRepository.save(userEntity);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail()+
            LoggerConstants.BLOCKED);
        return  modelMapper.map(restoredUser, ResponseDeleteBlockedUserDto.class);
    }
}
