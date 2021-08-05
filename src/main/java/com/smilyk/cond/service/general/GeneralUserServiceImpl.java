package com.smilyk.cond.service.general;

import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.service.admin.AdminUserServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    public ResponseUserDto updateUserByUuid(UserEntity userEntity, UpdateUserDto updateUserDto) {
        userEntity = checkFieldsForUpdatedUser(userEntity, updateUserDto);
        UserEntity restoredUserEntity = userRepository.save(userEntity);
        ResponseUserDto responseUserDto = modelMapper.map(restoredUserEntity, ResponseUserDto.class);
        LOGGER.info(LoggerConstants.USER_WITH_EMAIL + userEntity.getUserEmail() +
            LoggerConstants.WAS_UPDATED);
        return responseUserDto;
    }

    private UserEntity checkFieldsForUpdatedUser(UserEntity userEntity, UpdateUserDto updateUserDto) {
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
