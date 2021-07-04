package com.reecegroup.addressbook.model;

public class FieldError {
    private final String objectName;

    private final String field;

    private final String message;

    private final Object invalidValue;


    public FieldError(String objectName, String field, Object invalidValue, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
        this.invalidValue = invalidValue;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }
}
