package com.smilyk.cond.service.valid;

import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.exceptions.InvalidUserException;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.service.admin.AdminUserServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    UserEntityRepository userEntityRepository;
    @SneakyThrows
    @Override
    public void checkUniqueUser(String userEmail) {
        Optional<UserEntity> userEntity = userEntityRepository.getByUserEmail(userEmail);
        if(userEntity.isPresent()){
            LOGGER.warn(LoggerConstants.USER_WITH_EMAIL + userEmail + LoggerConstants.EXISTS);
            throw new InvalidUserException(
                LoggerConstants.USER_WITH_EMAIL + userEmail + LoggerConstants.EXISTS
            );
        }

    }
}
