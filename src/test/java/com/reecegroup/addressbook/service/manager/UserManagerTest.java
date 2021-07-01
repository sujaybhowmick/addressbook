package com.reecegroup.addressbook.service.manager;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.exception.UserNotFoundException;
import com.reecegroup.addressbook.model.response.UniqueContact;
import com.reecegroup.addressbook.repository.RepositoryTestHelper;
import com.reecegroup.addressbook.service.AddressBookService;
import com.reecegroup.addressbook.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserManagerTest {


    @Autowired
    private UserService userManager;

    @Autowired
    private AddressBookService addressBookService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        final User user = RepositoryTestHelper.createUser("sujaybhowmick");
        final User pUser = userManager.create(user);
        assertThat(pUser.getId()).isNotNull();
    }

    @Test
    void getUser() {
        final String owner = "sujaybhowmick2";
        final User user = RepositoryTestHelper.createUser(owner);
        final User pUser = userManager.create(user);
        assertThat(userManager.getUser(owner)).isNotNull();
    }

    @Test
    void testForUserNotFoundException() {
        final String owner = "notfound";
        try {
            userManager.getUser(owner);
            fail("User exists, test failed");
        } catch (UserNotFoundException e) {
            assertThat(true).isTrue();
        }
    }

    @Test
    void getUniqueContactsByUserId() {
        final User user = RepositoryTestHelper.createUser("FooFighter1");
        final User pUser = userManager.create(user);
        assertThat(pUser.getId()).isNotNull();
        AddressBook pAddressBook1 = addressBookService.create("Addressbook1", pUser.getId());
        AddressBook pAddressBook2 = addressBookService.create("Addressbook2", pUser.getId());
        addressBookService.addContact(
                AddressBookManagerHelper.createContact(
                        "John",
                        "Doe",
                        "M",
                        "8374208880"), pAddressBook1.getId());
        addressBookService.addContact(
                AddressBookManagerHelper.createContact(
                        "John",
                        "Doe",
                        "M",
                        "8374208880"), pAddressBook2.getId());

        Collection<UniqueContact> uniqueContacts = userManager.getUniqueContactsByUser(pUser.getId());
        assertThat(uniqueContacts.size()).isEqualTo(1);
    }
}