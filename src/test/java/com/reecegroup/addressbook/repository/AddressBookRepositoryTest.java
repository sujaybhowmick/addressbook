package com.reecegroup.addressbook.repository;

import static org.assertj.core.api.Assertions.*;
import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AddressBookRepositoryTest extends BaseRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void saveAddressBookForUser() {
        final User user = RepositoryTestHelper.createUser("sujaybhowmick1");
        AddressBook addressBook = RepositoryTestHelper.createAddressBook("Personal1");
        userRepository.save(user);
        addressBook.setOwner(user);
        user.getAddressBooks().add(addressBook);
        userRepository.save(user);
        Optional<User> persistedUser = userRepository.findById(user.getId());
        assertThat(persistedUser.isPresent()).isTrue();
        List<AddressBook> addressBooks = persistedUser.orElseThrow().getAddressBooks();
        assertThat(addressBooks.size()).isEqualTo(1);
        assertThat(addressBooks.get(0).getOwner()).isNotNull();

    }

    @Test
    public void deleteAddressBookForUser() {
        final User user = RepositoryTestHelper.createUser(USER_NAME);
        AddressBook addressBook = RepositoryTestHelper.createAddressBook(ADDRESS_BOOK_NAME);
        userRepository.save(user);
        addressBook.setOwner(user);
        user.getAddressBooks().add(addressBook);
        userRepository.save(user);

        addressBookRepository.delete(addressBook);
        final Optional<AddressBook> deleteAddressBook = addressBookRepository.findByAddressBookName(USER_NAME);
        assertThat(deleteAddressBook.isEmpty()).isTrue();
        // Check is user still exists
        final Optional<User> persistedUser = userRepository.findByUserName(USER_NAME);
        assertThat(persistedUser.isPresent()).isTrue();
    }


}