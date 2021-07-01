package com.reecegroup.addressbook.model.response;

import com.reecegroup.addressbook.entity.Contact;

public class ContactResponse extends DataResponse<Contact> {
    public ContactResponse(Contact data) {
        super(data);
    }
}
