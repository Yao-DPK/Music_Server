package com.music_server.mvp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.music_server.mvp.dao.PlaylistDao;
import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.Song;


@Component
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
    public int count_songs(Playlist playlist) {

        int count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM playlist_item WHERE playlist_id = ?",
            Integer.class,
            playlist.getId()
        );

        return count;
    }

    @Override

    public void add_song(Playlist playlist, Song song) {

        int count = count_songs(playlist);

        jdbcTemplate.update(
            "INSERT INTO playlist_item  (playlist_id, song_id, position) VALUES (?, ?, ?)",
            playlist.getId(), song.getId(), count + 1
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
