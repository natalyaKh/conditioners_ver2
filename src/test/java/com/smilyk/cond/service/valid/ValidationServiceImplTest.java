package com.smilyk.cond.service.valid;

import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.exceptions.InvalidUserException;
import com.smilyk.cond.exceptions.ObjectNotFoundException;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.service.general.GeneralUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
/**
 * Tests for {@link ValidationServiceImpl}
 */
class ValidationServiceImplTest {
    private static final String ENCRYPTED_PASSWORD = "1234";
    private static final String USER_UUID = "1111";
    private static final String USER_FIRST_NAME = "FirstName";
    private static final String USER_SECOND_NAME = "SecondName";
    private static final String USER_EMAIL = "mail@mail.com";
    private static final List<String> USER_ROLES_STRING = new ArrayList<>();
    private static final Collection<RoleEntity> ROLES = new ArrayList<>();

    private Optional<UserEntity> returnCacheValueUser;
    private UserEntity userEntity;

    @InjectMocks
    ValidationServiceImpl validatorService;
    @Mock
    UserEntityRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setUserEmail(USER_EMAIL);
        userEntity.setSecondName(USER_SECOND_NAME);
        userEntity.setFirstName(USER_FIRST_NAME);
        userEntity.setRoles(ROLES);
        userEntity.setUuidUser(USER_UUID);
        userEntity.setPassword(ENCRYPTED_PASSWORD);
        returnCacheValueUser = Optional.of(userEntity);
    }
    @Test
    void testCheckUserUniqueNotValid() {
//        Optional<UserEntity> userEntity = userEntityRepository.getByUserEmail(userEmail);
        when(userRepository.getByUserEmail(anyString()))
            .thenReturn(Optional.ofNullable(userEntity));
        assertThrows(InvalidUserException.class, () -> validatorService.checkUniqueUser(USER_EMAIL));
    }

    @Test
    void testCheckUserUniqueValid() {
        when(userRepository.getByUserEmail(anyString()))
            .thenReturn(Optional.empty());
        validatorService.checkUniqueUser(USER_EMAIL);
    }

    @Test
    void testCheckUserExistsNotValid() {
        when(userRepository.getUserByUuidUserAndDeletedAndBlocked(anyString(), eq(false),eq(false)))
            .thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> validatorService.checkIfUserExists(USER_UUID));
    }

    @Test
    void testCheckUserExistsValid() {
        when(userRepository.getUserByUuidUserAndDeletedAndBlocked(anyString(), eq(false),eq(false)))
            .thenReturn(returnCacheValueUser);
        validatorService.checkIfUserExists(USER_UUID);
    }
}

