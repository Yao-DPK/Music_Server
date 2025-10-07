package com.music_server.mvp.domain;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;


    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    // ----- Getters and Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }

    // ----- Utility Methods -----
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
        playlist.setCreator(this);  // lie la playlist à cet utilisateur
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
        playlist.setCreator(null);
    }

    public void addSong(Song song) {
        songs.add(song);
        song.setOwner(this);  // lie le song à cet utilisateur
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.setOwner(null);
    }

    // ----- toString (utile pour les logs) -----
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", playlists=" + playlists.size() +
                ", songs=" + songs.size() +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
    
    
}