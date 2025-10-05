package com.music_server.database.config.dao;

import java.util.Optional;

import com.music_server.database.config.domain.Song;
import com.music_server.database.config.domain.User;

public interface UserDao {

    public void create(User user);

    Optional<User> findOne(String username);
}
