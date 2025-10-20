package com.music_server.mvp.services.impl;

import com.music_server.mvp.repositories.PlaylistItemRepository;
import com.music_server.mvp.repositories.PlaylistRepository;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.PlaylistService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.PlaylistItemEntity;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;

@Service
public class PlaylistServiceImpl extends GenericServiceImpl<PlaylistEntity, Long,  PlaylistRepository> implements PlaylistService{
    
    private final UserRepository userRepository;

    public PlaylistServiceImpl(PlaylistRepository repository, UserRepository userRepository){
        super(repository);
        this.userRepository = userRepository;
    }

    @Autowired
    private PlaylistItemRepository playlistItemRepository;

    @Autowired
    private SongRepository songRepository;

    
    @Override
    public PlaylistEntity create(PlaylistEntity playlist) {
        // Récupérer l'utilisateur attaché à la session
        UserEntity creator = userRepository.findById(playlist.getCreator().getId())
                                         .orElseThrow(() -> new IllegalArgumentException("User not found"));
        playlist.setCreator(creator);

        return repository.save(playlist);
    }


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

    @Override
    public List<PlaylistEntity> findUsersPlaylistsById(Long id){
        return repository.findAllByCreator_Id(id);
    }

    @Override
    public List<PlaylistEntity> findUsersPlaylistsByUsername(String username){
        return repository.findAllByCreator_Username(username);
    }

}
