package com.music_server.mvp.repositories;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.domain.User;
import com.music_server.mvp.repository.UserRepository;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository test_user;

    // CREATE + READ (Single)
    @Test
    public void TestCreateUserAndReadIt() {
        User user = TestDataUtil.createTestUser("Pyke", "Pyke");
        test_user.save(user);

        Optional<User> result = test_user.findById(user.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("Pyke");
        assertThat(result.get().getPassword()).isEqualTo("Pyke");
    }

    // CREATE + READ (Multiple)
    @Test
    public void TestMultipleUsersCreationAndRead() {
        User user1 = TestDataUtil.createTestUser("Kyde", "Kyde");
        User user2 = TestDataUtil.createTestUser("Pyke", "Pyke");

        test_user.save(user1);
        test_user.save(user2);

        List<User> results = test_user.findAll();

        assertThat(results).hasSize(2);
        assertThat(results).extracting(User::getUsername)
                           .containsExactlyInAnyOrder("Kyde", "Pyke");
    }

    // UPDATE
    @Test
    public void TestUpdateUser() {
        User user = TestDataUtil.createTestUser("OldName", "OldPass");
        test_user.save(user);

        // Simuler une mise à jour
        user.setUsername("NewName");
        user.setPassword("NewPass");
        test_user.save(user); // save() agit comme merge() si l'id existe déjà

        Optional<User> updated = test_user.findById(user.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getUsername()).isEqualTo("NewName");
        assertThat(updated.get().getPassword()).isEqualTo("NewPass");
    }

    // DELETE (Single)
    @Test
    public void TestDeleteUser() {
        User user = TestDataUtil.createTestUser("ToDelete", "Pass");
        test_user.save(user);

        test_user.delete(user);

        Optional<User> result = test_user.findById(user.getId());
        assertThat(result).isEmpty();
    }

    // DELETE (All)
    @Test
    public void TestDeleteAllUsers() {
        User user1 = TestDataUtil.createTestUser("Alpha", "A");
        User user2 = TestDataUtil.createTestUser("Beta", "B");

        test_user.save(user1);
        test_user.save(user2);

        test_user.deleteAll();

        List<User> results = test_user.findAll();
        assertThat(results).isEmpty();
    }
}
