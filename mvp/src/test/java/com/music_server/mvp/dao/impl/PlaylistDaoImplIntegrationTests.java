package com.music_server.mvp.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.dao.SongDao;
import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.Song;

import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class PlaylistDaoImplIntegrationTests {
    
    @Autowired
    private PlaylistDaoImpl test_playlist;
    
    @Autowired
    private SongDao test_song;

    @Test
    public void testPlaylistCreationandReadIt(){
        Playlist playlist = TestDataUtil.createTestPlaylist("Chill");
        test_playlist.create(playlist);
        Optional<Playlist> result = test_playlist.findOne(playlist.getTitle());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(playlist.getTitle());
    }


    @Test
    public void CountSongsTest(){
        Playlist playlist = TestDataUtil.createTestPlaylist("Sport");
        test_playlist.create(playlist);

        Optional<Playlist> get_playlist = test_playlist.findOne("Sport");

        int count = test_playlist.count_songs(get_playlist.get());
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void AddSongTest(){
        Playlist playlist = TestDataUtil.createTestPlaylist("Sport");
        test_playlist.create(playlist);

        Song song = TestDataUtil.createTestSong("Sonic.mp3");
        test_song.create(song);

        Optional<Playlist> result_playlist = test_playlist.findOne("Sport");
        Optional<Song> result_song = test_song.findOne("Sonic.mp3");

        int count = test_playlist.count_songs(result_playlist.get());
        assertThat(count).isEqualTo(0);

        test_playlist.add_song(result_playlist.get(), result_song.get());
        
        count = test_playlist.count_songs(result_playlist.get());
        assertThat(count).isEqualTo(1);
    }

    @Test 
    public void MultiplePlaylistsCreationandReadTest(){
        Playlist playlist1 = TestDataUtil.createTestPlaylist("Silent Walks");
        test_playlist.create(playlist1);

        Playlist playlist2 = TestDataUtil.createTestPlaylist("Sport");
        test_playlist.create(playlist2);

        Playlist playlist3 = TestDataUtil.createTestPlaylist("Chill");
        test_playlist.create(playlist3);

        List<Playlist> results = test_playlist.findAll();


        assertThat(results).hasSize(3);
        assertThat(results).extracting(Playlist::getTitle).containsExactly("Silent Walks", "Sport", "Chill");

        
    }

}
