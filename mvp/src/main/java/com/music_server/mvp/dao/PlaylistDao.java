package com.music_server.mvp.dao;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.Song;

public interface PlaylistDao {

    public void create(Playlist playlist);
    
    public int count_songs(Playlist playlist);

    public void add_song(Playlist playlist, Song song);

    Optional<Playlist> findOne(String title);
    
    List<Playlist> findAll();
    
}   
