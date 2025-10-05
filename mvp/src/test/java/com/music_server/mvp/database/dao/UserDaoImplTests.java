package com.music_server.mvp.database.dao;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.impl.UserDaoImpl;
import com.music_server.database.config.domain.Song;
import com.music_server.database.config.domain.User;


@ExtendWith(MockitoExtension.class)
public class UserDaoImplTests {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl test_user;

    @Test
    public void testUserCreation(){
        User user = new User("Pyke", "Pyke");
        test_user.create(user);

        verify(jdbcTemplate).update(
            eq("INSERT INTO users (username, password) VALUES (?, ?)"),
            eq("Pyke"), eq("Pyke")
            );

    }
}

