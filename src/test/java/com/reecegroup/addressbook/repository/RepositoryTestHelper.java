package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;

public class RepositoryTestHelper {
    public static User createUser(final String userName) {
        final User.Builder builder = new User.Builder(userName);
        return builder
                .firstName("Sujay")
                .lastName("Bhowmick")
                .build();
    }

    public static AddressBook createAddressBook(final String name) {
        final AddressBook.Builder builder = new AddressBook.Builder(name);
        return builder.build();
    }

    public static Contact createContact(final String firstName,
                                        final String lastName,
                                        final String middleName,
                                        final String phoneNumber) {
        final Contact.Builder builder = new Contact.Builder();
        return builder.firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .phoneNumber(phoneNumber)
                .contactHash()
                .build();
    }

}
