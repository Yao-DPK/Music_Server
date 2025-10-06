package com.music_server.mvp.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.music_server.mvp.dao.PlaylistItemDao;
import com.music_server.mvp.domain.PlaylistItem;
import com.music_server.mvp.domain.Song;

@Component
public class PlaylistItemDaoImpl implements PlaylistItemDao{
    private final JdbcTemplate jdbcTemplate;

    public PlaylistItemDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(PlaylistItem playlist_item) {
        jdbcTemplate.update(
            "INSERT INTO playlist_item (playlist_id, position, song_id) VALUES (?, ?, ?)",
            playlist_item.getPlaylist_id(), playlist_item.getPosition(), playlist_item.getSong_id()
        );
    }

    
}
