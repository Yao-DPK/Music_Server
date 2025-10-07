package com.music_server.mvp.dao;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.Song;
import com.music_server.mvp.domain.User;

public interface UserDao {

    public void create(User user);

    Optional<User> findOne(String username);

    List<User> findAll();
}
