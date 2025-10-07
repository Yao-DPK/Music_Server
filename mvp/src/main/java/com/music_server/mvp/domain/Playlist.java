package com.music_server.mvp.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Plusieurs playlists peuvent appartenir à un même utilisateur
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")  // Clé étrangère vers User
    private User creator;

    // Une playlist contient plusieurs items
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PlaylistItem> items = new ArrayList<>();

    // ----- Constructors -----
    public Playlist() {}

    public Playlist(String title) {
        this.title = title;
    }

    public Playlist(String title, User creator) {
        this.title = title;
        this.creator = creator;

    }

    // ----- Getters & Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }

    public List<PlaylistItem> getItems() { return items; }
    public void setItems(List<PlaylistItem> items) { this.items = items; }

    // ----- Utility Methods -----
    public void addItem(PlaylistItem item) {
        items.add(item);
        item.setPlaylist(this);
    }

    public void removeItem(PlaylistItem item) {
        items.remove(item);
        item.setPlaylist(null);
    }

    public void addSong(Song song) {
        PlaylistItem item = new PlaylistItem();
        item.setSong(song);
        item.setPlaylist(this);
        item.setPosition(items.size() + 1);
        items.add(item);
    }

    public void removeSong(Song song) {
        items.removeIf(item -> {
            if (item.getSong().equals(song)) {
                item.setPlaylist(null);
                item.setSong(null);
                return true;
            }
            return false;
        });
    }

    // ----- toString -----
    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", items=" + items.size() +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;
        Playlist playlist = (Playlist) o;
        return id != null && id.equals(playlist.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
}
