package com.music_server.mvp.domain;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;


public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    

    public Playlist(Long id, String title) {
        this.id = id;
        this.title = title;
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

    public Long getId() {
        return id;
    }
    
    
    
}