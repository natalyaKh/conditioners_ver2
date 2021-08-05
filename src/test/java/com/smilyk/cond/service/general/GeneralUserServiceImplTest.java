package com.smilyk.cond.service.general;

import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UpdateUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.service.admin.AdminUserService;
import com.smilyk.cond.service.admin.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link GeneralUserService}
 */
class GeneralUserServiceImplTest {

    private static final String ENCRYPTED_PASSWORD = "1234";
    private static final String USER_UUID = "1111";
    private static final String USER_FIRST_NAME = "FirstName";
    private static final String USER_SECOND_NAME = "SecondName";
    private static final String USER_EMAIL = "mail@mail.com";
    private static final List<String> USER_ROLES_STRING = new ArrayList<>();
    private static final Collection<RoleEntity> ROLES = new ArrayList<>();

    private static final String UPDATED_USER_FIRST_NAME = "FirstNameUPDATED";
    private static final String UPDATED_USER_SECOND_NAME = "SecondNameUPDATED";
    private static final String UPDATED_USER_EMAIL = "updatedmail@mail.com";


    private UpdateUserDto updateUserDto;
    private UserEntity userEntity;
    private UserEntity updatedUserEntity;
    private List<UserEntity> listUserEntity = new ArrayList<>();
    private ResponseUserDto responseUserDto;
    private ResponseUserDto updatedResponseUserDto;
    private List<ResponseUserDto> responseUserDtoList = new ArrayList<>();

    @InjectMocks
    GeneralUserServiceImpl generalService;
    @Mock
    private UserEntityRepository userRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_ADMIN");
        ROLES.add(roleEntity);
        USER_ROLES_STRING.add("ROLE_ADMIN");
        userEntity = new UserEntity();
        userEntity.setUserEmail(USER_EMAIL);
        userEntity.setSecondName(USER_SECOND_NAME);
        userEntity.setFirstName(USER_FIRST_NAME);
        userEntity.setRoles(ROLES);
        userEntity.setUuidUser(USER_UUID);
        userEntity.setPassword(ENCRYPTED_PASSWORD);

        updatedUserEntity = new UserEntity();
        updatedUserEntity.setUserEmail(UPDATED_USER_EMAIL);
        updatedUserEntity.setSecondName(UPDATED_USER_SECOND_NAME);
        updatedUserEntity.setFirstName(UPDATED_USER_FIRST_NAME);
        updatedUserEntity.setRoles(ROLES);
        updatedUserEntity.setUuidUser(USER_UUID);
        updatedUserEntity.setPassword(ENCRYPTED_PASSWORD);

        updateUserDto = UpdateUserDto.builder()
            .userEmail(UPDATED_USER_EMAIL)
            .firstName(USER_FIRST_NAME)
            .secondName(USER_SECOND_NAME)
            .userUuid(USER_UUID)
            .build();

        listUserEntity.add(userEntity);

        responseUserDto = ResponseUserDto.builder()
            .userUuid(USER_UUID)
            .secondName(USER_SECOND_NAME)
            .firstName(USER_FIRST_NAME)
            .roles(USER_ROLES_STRING)
            .build();
        updatedResponseUserDto = ResponseUserDto.builder()
            .userUuid(USER_UUID)
            .secondName(UPDATED_USER_SECOND_NAME)
            .firstName(UPDATED_USER_FIRST_NAME)
            .roles(USER_ROLES_STRING)
            .build();

        responseUserDtoList.add(responseUserDto);
    }


    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(listUserEntity);

        List<ResponseUserDto> restoredUsers = generalService.getAllUsers();

        assertEquals(responseUserDtoList.size(), restoredUsers.size());

    }

    @Test
    void userEntityToResponseUserDto() {
        ResponseUserDto changedResponseUserDto = generalService.userEntityToResponseUserDto(userEntity);
        assertNotNull(changedResponseUserDto);

        assertEquals(responseUserDto.getUserUuid(), changedResponseUserDto.getUserUuid());
        assertEquals(responseUserDto.getFirstName(), changedResponseUserDto.getFirstName());
        assertEquals(responseUserDto.getSecondName(), changedResponseUserDto.getSecondName());
        assertEquals(responseUserDto.getRoles(), changedResponseUserDto.getRoles());
    }

    @Test
    void updateUserByUuid() {
//        UserEntity restoredUserEntity = userRepository.save(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);
        ResponseUserDto rez = generalService.updateUserByUuid(userEntity,updateUserDto);

        assertEquals(updatedResponseUserDto.getFirstName(), rez.getFirstName());
        assertEquals(updatedResponseUserDto.getSecondName(), rez.getSecondName());
    }

}
