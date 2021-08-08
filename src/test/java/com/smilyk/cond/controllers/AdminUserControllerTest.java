package com.smilyk.cond.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class AdminUserControllerTest {
    @Autowired
    WebApplicationContext wac;
//    @Mock
//    AdminUserService adminService;
//    @Mock
//    GeneralUserService generalService;
//    @Mock
//    ValidationService validService;
//    @Mock
//    UserSecurityService userSecurityService;

    protected MockMvc mockMvc;
//    @InjectMocks
//    AdminUserController controller;
//    @Mock
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .webAppContextSetup( wac)
            .apply(springSecurity())
            .build();
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void createUserFromAdmin() {
        try {
            mockMvc.perform(post("")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void createUserFromManager() {
        try {
            mockMvc.perform(post("")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void createUserFromWorker() {
        try {
            mockMvc.perform(post("")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void getUserByUuidFromAdmin() {
        try {
            mockMvc.perform(get("ver1/admin/1")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void getUserByUuidFromManager() {
        try {
            mockMvc.perform(get("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void getUserByUuidFromWorker() {
        try {
            mockMvc.perform(get("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void getAllUsersFromAdmin() {
        try {
            mockMvc.perform(get("ver1/admin")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void getAllUsersFromManager() {
        try {
            mockMvc.perform(get("ver1/admin")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void getAllUsersFromWorker() {
        try {
            mockMvc.perform(get("ver1/admin")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void deleteUserFromAdmin() {
        try {
            mockMvc.perform(delete("ver1/admin/1")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void deleteUserFromManager() {
        try {
            mockMvc.perform(delete("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void deleteUserFromWorker() {
        try {
            mockMvc.perform(delete("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void blockUserFromAdmin() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void blockUserFromManager() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void blockUserFromWorker() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void addRoleToUserFromAdmin() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void addRoleToUserFromManager() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void addRoleToUserFromWorker() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void deleteRoleFromUserFromAdmin() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void deleteRoleFromUserFromManager() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void deleteRoleFromUserFromWorker() {
        try {
            mockMvc.perform(put("ver1/admin/1/ADMIN")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void updateUserFromAdmin() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void updateUserFromManager() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void updateUserFromWorker() {
        try {
            mockMvc.perform(put("ver1/admin/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
