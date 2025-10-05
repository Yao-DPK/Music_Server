package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.SongDao;
import com.music_server.database.config.domain.Song;

public class SongDaoImpl implements SongDao{

    private final JdbcTemplate jdbcTemplate;

    public SongDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Song song) {
        jdbcTemplate.update(
            "INSERT INTO song (title) VALUES (?)",
            song.getTitle()
        );
    }

}
