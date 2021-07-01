package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByAddressBookIn(final List<AddressBook> addressBooks);

    Optional<Contact> findByContactHashAndAddressBook(final String contactHash, final AddressBook addressBook);

}
