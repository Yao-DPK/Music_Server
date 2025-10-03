package com.music_server.database.config.domain;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

public class Playlist {
    
    @Id
    private Long id;

    private String title;
    
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PlaylistItem> items = new ArrayList<>();
    

}