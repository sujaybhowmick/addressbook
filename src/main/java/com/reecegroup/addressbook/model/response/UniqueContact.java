package com.reecegroup.addressbook.model.response;

public class UniqueContact {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String phoneNumber;

    private UniqueContact(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.phoneNumber = builder.phoneNumber;
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

    public static class Builder {
        private String firstName;
        private String lastName;
        private String middleName;
        private String phoneNumber;

        public Builder() {
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UniqueContact build() {
            return new UniqueContact(this);
        }

    }
}
