package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    Optional<AddressBook> findByAddressBookName(final String addressBookName);

    Optional<AddressBook> findByIdAndOwner(final Long id, final User user);

    List<AddressBook> findByOwner(final User owner);
}
