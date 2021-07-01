package com.reecegroup.addressbook.exception;

public class AddressBookNotFoundException extends RuntimeException {
    private final Long addressBookId;

    public AddressBookNotFoundException(Long addressBookId) {
        this.addressBookId = addressBookId;
    }

    public Long getAddressBookId() {
        return addressBookId;
    }
}
