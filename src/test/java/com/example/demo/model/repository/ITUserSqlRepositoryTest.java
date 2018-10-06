package com.example.demo.model.repository;

import com.example.demo.config.AdditionalConfig;
import com.example.demo.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
@ComponentScan("com.example.demo")
public class ITUserSqlRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SimpleJPAUserRepository userRepository;

    private User user1;

    @Before
    public void setup() {
        // given
        user1 = new User(0,"login","password1","name1", new ArrayList<>());
        entityManager.persistAndFlush(user1);
    }

    @Test
    public void whenFindByLogin_thenReturnUser() {

        // when
        Optional<User> userDB = userRepository.findByLogin(user1.getLogin());

        // then
        assertTrue(userDB.isPresent());
        assertThat(userDB.get().getId(), equalTo(user1.getId()));
        assertThat(userDB.get(), equalTo(user1));
    }

    @Test
    public void whenFindByName_thenReturnUser() {

        // when
        Optional<User> userDB = userRepository.findByName(user1.getName());

        // then
        assertTrue(userDB.isPresent());
        assertThat(userDB.get().getId(), equalTo(user1.getId()));
        assertThat(userDB.get(), equalTo(user1));
    }

    @Test
    public void whenExistsByLoginInvokedOnExistingUser() {
        assertTrue(userRepository.existsByLogin(user1.getLogin()));
    }
}