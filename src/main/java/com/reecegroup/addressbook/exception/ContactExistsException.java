package com.reecegroup.addressbook.exception;

import com.reecegroup.addressbook.entity.Contact;

public class ContactExistsException extends RuntimeException {

    private final Contact contact;

    public ContactExistsException(Contact contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return this.contact.getFirstName();
    }

    public String getLastName() {
        return this.contact.getLastName();
    }

    public String getMiddleName() {
        return this.contact.getLastName();
    }

    public String getPhoneNumber() {
        return this.contact.getPhoneNumber();
    }
}
