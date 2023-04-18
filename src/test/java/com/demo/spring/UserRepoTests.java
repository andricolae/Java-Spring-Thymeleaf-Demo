package com.demo.spring;

import com.demo.spring.user.User;
import com.demo.spring.user.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepoTests {
    @Autowired private UserRepo repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("test@gmail.ro");
        user.setPassword("parola123");
        user.setFirstName("Test");
        user.setLastName("Steven");

        User saved = repo.save(user);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users)
            System.out.println(user);
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optUser = repo.findById(userId);

        User user = optUser.get();
        user.setPassword("hello2023");
        repo.save(user);

        User updated = repo.findById(userId).get();
        Assertions.assertThat(updated.getPassword()).isEqualTo("hello2023");
    }

    @Test
    public void testGet() {
        Integer userId = 2;
        Optional<User> optUser = repo.findById(userId);
        Assertions.assertThat(optUser).isPresent();
        System.out.println(optUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);
        Optional<User> optUser = repo.findById(userId);
        Assertions.assertThat(optUser).isNotPresent();
    }
}