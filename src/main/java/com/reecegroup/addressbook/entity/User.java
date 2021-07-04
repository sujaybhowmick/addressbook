package com.reecegroup.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, length = 25)
    @NotBlank(message = "userName is required")
    private String userName;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "firstName is required")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "lastName is required")
    private String lastName;

    @Column(name = "middle_name", length = 100)
    private String middleName;
    @OneToMany(targetEntity = AddressBook.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIgnore
    private List<AddressBook> addressBooks = new ArrayList<>();

    public User() {
    }

    public User(Builder builder) {
        this.userName = builder.userName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<AddressBook> getAddressBooks() {
        return addressBooks;
    }

    public void setAddressBooks(List<AddressBook> addressBooks) {
        this.addressBooks = addressBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder {
        private final String userName;
        private String firstName;
        private String lastName;
        private String middleName;

        public Builder(String userName) {
            this.userName = userName;
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

        public User build() {
            return new User(this);
        }
    }
}
