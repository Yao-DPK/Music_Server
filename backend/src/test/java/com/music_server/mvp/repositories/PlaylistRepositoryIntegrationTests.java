/* package com.music_server.mvp.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.PlaylistRepository;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class PlaylistRepositoryIntegrationTests {

    @Autowired
    private PlaylistRepository test_playlist;

    @Autowired
    private UserRepository test_user;

    @Autowired
    private SongRepository test_song;

    // CREATE + READ (single)
    @Test
    public void TestPlaylistCreationAndReadIt() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        PlaylistEntity playlist = TestDataUtil.createTestPlaylist("Chill", user);
        playlist.setCreator(user);

        test_playlist.save(playlist);
        Optional<PlaylistEntity> result = test_playlist.findById(playlist.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Chill");
        assertThat(result.get().getCreator().getUsername()).isEqualTo("Pyke");
    }

    // CREATE + READ (multiple)
    @Test
    public void MultiplePlaylistsCreationAndReadTest() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));

        PlaylistEntity playlist1 = TestDataUtil.createTestPlaylist("Silent Walks", user);
        PlaylistEntity playlist2 = TestDataUtil.createTestPlaylist("Sport", user);
        PlaylistEntity playlist3 = TestDataUtil.createTestPlaylist("Chill", user);

        test_playlist.save(playlist1);
        test_playlist.save(playlist2);
        test_playlist.save(playlist3);

        List<PlaylistEntity> results = test_playlist.findAll();

        assertThat(results).hasSize(3);
        assertThat(results)
            .extracting(PlaylistEntity::getTitle)
            .containsExactlyInAnyOrder("Silent Walks", "Sport", "Chill");
    }

    // UPDATE
    @Test
    public void TestPlaylistUpdate() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        PlaylistEntity playlist = test_playlist.save(TestDataUtil.createTestPlaylist("OldTitle", user));

        playlist.setTitle("NewTitle");
        test_playlist.save(playlist);

        Optional<PlaylistEntity> updated = test_playlist.findById(playlist.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("NewTitle");
    }

    // DELETE (single)
    @Test
    public void TestPlaylistDelete() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        PlaylistEntity playlist = test_playlist.save(TestDataUtil.createTestPlaylist("ToDelete", user));

        test_playlist.delete(playlist);

        Optional<PlaylistEntity> result = test_playlist.findById(playlist.getId());
        assertThat(result).isEmpty();
    }

    // DELETE (all)
    @Test
    public void TestDeleteAllPlaylists() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));

        PlaylistEntity playlist1 = TestDataUtil.createTestPlaylist("Playlist1", user);
        PlaylistEntity playlist2 = TestDataUtil.createTestPlaylist("Playlist2", user);

        test_playlist.save(playlist1);
        test_playlist.save(playlist2);

        test_playlist.deleteAll();

        List<PlaylistEntity> results = test_playlist.findAll();
        assertThat(results).isEmpty();
    }

    // ADD SONG TO PLAYLIST
    @Test
    public void TestAddSongToPlaylist() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        PlaylistEntity playlist = test_playlist.save(TestDataUtil.createTestPlaylist("Sport", user));

        SongEntity song = test_song.save(TestDataUtil.createTestSong("Sonic.mp3", user));
        playlist.addSong(song);

        test_playlist.save(playlist);

        Optional<PlaylistEntity> result = test_playlist.findById(playlist.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getItems()).hasSize(1);
        assertThat(result.get().getItems().get(0).getSong().getTitle()).isEqualTo("Sonic.mp3");
    }

    // REMOVE SONG FROM PLAYLIST
    @Test
    public void TestRemoveSongFromPlaylist() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        PlaylistEntity playlist = test_playlist.save(TestDataUtil.createTestPlaylist("Chill", user));

        SongEntity song1 = test_song.save(TestDataUtil.createTestSong("Sonic.mp3", user));
        SongEntity song2 = test_song.save(TestDataUtil.createTestSong("Mario.mp3", user));

        playlist.addSong(song1);
        playlist.addSong(song2);
        test_playlist.save(playlist);

        // supprimer une chanson
        playlist.removeSong(song1);
        test_playlist.save(playlist);

        Optional<PlaylistEntity> result = test_playlist.findById(playlist.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getItems()).hasSize(1);
        assertThat(result.get().getItems().get(0).getSong().getTitle()).isEqualTo("Mario.mp3");
    }
}
 */