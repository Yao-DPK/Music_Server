package com.music_server.mvp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.SongService;


@Service
public class SongServiceImpl extends GenericServiceImpl<SongEntity, Long, SongRepository> implements SongService{
    
    private final UserRepository userRepository;

    public SongServiceImpl(SongRepository repository,  UserRepository userRepository){
        super(repository);
        this.userRepository = userRepository;
    } 

    @Override
    public SongEntity create(SongEntity song) {
        // Récupérer l'utilisateur attaché à la session
        UserEntity owner = userRepository.findById(song.getOwner().getId())
                                         .orElseThrow(() -> new IllegalArgumentException("User not found"));
        song.setOwner(owner);

        return repository.save(song);
    }

    @Override
    public List<SongEntity> findUsersSongsById(Long id){
        return repository.findAllByOwner_Id(id);
    }

    @Override
    public List<SongEntity> findUsersSongsByUsername(String username){
        return repository.findAllByOwner_Username(username);
    }


}
