package com.music_server.mvp.services;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.UserEntity;

public interface PlaylistService extends GenericService<PlaylistEntity, Long> {
    
    PlaylistEntity partialUpdate(Long id, PlaylistEntity entity);

    
}
