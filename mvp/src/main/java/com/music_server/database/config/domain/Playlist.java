package com.music_server.database.config.domain;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;


@Entity
public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PlaylistItem> items = new ArrayList<PlaylistItem>();

    public Playlist(String title, List<PlaylistItem> items) {
        this.title = title;
        this.items = items;
    }
    
    

    public Playlist(String title) {
        this.title = title;
    }


    public Playlist() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PlaylistItem> getItems() {
        return items;
    }

    public void setItems(List<PlaylistItem> items) {
        this.items = items;
    }
    
    
}