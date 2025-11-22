package com.music_server.mvp.services;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;

public interface PlaylistService extends GenericService<PlaylistEntity, Long> {
    
    PlaylistEntity partialUpdate(Long id, PlaylistEntity entity);

    List<PlaylistEntity> findUsersPlaylistsByUsername(String creator);
    Optional<PlaylistEntity> findUserPlaylistByTitle(String creator, String title);
    PlaylistEntity addSong(Long id, Long song_id);
}
