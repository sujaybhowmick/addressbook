package com.reecegroup.addressbook.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contact_hash", nullable = false, unique = true, length = 25)
    private String contactHash;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name="middle_name",
            length = 100)
    private String middleName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

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
}
