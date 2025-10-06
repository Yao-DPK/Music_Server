package com.music_server.mvp.domain;
import jakarta.persistence.*;


public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Song(){
        this.title = "";
    }

    public Song(String title){
        this.title = title;
    } 

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Song(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    

    
}   