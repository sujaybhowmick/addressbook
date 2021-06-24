package com.reecegroup.addressbook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address_books")
public class AddressBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "addressBookName", unique = true, nullable = false)
    private String addressBookName;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_book_id")
    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }
}
