package com.music_server.database.config.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

    @Override
    public Optional<Playlist> findOne(String title) {
        List<Playlist> results = jdbcTemplate.query("SELECT id, title FROM playlist WHERE title = ? LIMIT 1", 
        new PlaylistRowMapper(), title);

        return results.stream().findFirst();
    }

    public static class PlaylistRowMapper implements RowMapper<Playlist>{

        @Override
        public Playlist mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return new Playlist(rs.getLong("id"), rs.getString("title"));
        }
    }

}
