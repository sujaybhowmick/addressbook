package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.User;

public class RepositoryTestHelper {
    public static User createUser(final String userName) {
        final User.Builder builder = new User.Builder(userName);
        return builder
                .firstName("Foo")
                .lastName("Fighter")
                .build();
    }

}
