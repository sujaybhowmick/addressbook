package com.reecegroup.addressbook.exception;

public class UserExistsException extends RuntimeException {
    private final String userName;

    public UserExistsException(Throwable t, String userName) {
        super(t);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
