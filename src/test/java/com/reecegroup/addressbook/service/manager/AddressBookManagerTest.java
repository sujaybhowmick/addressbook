package com.reecegroup.addressbook.service.manager;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.repository.ContactRepository;
import com.reecegroup.addressbook.repository.RepositoryTestHelper;
import com.reecegroup.addressbook.service.AddressBookService;
import com.reecegroup.addressbook.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AddressBookManagerTest {

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userManager;

    @Autowired
    private ContactRepository contactRepository;


    @Test
    void create() {
        final User pUser = createUser("user1");
        final AddressBook addressBook = createAddressBook("AddressBook1", pUser);
        assertThat(addressBook).isNotNull();
    }


    @Test
    void getAddressBook() {
        final User pUser = createUser("user2");
        assertThat(pUser.getId()).isNotNull();
        final AddressBook addressBook = createAddressBook("AddressBook2", pUser);
        assertThat(addressBook).isNotNull();
        assertThat(addressBookService.getAddressBook(addressBook.getId())).isNotNull();
    }

    @Test
    void getAllAddressBooksByUser() {
        final User pUser = createUser("user3");
        final AddressBook addressBook = createAddressBook("AddressBook3", pUser);
        assertThat(addressBook).isNotNull();
        assertThat(addressBookService.getAllAddressBooksByUser(pUser.getId()).size()).isEqualTo(1);
    }

    @Test
    void getContactsByAddressBook() {
        final User pUser = createUser("user4");
        assertThat(pUser.getId()).isNotNull();
        AddressBook pAddressBook1 = addressBookService.create("Addressbook4", pUser.getId());
        assertThat(pAddressBook1).isNotNull();
        AddressBook pAddressBook2 = addressBookService.create("Addressbook5", pUser.getId());
        assertThat(pAddressBook2).isNotNull();
        Contact pContact1 = this.addContact(pAddressBook1);
        Contact pContact2 = this.addContact(pAddressBook2);
        Collection<Contact> contacts1 = this.contactRepository.findByAddressBook(pAddressBook1);
        assertThat(contacts1.size()).isEqualTo(1);
        Collection<Contact> contacts2 = this.contactRepository.findByAddressBook(pAddressBook2);
        assertThat(contacts2.size()).isEqualTo(1);
    }

    @Test
    void addContact() {
        final User pUser = createUser("user5");
        assertThat(pUser.getId()).isNotNull();
        AddressBook pAddressBook1 = addressBookService.create("Addressbook6", pUser.getId());
        assertThat(pAddressBook1).isNotNull();
        Contact pContact = this.addContact(pAddressBook1);
        Collection<Contact> contacts1 = this.contactRepository.findByAddressBook(pAddressBook1);
        assertThat(contacts1.size()).isEqualTo(1);
    }

    @Test
    void removeContact() {
        final User pUser = createUser("user6");
        assertThat(pUser.getId()).isNotNull();
        AddressBook pAddressBook1 = addressBookService.create("Addressbook7", pUser.getId());
        assertThat(pAddressBook1).isNotNull();
        Contact contact = this.addContact(pAddressBook1);
        List<Contact> contacts = this.contactRepository.findByAddressBook(pAddressBook1);
        assertThat(contacts.size()).isEqualTo(1);
        addressBookService.removeContact(contacts.get(0).getId());
        contacts = this.contactRepository.findByAddressBook(pAddressBook1);
        assertThat(contacts.size()).isEqualTo(0);

    }

    private User createUser(final String userName) {
        final User user = RepositoryTestHelper.createUser(userName);
        return userManager.create(user);
    }

    private Contact addContact(AddressBook addressBook) {
        return addressBookService.addContact(
                AddressBookManagerHelper.createContact(
                        "John",
                        "Doe",
                        "M",
                        "8374208880"), addressBook.getId());
    }

    private AddressBook createAddressBook(String addressBookName, User user) {
        return addressBookService.create(addressBookName, user.getId());
    }
}