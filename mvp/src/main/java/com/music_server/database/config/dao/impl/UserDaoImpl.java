package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.UserDao;
import com.music_server.database.config.domain.Song;
import com.music_server.database.config.domain.User;

public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update(
            "INSERT INTO users (username, password) VALUES (?, ?)",
            user.getUsername(), user.getPassword()
        );
    }

}
