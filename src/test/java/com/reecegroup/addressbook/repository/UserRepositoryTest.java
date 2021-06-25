package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testInsertUser() {
        userRepository.save(createUser());
        User newUser = userRepository.findByUserName("sujaybhowmick");
        assertNotNull(newUser);
    }

    @Test
    public void testUniqueUserCreation() {
        userRepository.save(createUser());
        User newUser = userRepository.findByUserName("sujaybhowmick");
        assertNotNull(newUser);
        try {
            userRepository.save(createUser());
            fail("Test for unique user creation failed");
        }catch (DataIntegrityViolationException e) {
            assertTrue(true);
        }
    }

    private User createUser() {
        final User.Builder builder = new User.Builder("sujaybhowmick");
        return builder
                .firstName("Sujay")
                .lastName("Bhowmick")
                .build();
    }

}