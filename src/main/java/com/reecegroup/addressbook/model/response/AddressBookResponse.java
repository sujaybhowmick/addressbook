package com.reecegroup.addressbook.model.response;

import com.reecegroup.addressbook.entity.AddressBook;

public class AddressBookResponse extends DataResponse<AddressBook> {
    public AddressBookResponse(AddressBook data) {
        super(data);
    }
}
