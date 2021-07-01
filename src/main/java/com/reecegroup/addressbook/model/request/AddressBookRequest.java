package com.reecegroup.addressbook.model.request;

public class AddressBookRequest {
    private final String addressBookName;

    private final String owner;

    public AddressBookRequest(String addressBookName, String owner) {
        this.addressBookName = addressBookName;
        this.owner = owner;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public String getOwner() {
        return owner;
    }
}
