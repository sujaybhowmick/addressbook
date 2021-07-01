package com.reecegroup.addressbook.service;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.repository.AddressBookRepository;
import com.reecegroup.addressbook.repository.ContactRepository;
import com.reecegroup.addressbook.repository.UserRepository;

import java.util.Optional;

public abstract class BaseService {
    protected final UserRepository userRepository;
    protected final AddressBookRepository addressBookRepository;
    protected final ContactRepository contactRepository;

    public BaseService(UserRepository userRepository,
                       AddressBookRepository addressBookRepository,
                       ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.addressBookRepository = addressBookRepository;
        this.contactRepository = contactRepository;
    }

    protected boolean contactExistsInAddressBook(final String contactHash, final AddressBook addressBook) {
        Optional<Contact> optionalContact = this.contactRepository.findByContactHashAndAddressBook(contactHash,
                addressBook);
        return optionalContact.isPresent();
    }

    protected User getUserById(final Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow();
    }

    protected Optional<AddressBook> getAddressBookById(final Long addressBookId) {
        return addressBookRepository.findById(addressBookId);
    }
}
