package com.reecegroup.addressbook.service;

import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.model.response.UniqueContact;

import java.util.Collection;

public interface UserService {
    User create(final User user);

    Collection<User> getUsers();

    User getUser(final String owner);

    Collection<UniqueContact> getUniqueContactsByUser(final Long userId);

}
