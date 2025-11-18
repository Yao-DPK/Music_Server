package com.music_server.mvp.services;

import java.util.List;

import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;

public interface SongService extends GenericService<SongEntity, Long> {

    List<SongEntity> findUsersSongsByUsername(String owner);

}
