/* package com.music_server.mvp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private String jwtToken;

    @Autowired
    public UserControllerIntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserService userService,
            PasswordEncoder passwordEncoder) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() throws Exception {
        // 1️⃣ Create test user
        UserEntity user = TestDataUtil.createTestUser("Pyke", passwordEncoder.encode("Pyke"));
        userService.create(user);

        // 2️⃣ Authenticate to get JWT
        String loginJson = """
        {
            "username": "Pyke",
            "password": "Pyke"
        }
        """;

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 3️⃣ Extract token
        JsonNode jsonNode = objectMapper.readTree(response);
        jwtToken = jsonNode.get("token").asText();
    }

    // ==========================
    // 1. CRUD TESTS
    // ==========================

    @Test
    void testUserCreation_ShouldReturn201RegisterUser() throws Exception {
        String userJson = """
            {
                "username":"Kyde",
                "password":"Kyde"
            }
        
        """;

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testReadAll_ShouldReturn200Ok() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/users")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadOne_ShouldReturn200Ok() throws Exception {
        UserEntity user = TestDataUtil.createTestUser("Kyde", passwordEncoder.encode("Kyde"));
        UserEntity savedUser = userService.create(user);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/users/{id}", savedUser.getId())
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Kyde"));
    }

    @Test
    void testFullUpdate_ShouldReturn200Ok() throws Exception {
        UserEntity userToUpdate = TestDataUtil.createTestUser("Aqua", "Lad");
        UserEntity savedUserToUpdate = userService.create(userToUpdate);

        UserEntity userThatUpdates = TestDataUtil.createTestUser("SuperBoy", "Prime");
        String userJson = objectMapper.writeValueAsString(userThatUpdates);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/users/{id}", savedUserToUpdate.getId())
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedUserToUpdate.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("SuperBoy"));
    }

    @Test
    void testPartialUpdate_ShouldReturn200Ok() throws Exception {
        UserEntity userToUpdate = TestDataUtil.createTestUser("Aqua", "Lad");
        UserEntity savedUserToUpdate = userService.create(userToUpdate);

        UserEntity partialUpdate = new UserEntity();
        partialUpdate.setUsername("SuperBoy");
        String userJson = objectMapper.writeValueAsString(partialUpdate);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/users/{id}", savedUserToUpdate.getId())
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedUserToUpdate.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("SuperBoy"));
    }

    @Test
    void testDelete_ShouldReturn204NoContent() throws Exception {
        UserEntity userToDelete = TestDataUtil.createTestUser("Aqua", "Lad");
        UserEntity savedUserToDelete = userService.create(userToDelete);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/users/{id}", savedUserToDelete.getId())
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // ==========================
    // 2. ERROR HANDLING TESTS
    // ==========================

    @Test
    void testGetNonExistentUser_ShouldReturn404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/users/{id}", 9999L)
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testInvalidUserCreation_ShouldReturn400() throws Exception {
        String invalidJson = """
        {
            "username": "",
            "password": ""
        }
        """;

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
 */