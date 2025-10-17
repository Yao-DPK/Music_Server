package com.music_server.mvp.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class PlaylistItemDto {
    private Long id;

    private PlaylistDto playlist;

    private SongDto song;
    
    private int position;

    

    public PlaylistItemDto(Long id, PlaylistDto playlist, SongDto song, int position) {
        this.id = id;
        this.playlist = playlist;
        this.song = song;
        this.position = position;
    }

    public PlaylistItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaylistDto getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistDto playlist) {
        this.playlist = playlist;
    }

    public SongDto getSong() {
        return song;
    }

    public void setSong(SongDto song) {
        this.song = song;
    }

    
}
