package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.PlaylistItemDao;

public class PlaylistItemDaoImpl implements PlaylistItemDao{
    private final JdbcTemplate jdbcTemplate;

    public PlaylistItemDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

}
