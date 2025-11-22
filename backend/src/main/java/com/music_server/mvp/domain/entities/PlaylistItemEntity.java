package com.music_server.mvp.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(
    name = "playlist_items",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"playlist_id", "position"}) // empêche deux chansons à la même position
    }
)
public class PlaylistItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plusieurs items appartiennent à une même playlist
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlist_id", nullable = false)
    
    private PlaylistEntity playlist;

    // Chaque item référence une seule chanson, mais une chanson peut apparaître dans plusieurs playlists
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

    // Position de la chanson dans la playlist
    @Column(nullable = false)
    private int position;

    // ----- Constructeurs -----
    public PlaylistItemEntity() {}

    public PlaylistItemEntity(PlaylistEntity playlist, SongEntity song, int position) {
        this.playlist = playlist;
        this.song = song;
        this.position = position;
    }

    public PlaylistItemEntity(SongEntity song) {
        this.song = song;
    }

    // ----- Getters / Setters -----
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public PlaylistEntity getPlaylist() { return playlist; }

    public void setPlaylist(PlaylistEntity playlist) { this.playlist = playlist; }

    public SongEntity getSong() { return song; }

    public void setSong(SongEntity song) { this.song = song; }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistItemEntity)) return false;
        PlaylistItemEntity other = (PlaylistItemEntity) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }

    // ----- toString -----
    @Override
    public String toString() {
        return "PlaylistEntityItem{" +
                "id=" + id +
                ", position=" + position +
                ", song=" + (song != null ? song.getTitle() : "null") +
                ", playlist=" + (playlist != null ? playlist.getId() : "null") +
                '}';
    }
}
