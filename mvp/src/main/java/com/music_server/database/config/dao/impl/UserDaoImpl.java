package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.UserDao;

public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

}
