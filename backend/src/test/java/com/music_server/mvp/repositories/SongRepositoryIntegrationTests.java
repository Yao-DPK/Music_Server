package com.music_server.mvp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.SongRepository;
import com.music_server.mvp.repositories.UserRepository;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class SongRepositoryIntegrationTests {

    @Autowired
    private SongRepository test_song;

    @Autowired
    private UserRepository test_user;

    // CREATE + READ (single)
    @Test
    public void TestThatSongCanBeCreatedAndRecalled() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));

        SongEntity song = TestDataUtil.createTestSong("Sonic.mp3", user);
        song.setOwner(user);
        test_song.save(song);

        Optional<SongEntity> result = test_song.findById(song.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Sonic.mp3");
        assertThat(result.get().getOwner().getUsername()).isEqualTo("Pyke");
    }

    // CREATE + READ (multiple)
    @Test
    public void MultipleSongCreationAndReadTest() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));

        SongEntity song1 = TestDataUtil.createTestSong("Sonic.mp3", user);
        SongEntity song2 = TestDataUtil.createTestSong("Mario.mp3", user);
        SongEntity song3 = TestDataUtil.createTestSong("Marios.mp3", user);

        test_song.save(song1);
        test_song.save(song2);
        test_song.save(song3);

        List<SongEntity> results = test_song.findAll();

        assertThat(results).hasSize(3);
        assertThat(results)
            .extracting(SongEntity::getTitle)
            .containsExactlyInAnyOrder("Sonic.mp3", "Mario.mp3", "Marios.mp3");
    }

    // UPDATE
    @Test
    public void TestThatSongCanBeUpdated() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        SongEntity song = test_song.save(TestDataUtil.createTestSong("OldTitle.mp3", user));

        song.setTitle("NewTitle.mp3");
        test_song.save(song); // Hibernate = merge()

        Optional<SongEntity> result = test_song.findById(song.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("NewTitle.mp3");
    }

    // DELETE single
    @Test
    public void TestThatSongCanBeDeleted() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));
        SongEntity song = test_song.save(TestDataUtil.createTestSong("DeleteMe.mp3", user));

        test_song.delete(song);

        Optional<SongEntity> result = test_song.findById(song.getId());
        assertThat(result).isEmpty();
    }

    // DELETE all
    @Test
    public void TestThatAllSongsCanBeDeleted() {
        UserEntity user = test_user.save(TestDataUtil.createTestUser("Pyke", "Pyke"));

        SongEntity song1 = TestDataUtil.createTestSong("Track1.mp3", user);
        SongEntity song2 = TestDataUtil.createTestSong("Track2.mp3", user);

        test_song.save(song1);
        test_song.save(song2);

        test_song.deleteAll();

        List<SongEntity> results = test_song.findAll();
        assertThat(results).isEmpty();
    }
}
