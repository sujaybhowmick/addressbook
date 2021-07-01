package com.reecegroup.addressbook.model.request;

public class ContactRequest {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String phoneNumber;
    private final Long addressBookId;


    public ContactRequest(String firstName, String lastName, String middleName, String phoneNumber, Long addressBookId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.addressBookId = addressBookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getAddressBookId() {
        return addressBookId;
    }
}
