package com.smilyk.cond.service.admin;

import com.smilyk.cond.InitialRolesAuthoritiesSetup;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
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
            // TODO: 31/07/2021 exception
        }
        ArrayList<RoleEntity> roles = new ArrayList<>();
        roles.add(roleEntity);
        userEntity.setRoles(roles);
        UserEntity restoredUser = userRepository.save(userEntity);
        if(restoredUser.equals(null)){
            // TODO: 31/07/2021 throw exception
        }
        LOGGER.info("User with name: " + userDto.getFirstName() + " and with roles: " +
            userDto.getRoles() + " was created successfully");
        ResponseUserDto responseUserDto = modelMapper.map(restoredUser, ResponseUserDto.class);
        responseUserDto.setRoles(restoredUser.getRoles().iterator().next().getName());
        return responseUserDto;
    }
}
