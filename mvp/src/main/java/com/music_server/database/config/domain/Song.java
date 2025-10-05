package com.music_server.database.config.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

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

}   