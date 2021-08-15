package com.smilyk.service.admin;

import com.smilyk.InitialRolesAuthoritiesSetup;
import com.smilyk.constants.LoggerConstants;
import com.smilyk.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UserDto;
import com.smilyk.enums.Roles;
import com.smilyk.exceptions.InvalidUserException;
import com.smilyk.exceptions.ObjectNotFoundException;
import com.smilyk.model.RoleEntity;
import com.smilyk.model.UserEntity;
import com.smilyk.repo.RoleRepository;
import com.smilyk.repo.UserEntityRepository;
import com.smilyk.service.general.GeneralUserServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * implementation of {@link AdminUserService}
 * class contains methods for permit ADMINISTRATOR only
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    final GeneralUserServiceImpl generalService;
    final UserEntityRepository userRepository;
    final RoleRepository roleRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final InitialRolesAuthoritiesSetup initialUsersSetup;

    public AdminUserServiceImpl(GeneralUserServiceImpl generalService, UserEntityRepository userRepository,
                                RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                InitialRolesAuthoritiesSetup initialUsersSetup) {
        this.generalService = generalService;
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
        if (roleEntity.equals(null)) {
            LOGGER.warn(LoggerConstants.ROLE + userDto.getRoles().name() +
                LoggerConstants.NOT_FOUND_IN_DB);
            throw new ObjectNotFoundException(LoggerConstants.ROLE + userDto.getRoles().name() +
                LoggerConstants.NOT_FOUND_IN_DB);
        }
        ArrayList<RoleEntity> roles = new ArrayList<>();
        roles.add(roleEntity);
        userEntity.setRoles(roles);
        UserEntity restoredUser = userRepository.save(userEntity);
        checkSavingUserWithChanges(restoredUser, " during saving user");
        LOGGER.info(LoggerConstants.USER_WITH_NAME + userDto.getFirstName() + " and " +
            LoggerConstants.USER_WITH_ROLES +
            userDto.getRoles() + LoggerConstants.CREATED);
        return generalService.userEntityToResponseUserDto(restoredUser);
    }

    @Override
    public ResponseDeleteBlockedUserDto deleteUser(UserEntity userEntity) {
        userEntity.setDeleted(true);
        UserEntity restoredUser = userRepository.save(userEntity);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail() +
            LoggerConstants.DELETED);
        return modelMapper.map(restoredUser, ResponseDeleteBlockedUserDto.class);
    }

    @Override
    public ResponseDeleteBlockedUserDto blockUser(UserEntity userEntity) {
        userEntity.setBlocked(true);
        UserEntity restoredUser = userRepository.save(userEntity);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail() +
            LoggerConstants.BLOCKED);
        return modelMapper.map(restoredUser, ResponseDeleteBlockedUserDto.class);
    }

    @Override
    public ResponseUserDto addRoleToUser(UserEntity userEntity, Roles role) {
        RoleEntity roleEntity = roleRepository.findByName(role.name());
        UserEntity restoredUser = new UserEntity();
        if (roleEntity.equals(null)) {
            throw new ObjectNotFoundException(LoggerConstants.ROLE + role.name() +
                LoggerConstants.NOT_FOUND_IN_DB);
        }
        Collection<RoleEntity> roles = userEntity.getRoles();
        if (roles.contains(roleEntity)) {
            throw new InvalidUserException(LoggerConstants.USER_WITH_NAME + userEntity.getFirstName()
                + " " + userEntity.getSecondName() + LoggerConstants.HAS_ROLE + role.name());
        }
        roles.add(roleEntity);
        userEntity.setRoles(roles);
        restoredUser = userRepository.save(userEntity);
        checkSavingUserWithChanges(restoredUser, " during saving role to user");
        LOGGER.info(LoggerConstants.ROLE + role.name() + LoggerConstants.WAS_ADD +
            LoggerConstants.USER_WITH_NAME +
            restoredUser.getFirstName() + " " + restoredUser.getSecondName());
        return generalService.userEntityToResponseUserDto(restoredUser);
    }

    @Override
    public ResponseUserDto deleteRoleFromUser(UserEntity userEntity, Roles role) {
        RoleEntity roleEntity = roleRepository.findByName(role.name());
        if (roleEntity.equals(null)) {
            throw new ObjectNotFoundException(LoggerConstants.ROLE + role.name() +
                LoggerConstants.NOT_FOUND_IN_DB);
        }
        Collection<RoleEntity> roles = userEntity.getRoles();
        if (!roles.contains(roleEntity)) {
            throw new InvalidUserException(LoggerConstants.USER_WITH_NAME + userEntity.getFirstName()
                + " " + userEntity.getSecondName() + LoggerConstants.HAS_NO_ROLE + role.name());
        }
        roles.remove(roleEntity);
        userEntity.setRoles(roles);
        UserEntity restoredUser = userRepository.save(userEntity);
        checkSavingUserWithChanges(restoredUser, " during saving role to user");
        LOGGER.info(LoggerConstants.ROLE + role.name() + LoggerConstants.WAS_DELETED +
            LoggerConstants.USER_WITH_NAME +
            restoredUser.getFirstName() + " " + restoredUser.getSecondName());
        return generalService.userEntityToResponseUserDto(restoredUser);

    }

    private void checkSavingUserWithChanges(UserEntity restoredUser, String s) {
        if (restoredUser.equals(null)) {
            throw new ObjectNotFoundException(LoggerConstants.SOMETHING_WAS_WRONG + s);
        }
    }
}
