package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    Optional<AddressBook> findByAddressBookName(final String addressBookName);
}
