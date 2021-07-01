package com.reecegroup.addressbook.exception;

public class AddressBookExistsException extends RuntimeException {
    private final String addressBookName;
    private final String userName;

    public AddressBookExistsException(String addressBookName, String userName) {
        this.addressBookName = addressBookName;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddressBookName() {
        return addressBookName;
    }
}
