package com.reecegroup.addressbook.model.response;

import com.reecegroup.addressbook.entity.User;

public class UserResponse extends DataResponse<User> {
    public UserResponse(User user) {
        super(user);
    }
}
