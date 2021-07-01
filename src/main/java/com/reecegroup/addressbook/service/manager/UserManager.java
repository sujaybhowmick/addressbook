package com.reecegroup.addressbook.service.manager;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.exception.UserExistsException;
import com.reecegroup.addressbook.exception.UserNotFoundException;
import com.reecegroup.addressbook.model.response.UniqueContact;
import com.reecegroup.addressbook.repository.AddressBookRepository;
import com.reecegroup.addressbook.repository.ContactRepository;
import com.reecegroup.addressbook.repository.UserRepository;
import com.reecegroup.addressbook.service.BaseService;
import com.reecegroup.addressbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManager extends BaseService implements UserService {

    private final Logger log = LoggerFactory.getLogger(AddressBookManager.class);

    public UserManager(UserRepository userRepository,
                       AddressBookRepository addressBookRepository,
                       ContactRepository contactRepository) {
        super(userRepository, addressBookRepository, contactRepository);
    }

    @Override
    public User create(User user) {
        try {
            return this.userRepository.save(user);
        }catch(DataIntegrityViolationException e) {
            log.info(String.format("User %s already exists", user.getUserName()));
            throw new UserExistsException(e.getCause(), user.getUserName());
        }
    }

    @Override
    public Collection<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(final String userName) {
        Optional<User> user = this.userRepository.findByUserName(userName);
        if (user.isPresent()) {
            return user.get();
        }else {
            log.info(String.format("User %s not found", userName));
            throw new UserNotFoundException(userName);
        }
    }

    @Override
    public Collection<UniqueContact> getUniqueContactsByUser(Long userId) {
        User user = this.getUserById(userId);
        List<AddressBook> addressBooks = addressBookRepository.findByOwner(user);
        List<Contact> contacts = contactRepository.findByAddressBookIn(addressBooks);
        HashMap<String, UniqueContact> uniqueContacts = new HashMap<>();
        for (Contact contact : contacts) {
            uniqueContacts.put(contact.getContactHash(), createUniqueContact(contact));
        }
        return uniqueContacts.values();
    }

    private UniqueContact createUniqueContact(Contact contact) {
        final UniqueContact.Builder builder = new UniqueContact.Builder();
        return builder.firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .middleName(contact.getMiddleName())
                .phoneNumber(contact.getPhoneNumber())
                .build();
    }
}
