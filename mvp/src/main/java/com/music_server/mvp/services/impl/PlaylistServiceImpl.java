package com.music_server.mvp.services.impl;

import com.music_server.mvp.repositories.PlaylistItemRepository;
import com.music_server.mvp.repositories.PlaylistRepository;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.services.PlaylistService;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.PlaylistItemEntity;
import com.music_server.mvp.domain.entities.SongEntity;

public class PlaylistServiceImpl extends GenericServiceImpl<PlaylistEntity, Long,  PlaylistRepository> implements PlaylistService{
    
    public PlaylistServiceImpl(PlaylistRepository repository){
        super(repository);
    }

    @Autowired
    private PlaylistItemRepository playlistItemRepository;

    @Autowired
    private SongRepository songRepository;

    @Transactional
    public PlaylistEntity partialUpdate(Long id, PlaylistEntity entity){
        return repository.findById(id).map(foundPlaylist -> {
            Optional.ofNullable(entity.getTitle()).ifPresent(foundPlaylist::setTitle);
            return repository.save(entity);
        }).orElseThrow(()-> new RuntimeException("Playlist Not Found"));
    }

    @Transactional
    public PlaylistEntity addSong(Long id, Long song_id){
        PlaylistEntity playlist = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Playlist not found"));;
        SongEntity song = songRepository.findById(song_id)
        .orElseThrow(() -> new RuntimeException("Song not found"));
        playlist.addSong(song);

        return repository.save(playlist);
    }

}
