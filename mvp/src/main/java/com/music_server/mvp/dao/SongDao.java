package com.music_server.mvp.dao;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.Song;

public interface SongDao {

    public void create(Song song);  

    Optional<Song> findOne(String title);

    List<Song> findAll();

}
