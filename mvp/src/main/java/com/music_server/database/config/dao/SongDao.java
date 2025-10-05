package com.music_server.database.config.dao;

import java.util.Optional;

import com.music_server.database.config.domain.Song;

public interface SongDao {

    public void create(Song song);  

    Optional<Song> findOne(String title);

}
