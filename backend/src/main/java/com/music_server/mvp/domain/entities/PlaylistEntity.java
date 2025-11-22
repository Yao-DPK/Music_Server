package com.music_server.mvp.domain.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "playlists")
public class PlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name= "creator")
    private String creator;

    // Une playlist contient plusieurs items
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    
    private List<PlaylistItemEntity> items = new ArrayList<>();


    // ----- Constructors -----
    public PlaylistEntity() {}

    public PlaylistEntity(String title) {
        this.title = title;
    }

    public PlaylistEntity(String title, String creator) {
        this.title = title;
        this.creator = creator;
    }

    // ----- Getters & Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public List<PlaylistItemEntity> getItems() { return items; }
    public void setItems(List<PlaylistItemEntity> items) { this.items = items; }

    // ----- Utility Methods -----
    public void addItem(PlaylistItemEntity item) {
        items.add(item);
        item.setPlaylist(this);
    }

    public void removeItem(PlaylistItemEntity item) {
        items.remove(item);
        item.setPlaylist(null);
    }

    public void addSong(SongEntity song) {
        PlaylistItemEntity item = new PlaylistItemEntity();
        item.setSong(song);
        item.setPlaylist(this);
        item.setPosition(items.size() + 1);
        items.add(item);
    }

    public void removeSong(SongEntity song) {
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
        return "PlaylistEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", items=" + items.size() +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistEntity)) return false;
        PlaylistEntity playlist = (PlaylistEntity) o;
        return id != null && id.equals(playlist.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
}
