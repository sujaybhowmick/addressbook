package com.reecegroup.addressbook.service;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;

import java.util.Collection;

public interface AddressBookService {
    AddressBook create(final String name, final Long userId);

    AddressBook getAddressBook(Long addressId);

    Collection<AddressBook> getAllAddressBooksByUser(final Long userId);

    Collection<Contact> getContactsByAddressBook(Long addressBookId);

    Contact addContact(final Contact contact, final Long addressBookId);

    Contact removeContact(final Long contactId);
}
