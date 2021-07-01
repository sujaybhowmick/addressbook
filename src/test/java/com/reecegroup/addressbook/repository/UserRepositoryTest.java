package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.service.manager.AddressBookManagerHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserRepositoryTest extends BaseRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void insertUser() {
        userRepository.save(RepositoryTestHelper.createUser(USER_NAME));
        Optional<User> persistedUser = userRepository.findByUserName(USER_NAME);
        assertThat(persistedUser.isPresent()).isTrue();
        User user = persistedUser.orElseThrow();
        assertThat(user.getCreatedAt()).isNotNull();

    }

    @Test
    public void deleteUser() {
        userRepository.save(RepositoryTestHelper.createUser(USER_NAME));
        Optional<User> persistedUser = userRepository.findByUserName(USER_NAME);
        assertThat(persistedUser.isPresent()).isTrue();
        User user = persistedUser.orElseThrow();
        assertThat(user).isNotNull();
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            fail();
        }
        Optional<User> deletedUser = userRepository.findByUserName(user.getUserName());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    public void uniqueUserCreation() {
        userRepository.save(RepositoryTestHelper.createUser(USER_NAME));
        Optional<User> newUser = userRepository.findByUserName(USER_NAME);
        assertThat(newUser.isPresent()).isTrue();
        try {
            userRepository.save(RepositoryTestHelper.createUser(USER_NAME));
            fail();
        } catch (DataIntegrityViolationException e) {
            assertThat(true).isTrue();
        }
    }
}