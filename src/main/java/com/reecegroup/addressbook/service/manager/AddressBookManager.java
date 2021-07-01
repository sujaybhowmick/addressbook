package com.reecegroup.addressbook.service.manager;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.exception.AddressBookExistsException;
import com.reecegroup.addressbook.exception.AddressBookNotFoundException;
import com.reecegroup.addressbook.exception.ContactExistsException;
import com.reecegroup.addressbook.exception.ContactNotFoundException;
import com.reecegroup.addressbook.repository.AddressBookRepository;
import com.reecegroup.addressbook.repository.ContactRepository;
import com.reecegroup.addressbook.repository.UserRepository;
import com.reecegroup.addressbook.service.AddressBookService;
import com.reecegroup.addressbook.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class AddressBookManager extends BaseService implements AddressBookService {

    private final Logger log = LoggerFactory.getLogger(AddressBookManager.class);

    public AddressBookManager(UserRepository userRepository,
                              AddressBookRepository addressBookRepository,
                              ContactRepository contactRepository) {
        super(userRepository, addressBookRepository, contactRepository);
    }


    @Override
    @Transactional
    public AddressBook create(String name, Long userId) {
        User user = this.getUserById(userId);
        AddressBook addressBook = AddressBookManagerHelper.createAddressBook(name);
        addressBook.setOwner(user);
        user.getAddressBooks().add(addressBook);
        try {
            return addressBookRepository.save(addressBook);
        }catch (DataIntegrityViolationException de) {
            log.error("Duplicate Address book");
            throw new AddressBookExistsException(name, user.getUserName());
        }
    }

    @Override
    public AddressBook getAddressBook(Long addressId) {
        return this.getAddressBookById(addressId).orElseThrow();
    }

    @Override
    public Collection<AddressBook> getAllAddressBooksByUser(Long userId) {
        return getUserById(userId).getAddressBooks();
    }

    @Override
    public Collection<Contact> getContactsByAddressBook(Long addressBookId) {
        final Optional<AddressBook> optionalAddressBook = this.getAddressBookById(addressBookId);
        if (optionalAddressBook.isPresent()) {
            AddressBook addressBook = optionalAddressBook.get();
            return addressBook.getContacts();
        }
        log.info(String.format("Address Book with %d not found", addressBookId));
        throw new AddressBookNotFoundException(addressBookId);
    }

    @Override
    public Contact addContact(final Contact contact, final Long addressBookId) {
        Optional<AddressBook> optionalAddressBook = this.getAddressBookById(addressBookId);
        if(optionalAddressBook.isPresent()) {
            final AddressBook addressBook = optionalAddressBook.get();
            if(this.contactExistsInAddressBook(contact.getContactHash(), addressBook)) {
                throw new ContactExistsException(contact);
            }
            contact.setAddressBook(addressBook);
            return contactRepository.save(contact);
        }
        log.info(String.format("Address book with id %d not found", addressBookId));
        throw new AddressBookNotFoundException(addressBookId);
    }

    @Override
    public Contact removeContact(Long contactId) {
        final Optional<Contact> optionalContact = contactRepository.findById(contactId);
        if(optionalContact.isPresent()) {
            final Contact contact = optionalContact.get();
            contactRepository.delete(contact);
            return contact;
        }
        log.info(String.format("Contact with id %d not found", contactId));
        throw new ContactNotFoundException(contactId);
    }
}
