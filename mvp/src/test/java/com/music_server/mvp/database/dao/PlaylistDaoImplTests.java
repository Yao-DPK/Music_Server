package com.music_server.mvp.database.dao;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.database.config.dao.impl.PlaylistDaoImpl;
import com.music_server.database.config.domain.Playlist;
import com.music_server.database.config.domain.Song;


@ExtendWith(MockitoExtension.class)
public class PlaylistDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PlaylistDaoImpl test_playlist;

    @Test
    public void playlistCreationTest(){
        Playlist playlist = new Playlist("Sport");
        test_playlist.create(playlist);

        verify(jdbcTemplate).update(
            eq("INSERT INTO playlist (title) VALUES (?)"), 
            eq("Sport"));
    }

    @Test
    public void ReadOnePlaylistTest(){
        test_playlist.findOne("Sport");

        verify(jdbcTemplate).query(
            eq("SELECT id, title FROM playlist WHERE title = ? LIMIT 1"), 
            ArgumentMatchers.<PlaylistDaoImpl.PlaylistRowMapper>any(),
            eq("Sport")
            );
    }
}
