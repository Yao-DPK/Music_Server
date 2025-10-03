package com.music_server.database.config.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class PlaylistItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Long playlist_id;

    private Long song_id;

    private int position;
}
