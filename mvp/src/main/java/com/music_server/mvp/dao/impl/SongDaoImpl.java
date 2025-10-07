package com.music_server.mvp.dao.impl;

import static org.mockito.ArgumentMatchers.eq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.music_server.mvp.dao.SongDao;
import com.music_server.mvp.domain.Song;

import java.util.List;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;


@Component
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
    
    @Override
    public Optional<Song> findOne(String title) {
        List<Song> results = jdbcTemplate.query("SELECT id, title FROM song WHERE title = ? LIMIT 1", 
        new SongRowMapper(), title);

        return results.stream().findFirst();
    }

    @Override
    public List<Song> findAll(){
        return jdbcTemplate.query("SELECT id, title FROM song", new SongRowMapper());
    }

    public static class SongRowMapper implements RowMapper<Song>{

        @Override
        public Song mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return new Song(rs.getLong("id"), rs.getString("title"));
        }
    }
}
