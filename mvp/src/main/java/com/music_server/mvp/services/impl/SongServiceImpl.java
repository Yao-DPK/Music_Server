package com.music_server.mvp.services.impl;

import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.services.SongService;

public class SongServiceImpl extends GenericServiceImpl<SongEntity, Long, SongRepository> implements SongService{
    
    public SongServiceImpl(SongRepository repository){
        super(repository);
    } 

}
