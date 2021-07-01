package com.reecegroup.addressbook.entity;

import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contact_seq")
    @SequenceGenerator(name = "contact_seq", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "contact_hash", nullable = false, length = 32)
    private String contactHash;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "middle_name",
            length = 100)
    private String middleName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @ManyToOne(targetEntity = AddressBook.class, optional = false)
    @JoinColumn(name = "address_book_id")
    private AddressBook addressBook;


    public Contact() {
    }

    public Contact(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.phoneNumber = builder.phoneNumber;
        this.contactHash = builder.contactHash;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public String getContactHash() {
        return contactHash;
    }

    public void setContactHash(String contactHash) {
        this.contactHash = contactHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private String firstName;

        private String lastName;

        private String middleName;

        private String phoneNumber;

        private String contactHash;

        public Builder() {
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder middleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder contactHash() {
            String sb = this.firstName.toLowerCase() + this.lastName.toLowerCase() +
                    (this.middleName != null ? this.middleName.toLowerCase() : "") +
                    this.phoneNumber.toLowerCase();
            this.contactHash = DigestUtils.md5DigestAsHex(sb.getBytes(StandardCharsets.UTF_8));
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }

    }
}
