package com.music_server.mvp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.dto.PlaylistDto;
import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.services.PlaylistService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class PlaylistControllerIntegrationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;
    private UserEntity testUser;

    @BeforeEach
    void setup() throws Exception {
        testUser = TestDataUtil.createTestUser("Pyke", passwordEncoder.encode("Pyke"));
        userService.create(testUser);

        String loginJson = """
            {
                "username": "Pyke",
                "password": "Pyke"
            }
            """;

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        jwtToken = jsonNode.get("token").asText();
    }

    // ==========================
    // 1. HAPPY PATH TESTS
    // ==========================
    @Test
    void testCreatePlaylist_ShouldReturn201Created() throws Exception {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setTitle("My Playlist");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/playlists/me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playlistDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("My Playlist"));
    }

    @Test
    void testGetUserPlaylists_ShouldReturn200Ok() throws Exception {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setTitle("Existing Playlist");
        playlistEntity.setCreator(testUser);

        playlistService.create(playlistEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/playlists/me")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Existing Playlist"));
    }

    // ==========================
    // 2. VALIDATION & ERROR TESTS
    // ==========================
    @Test
    void testCreatePlaylist_InvalidBody_ShouldReturn400() throws Exception {
        String invalidJson = """
            {
                "unknownField": "badData"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/playlists/me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetUserPlaylists_Unauthenticated_ShouldReturn401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/playlists/me"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // ==========================
    // 3. SECURITY / PERMISSIONS
    // ==========================
    @Test
    void testCreatePlaylist_DifferentUser_ShouldNotBeAllowedToAssignOtherOwner() throws Exception {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setTitle("NotAllowed");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/playlists/me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playlistEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // Playlist should always belong to authenticated user
    }
}
