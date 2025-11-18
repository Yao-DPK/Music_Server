package com.music_server.mvp.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Plusieurs chansons peuvent appartenir à un même utilisateur
    /* @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") // clé étrangère
    private UserEntity owner; */

    @Column(name= "owner")
    private String owner;

    // ----- Constructors -----
    public SongEntity() {
        // Hibernate exige un constructeur vide
    }

    public SongEntity(String title) {
        this.title = title;
    }


    public SongEntity(String title, String owner) {
        this.title = title;
        this.owner = owner;
    }

    public SongEntity(Long id, String title, String owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
    }

    // ----- Getters & Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOwner() { return owner; }
    public void setOwner(String ownerId) { this.owner = ownerId; }

    // ----- Utility Methods -----
    @Override
    public String toString() {
        return "SongEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner_id=" + (owner != null ? owner : "none") +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongEntity)) return false;
        SongEntity other = (SongEntity) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        // Avant persistance : hash mémoire unique
        // Après persistance : hash stable basé sur l'id
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
}
