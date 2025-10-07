package com.music_server.mvp.dao.impl;

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

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class UserDaoImplIntegrationTests {
    
    private UserDaoImpl test_user;

    @Autowired
    public UserDaoImplIntegrationTests(UserDaoImpl test_user){
        this.test_user = test_user;
    }

    @Test
    public void TestCreateUserandReadIt(){
        User user = TestDataUtil.createTestUser("Pyke", "Pyke");
        
        test_user.create(user);
        Optional<User> result = test_user.findOne(user.getUsername());
        
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());
        
    }
    
    @Test
    public void TestMultipleUsersCreationandRead(){
        User user1 = TestDataUtil.createTestUser("Kyde", "Kyde");
        test_user.create(user1);
        User user2 = TestDataUtil.createTestUser("Pyke", "Pyke");
        test_user.create(user2);


        List<User> results = test_user.findAll();
        assertThat(results).extracting(User::getUsername).contains("Kyde", "Pyke");
        
    }
}
