package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryTest {
    protected static final String USER_NAME = "FooFighter360";
    protected static final String ADDRESS_BOOK_NAME = "Personal1";
    protected static final String FIRST_NAME = "Foo";
    protected static final String LAST_NAME = "Bar";
    protected static final String MIDDLE_NAME = "Fighter";
    protected static final String PHONE_NUMBER = "0420888319";

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AddressBookRepository addressBookRepository;

    @Autowired
    protected ContactRepository contactRepository;

    @Autowired
    protected EntityManager entityManager;

    protected User saveUser(final String userName) {
        final User user = RepositoryTestHelper.createUser(userName);
        return userRepository.save(user);
    }

    protected AddressBook saveAddressBookForUser(final String name, final User user) {
        final AddressBook addressBook = RepositoryTestHelper.createAddressBook(name);
        //addressBook.setOwner(user);
        //return addressBookRepository.save(addressBook);
        user.getAddressBooks().add(addressBook);
        userRepository.save(user);
        return addressBook;
    }
}
