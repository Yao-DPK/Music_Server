package com.music_server.mvp.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.mvp.dao.impl.UserDaoImpl;
import com.music_server.mvp.dao.impl.UserDaoImpl.UserRowMapper;
import com.music_server.mvp.domain.Song;
import com.music_server.mvp.domain.User;


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

    @Test
    public void ReadOneUserTest(){
        test_user.findOne("Pyke");

        verify(jdbcTemplate).query(
            eq("SELECT id, username FROM users WHERE username = ? LIMIT 1"),
            ArgumentMatchers.<UserDaoImpl.UserRowMapper>any(),
            eq("Pyke")
        );

    }

    @Test
    public void ReadManyUsersTest(){
        test_user.findAll();

        verify(jdbcTemplate).query(eq("SELECT id, username FROM users"), ArgumentMatchers.<UserRowMapper>any());
    }
}

