package com.reecegroup.addressbook.service.manager;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class AddressBookManagerHelper {
    public static User createUser(final String userName,
                                  final String firstName,
                                  final String lastName,
                                  final String middleName) {
        final User.Builder builder = new User.Builder(userName);
        return builder
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
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

    public static String contactHash(Contact contact, String addressBookName, String userName) {
        StringBuilder sb = new StringBuilder(contact.getFirstName().toLowerCase())
                .append(contact.getLastName().toLowerCase());
        sb.append(contact.getMiddleName() != null ? contact.getMiddleName().toLowerCase() : "");
        sb.append(contact.getPhoneNumber())
                .append(addressBookName.toLowerCase())
                .append(userName.toLowerCase());
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

}
