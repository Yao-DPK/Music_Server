package com.music_server.mvp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;

public class PlaylistItemDto {
    private Long id;
    
    private Long playlistId;

    private SongDto song;
    
    private int position;

    

    public PlaylistItemDto(Long id, Long playlistId, SongDto song, int position) {
        this.id = id;
        this.playlistId = playlistId;
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

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public SongDto getSong() {
        return song;
    }

    public void setSong(SongDto song) {
        this.song = song;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    

    
}
