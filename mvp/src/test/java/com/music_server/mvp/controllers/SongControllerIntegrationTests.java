package com.music_server.mvp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.services.SongService;
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
public class SongControllerIntegrationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;
    private UserEntity testUser;

    // --- Setup user + token ---
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
    void testUploadSong_ShouldReturn201Created() throws Exception {
        String songJson = """
            {
                "title": "TestSong.mp3"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/songs/me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestSong.mp3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner.id").value(testUser.getId()));
    }

    @Test
    void testGetUserSongs_ShouldReturn200Ok() throws Exception {
        SongEntity song = new SongEntity();
        
        song.setTitle("Sonic.mp3");
        song.setOwner(testUser);
        songService.create(song);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/songs/me")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Sonic.mp3"));
    }

    // ==========================
    // 2. VALIDATION & ERROR TESTS
    // ==========================
    @Test
    void testUploadSong_InvalidBody_ShouldReturn400() throws Exception {
        String invalidJson = """
            {
                "unknownField": "badData"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/songs/me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetUserSongs_Unauthenticated_ShouldReturn401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/songs/me"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // ==========================
    // 3. SECURITY / PERMISSIONS
    // ==========================
    @Test
    void testUploadSong_DifferentUser_ShouldNotBeAllowed() throws Exception {
        // Create another user
        UserEntity otherUser = TestDataUtil.createTestUser("Hacker", passwordEncoder.encode("Hacker"));
        userService.create(otherUser);

        String songJson = """
            {
                "title": "NotAllowed.mp3"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/songs/me")
                        .header("Authorization", "Bearer " + jwtToken) // token of testUser
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner.id").value(testUser.getId())); 
        // The song should always belong to authenticated user, not the payload
    }
}
