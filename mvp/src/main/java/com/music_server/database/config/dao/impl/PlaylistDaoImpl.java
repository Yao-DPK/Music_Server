package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.PlaylistDao;

public class PlaylistDaoImpl implements PlaylistDao{

    private final JdbcTemplate jdbcTemplate;

    public PlaylistDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

}
