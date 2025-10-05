package com.music_server.database.config.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Entity
public class PlaylistItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Long playlist_id;

    private Long song_id;

    private int position;

    public PlaylistItem() {
    }

    public PlaylistItem(Long playlist_id, Long song_id, int position) {
        this.playlist_id = playlist_id;
        this.song_id = song_id;
        this.position = position;
    }

    public Long getPlaylist_id() {
        return playlist_id;
    }

    public Long getSong_id() {
        return song_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPlaylist_id(Long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

            

    
}
