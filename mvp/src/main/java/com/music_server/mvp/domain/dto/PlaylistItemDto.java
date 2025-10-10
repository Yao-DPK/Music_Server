package com.music_server.mvp.domain.dto;

public class PlaylistItemDto {
    private Long id;

    private String title;

    private Long playlist_id;

    private Long song_id;

    

    public PlaylistItemDto(Long id, String title, Long playlist_id, Long song_id) {
        this.id = id;
        this.title = title;
        this.playlist_id = playlist_id;
        this.song_id = song_id;
    }

    public PlaylistItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(Long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public Long getSong_id() {
        return song_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    
}
