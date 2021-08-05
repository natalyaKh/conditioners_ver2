package com.smilyk.cond.service.admin;

import com.smilyk.cond.dto.ResponseDeleteBlockedUserDto;
import com.smilyk.cond.dto.ResponseUserDto;
import com.smilyk.cond.dto.UserDto;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.AuthorityEntity;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.RoleRepository;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.service.general.GeneralUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AdminUserService}
 */
class AdminUserServiceImplTest {

    private static final String ENCRYPTED_PASSWORD = "1234";
    private static final String USER_UUID = "1111";
    private static final String USER_FIRST_NAME = "FirstName";
    private static final String USER_SECOND_NAME = "SecondName";
    private static final Roles USER_ROLES = Roles.ROLE_ADMIN;
    private static final String USER_EMAIL = "mail@mail.com";
    private static final List<String> USER_ROLES_STRING = new ArrayList<>();
    private static final Collection<RoleEntity> ROLES = new ArrayList<>();
    private static final Collection<RoleEntity> TWO_ROLES = new ArrayList<>();
    private static final List<String> TWO_USER_ROLES_STRING = new ArrayList<>();
    private UserDto userDto;
    private ResponseUserDto responseUserDto;
    private ResponseUserDto responseUserTwoRolesDto;
    private UserEntity userEntity;
    private UserEntity deletedUserEntity;
    private UserEntity blockedUserEntity;
    private  UserEntity twoRolesUserEntity;

    RoleEntity roleEntity = new RoleEntity();
    RoleEntity secondRoleEntity = new RoleEntity();
    @InjectMocks
    AdminUserServiceImpl adminService;
    @Mock
    GeneralUserServiceImpl generalService;
    @Mock
    UserEntityRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

        deletedUserEntity = new UserEntity();
        deletedUserEntity.setUserEmail(USER_EMAIL);
        deletedUserEntity.setSecondName(USER_SECOND_NAME);
        deletedUserEntity.setFirstName(USER_FIRST_NAME);
        deletedUserEntity.setRoles(TWO_ROLES);
        deletedUserEntity.setUuidUser(USER_UUID);
        deletedUserEntity.setPassword(ENCRYPTED_PASSWORD);
        deletedUserEntity.setDeleted(true);

        blockedUserEntity = new UserEntity();
        blockedUserEntity.setUserEmail(USER_EMAIL);
        blockedUserEntity.setSecondName(USER_SECOND_NAME);
        blockedUserEntity.setFirstName(USER_FIRST_NAME);
        blockedUserEntity.setRoles(ROLES);
        blockedUserEntity.setUuidUser(USER_UUID);
        blockedUserEntity.setPassword(ENCRYPTED_PASSWORD);
        blockedUserEntity.setBlocked(true);

        Collection<UserEntity> userRoles = new ArrayList<>();
        userRoles.add(new UserEntity());
        roleEntity.setUserEntity(userRoles);
        Collection<AuthorityEntity> userAuthorities;
        secondRoleEntity.setName("ROLE_MANAGER");
        TWO_ROLES.add(secondRoleEntity);
        TWO_ROLES.add(roleEntity);
        TWO_USER_ROLES_STRING.add("ROLE_ADMIN");
        TWO_USER_ROLES_STRING.add("ROLE_MANAGER");
        responseUserTwoRolesDto = ResponseUserDto.builder()
            .userUuid(USER_UUID)
            .firstName(USER_FIRST_NAME)
            .secondName(USER_SECOND_NAME)
            .roles(TWO_USER_ROLES_STRING)
            .build();

        responseUserDto = ResponseUserDto.builder()
            .userUuid(USER_UUID)
            .firstName(USER_FIRST_NAME)
            .secondName(USER_SECOND_NAME)
            .roles(USER_ROLES_STRING)
            .build();

        twoRolesUserEntity = new UserEntity();
        twoRolesUserEntity.setUserEmail(USER_EMAIL);
        twoRolesUserEntity.setSecondName(USER_SECOND_NAME);
        twoRolesUserEntity.setFirstName(USER_FIRST_NAME);
        twoRolesUserEntity.setRoles(TWO_ROLES);
        twoRolesUserEntity.setUuidUser(USER_UUID);
        twoRolesUserEntity.setPassword(ENCRYPTED_PASSWORD);

        userDto = UserDto.builder()
            .userUuid(USER_UUID)
            .firstName(USER_FIRST_NAME)
            .secondName(USER_SECOND_NAME)
            .password(ENCRYPTED_PASSWORD)
            .roles(USER_ROLES)
            .userEmail(USER_EMAIL)
            .build();
    }

    @Test
    void createUser() {
        when(roleRepository.findByName(anyString())).thenReturn(roleEntity);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(generalService.userEntityToResponseUserDto(any(UserEntity.class))).thenReturn(responseUserDto);

        ResponseUserDto savedUser = adminService.createUser(userDto);

        assertNotNull(savedUser);
        assertEquals(userEntity.getFirstName(), savedUser.getFirstName());
        assertNotNull(savedUser.getUserUuid());
        userDto.setPassword("");
//        checking how times called method
        verify(bCryptPasswordEncoder, times(1)).encode(ENCRYPTED_PASSWORD);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {

//        UserEntity restoredUser = userRepository.save(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(deletedUserEntity);
        ResponseDeleteBlockedUserDto deletedUser = adminService.deleteUser(userEntity);

        assertNotNull(deletedUser);
        assertEquals(userEntity.getFirstName(), deletedUser.getFirstName());
//        checking how times called method
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void blockUser() {
//      UserEntity restoredUser = userRepository.save(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(blockedUserEntity);
        ResponseDeleteBlockedUserDto blockedUser = adminService.deleteUser(userEntity);

        assertNotNull(blockedUser);
        assertEquals(userEntity.getFirstName(), blockedUser.getFirstName());
//      checking how times called method
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void addRoleToUser() {
//        RoleEntity roleEntity = roleRepository.findByName(role.name());
        when(roleRepository.findByName(anyString())).thenReturn(roleEntity);
//        restoredUser = userRepository.save(userEntity);

        when(userRepository.save(any(UserEntity.class))).thenReturn(twoRolesUserEntity);
        when(generalService.userEntityToResponseUserDto(any(UserEntity.class))).thenReturn(responseUserTwoRolesDto);

        ResponseUserDto changedUser = adminService.addRoleToUser(userEntity, Roles.ROLE_MANAGER);
        assertNotNull(changedUser);
        assertEquals(responseUserTwoRolesDto, changedUser);
//      checking how times called method
        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

    @Test
    void deleteRoleFromUser() {
        when(roleRepository.findByName(anyString())).thenReturn(secondRoleEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(generalService.userEntityToResponseUserDto(any(UserEntity.class))).thenReturn(responseUserDto);

        ResponseUserDto changedUser = adminService.deleteRoleFromUser(twoRolesUserEntity, Roles.ROLE_MANAGER);
        assertNotNull(changedUser);
        assertEquals(responseUserDto, changedUser);
//      checking how times called method
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
