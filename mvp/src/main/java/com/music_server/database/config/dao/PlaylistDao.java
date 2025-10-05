package com.music_server.database.config.dao;

import java.util.Optional;

import com.music_server.database.config.domain.Playlist;

public interface PlaylistDao {

    public void create(Playlist playlist);

    Optional<Playlist> findOne(String title);
}
