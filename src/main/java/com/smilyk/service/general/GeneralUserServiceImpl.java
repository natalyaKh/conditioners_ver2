package com.smilyk.service.general;

import com.smilyk.constants.LoggerConstants;
import com.smilyk.dto.ResponseUserDto;
import com.smilyk.dto.UpdateUserDto;
import com.smilyk.model.RoleEntity;
import com.smilyk.model.UserEntity;
import com.smilyk.repo.UserEntityRepository;
import com.smilyk.service.admin.AdminUserServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * implementation of {@link GeneralUserService}
 * class contains methods for permit some roles
 */
@Service
public class GeneralUserServiceImpl implements GeneralUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    final UserEntityRepository userRepository;

    public GeneralUserServiceImpl(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseUserDto getUserByUuid(UserEntity userEntity) {
        ResponseUserDto responseUserDto = modelMapper.map(userEntity, ResponseUserDto.class);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail() +
            LoggerConstants.WAS_SEND_TO_CLIENT);
        return responseUserDto;
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        LOGGER.info(LoggerConstants.LIST_OF_USERS + LoggerConstants.WAS_SEND_TO_CLIENT);
        List<ResponseUserDto> responseUserDtoList = userEntityList.stream().map(this::userEntityToResponseUserDto)
            .collect(Collectors.toList());
        return responseUserDtoList;
    }

    @Override
    public ResponseUserDto updateUserByUuid(UserEntity userEntity, UpdateUserDto updateUserDto) {
        userEntity = checkFieldsForUpdatedUser(userEntity, updateUserDto);
        UserEntity restoredUserEntity = userRepository.save(userEntity);
        ResponseUserDto responseUserDto = modelMapper.map(restoredUserEntity, ResponseUserDto.class);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail() +
            LoggerConstants.WAS_UPDATED);
        return responseUserDto;
    }

    public ResponseUserDto userEntityToResponseUserDto(UserEntity restoredUser) {
        ResponseUserDto responseUserDto = modelMapper.map(restoredUser, ResponseUserDto.class);
        List<String> usersRoles = restoredUser.getRoles().stream().map(RoleEntity::getName)
            .collect(Collectors.toList());
        responseUserDto.setRoles(usersRoles);
        return responseUserDto;
    }

    UserEntity checkFieldsForUpdatedUser(UserEntity userEntity, UpdateUserDto updateUserDto) {
        if (updateUserDto.getFirstName() != null) {
            userEntity.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getSecondName() != null) {
            userEntity.setSecondName(updateUserDto.getSecondName());
        }
        if (updateUserDto.getUserEmail() != null) {
            userEntity.setUserEmail(updateUserDto.getUserEmail());
        }
        return userEntity;
    }
}
