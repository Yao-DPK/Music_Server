package com.music_server.mvp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.music_server.mvp.dao.UserDao;
import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.Song;
import com.music_server.mvp.domain.User;


@Component
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

    @Override
    public Optional<User> findOne(String username) {
        List<User> results = jdbcTemplate.query("SELECT id, username FROM users WHERE username = ? LIMIT 1", 
        new UserRowMapper(), username);

        return results.stream().findFirst();
    }

    public static class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("username"));
        }
    }


}
