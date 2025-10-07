package com.music_server.mvp.domain;

import jakarta.persistence.*;

@Entity
@Table(
    name = "playlist_items",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"playlist_id", "position"}) // empêche deux chansons à la même position
    }
)
public class PlaylistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plusieurs items appartiennent à une même playlist
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    // Chaque item référence une seule chanson, mais une chanson peut apparaître dans plusieurs playlists
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    // Position de la chanson dans la playlist
    @Column(nullable = false)
    private int position;

    // ----- Constructeurs -----
    public PlaylistItem() {}

    public PlaylistItem(Playlist playlist, Song song, int position) {
        this.playlist = playlist;
        this.song = song;
        this.position = position;
    }

    // ----- Getters / Setters -----
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Playlist getPlaylist() { return playlist; }

    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }

    public Song getSong() { return song; }

    public void setSong(Song song) { this.song = song; }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistItem)) return false;
        PlaylistItem other = (PlaylistItem) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }

    // ----- toString -----
    @Override
    public String toString() {
        return "PlaylistItem{" +
                "id=" + id +
                ", position=" + position +
                ", song=" + (song != null ? song.getTitle() : "null") +
                ", playlist=" + (playlist != null ? playlist.getId() : "null") +
                '}';
    }
}
