package com.music_server.mvp.domain.entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username can't be blank")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @JsonIgnore
    @NotBlank(message = "Password can\'t be blank")
    @Column(nullable = false)
    private String password;

    // ----- Constructors -----
    public UserEntity() {}

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ----- Getters and Setters -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ----- toString (utile pour les logs) -----
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    // ----- equals & hashCode -----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity user = (UserEntity) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? System.identityHashCode(this) : id.hashCode();
    }
    
    
}