package com.music_server.mvp.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Plusieurs chansons peuvent appartenir à un même utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // clé étrangère
    private User owner;

    // ----- Constructors -----
    public Song() {
        // Hibernate exige un constructeur vide
    }

    public Song(String title) {
        this.title = title;
    }

    public Song(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // ----- Getters & Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    // ----- Utility Methods -----
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + (owner != null ? owner.getUsername() : "none") +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song other = (Song) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        // Avant persistance : hash mémoire unique
        // Après persistance : hash stable basé sur l'id
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
}
