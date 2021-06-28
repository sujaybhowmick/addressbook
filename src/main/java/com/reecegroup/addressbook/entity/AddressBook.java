package com.reecegroup.addressbook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address_books")
public class AddressBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "addressbook_seq")
    @SequenceGenerator(name = "addressbook_seq", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "addressBookName", unique = true, nullable = false)
    private String addressBookName;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_book_id")
    private List<Contact> contacts = new ArrayList<>();


    public AddressBook() {
    }

    public AddressBook(Builder builder) {
        this.addressBookName = builder.addressBookName;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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


    public static class Builder {
        private final String addressBookName;

        public Builder(final String addressBookName) {
            this.addressBookName = addressBookName;
        }

        public AddressBook build() {
            return new AddressBook(this);
        }
    }
}
