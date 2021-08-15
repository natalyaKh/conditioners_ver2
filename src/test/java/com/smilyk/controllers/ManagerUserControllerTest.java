package com.smilyk.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ManagerUserControllerTest {
    @Autowired
    WebApplicationContext wac;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders
            .webAppContextSetup( wac)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void getUserByUuidFromAdmin() {
        try {
            mockMvc.perform(get("ver1/manager/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void getUserByUuidFromManager() {
        try {
            mockMvc.perform(get("ver1/manager/1")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void getUserByUuidFromWorker() {
        try {
            mockMvc.perform(get("ver1/manager/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void getAllUsersFromAdmin() {
        try {
            mockMvc.perform(get("ver1/manager")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void getAllUsersFromManager() {
        try {
            mockMvc.perform(get("ver1/manager")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "WORKER")
    void getAllUsersFromWorker() {
        try {
            mockMvc.perform(get("ver1/manager")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    void updateUserFromAdmin() {
        try {
            mockMvc.perform(put("ver1/manager/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "MANAGER")
    void updateUserFromManager() {
        try {
            mockMvc.perform(put("ver1/manager/1")).andExpect(status().isOk());
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
