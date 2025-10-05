package com.music_server.database.config.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.PlaylistDao;
import com.music_server.database.config.domain.Playlist;
import com.music_server.database.config.domain.Song;

public class PlaylistDaoImpl implements PlaylistDao{

    private final JdbcTemplate jdbcTemplate;

    public PlaylistDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Playlist playlist) {
        jdbcTemplate.update(
            "INSERT INTO playlist (title) VALUES (?)",
            playlist.getTitle()
        );
    }
}
