package com.reecegroup.addressbook.repository;

import static org.assertj.core.api.Assertions.*;
import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ContactRepositoryTest extends BaseRepositoryTest {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void save() {
        final AddressBook addressBook = RepositoryTestHelper.createAddressBook(ADDRESS_BOOK_NAME);
        final User user = RepositoryTestHelper.createUser(USER_NAME);
        userRepository.save(user);
        addressBook.setOwner(user);
        user.getAddressBooks().add(addressBook);
        userRepository.save(user);
        List<AddressBook> addressBooks = user.getAddressBooks();

        final Contact contact = RepositoryTestHelper.createContact(FIRST_NAME,
                LAST_NAME, MIDDLE_NAME,PHONE_NUMBER);
        addressBooks.get(0).getContacts().add(contact);
        contact.setAddressBook(addressBooks.get(0));
        addressBookRepository.save(addressBooks.get(0));

        // Retrieve all contacts for the User;
        Optional<User> optionalUser = userRepository.findByUserName(USER_NAME);
        User persistedUser = optionalUser.orElseThrow();
        List<AddressBook> pAddressBooks = persistedUser.getAddressBooks();
        assertThat(pAddressBooks.size()).isEqualTo(1);
        assertThat(pAddressBooks.get(0)).isNotNull();
        List<Contact> contacts = pAddressBooks.get(0).getContacts();
        assertThat(contacts.size()).isEqualTo(1);
        assertThat(contacts.get(0)).isNotNull();
        final String contactHash = DigestUtils.md5DigestAsHex((FIRST_NAME.toLowerCase()
                + LAST_NAME.toLowerCase()
                + MIDDLE_NAME.toLowerCase()
                + PHONE_NUMBER.toLowerCase())
                .getBytes(StandardCharsets.UTF_8));
        assertThat(contactHash).isEqualTo(contacts.get(0).getContactHash());
    }
}