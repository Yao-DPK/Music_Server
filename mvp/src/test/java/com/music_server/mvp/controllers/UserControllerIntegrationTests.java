package com.music_server.mvp.controllers;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.services.UserService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testUserCreation_ShoudReturn201CreatedAndSavedUser() throws Exception{
        UserEntity user = TestDataUtil.createTestUser("Pyke", "Pyke");
        String userJson = objectMapper.writeValueAsString(user);


        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()

        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value("Pyke")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value("Pyke")
        );
    }

    @Test
    public void testReadAll_ShouldReturn200Ok() throws Exception{
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testReadOne_ShouldReturn200Ok() throws Exception{
        UserEntity user = TestDataUtil.createTestUser("Pyke", "Pyke");
       userService.create(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/1")
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFullUpdate_ShouldReturn200Ok() throws Exception{
        UserEntity userToUpdate = TestDataUtil.createTestUser("Aqua", "Lad");
        userService.create(userToUpdate);

        UserEntity userThatUpdates = TestDataUtil.createTestUser("SuperBoy", "Prime");

        String userJson = objectMapper.writeValueAsString(userThatUpdates);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value(userThatUpdates.getUsername())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value(userThatUpdates.getPassword())
        );

    }

    @Test
    public void testPartialUpdate_ShouldReturn200Ok() throws Exception{
        UserEntity userToUpdate = TestDataUtil.createTestUser("Aqua", "Lad");
        userService.create(userToUpdate);

        UserEntity userThatUpdates = TestDataUtil.createTestUserDto("SuperBoy", "");
        userThatUpdates.setUsername(null);
        String userJson = objectMapper.writeValueAsString(userThatUpdates);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value(userThatUpdates.getUsername())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value(userThatUpdates.getPassword())
        );
    }

    @Test
    public void testDelete_ShouldReturn204NoContent() throws Exception{
        UserEntity userToUpdate = TestDataUtil.createTestUser("Aqua", "Lad");
        userService.create(userToUpdate);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/users/1")
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }

}
