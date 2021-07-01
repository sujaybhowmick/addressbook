package com.reecegroup.addressbook.controller;

import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.model.request.ContactRequest;
import com.reecegroup.addressbook.model.response.ContactResponse;
import com.reecegroup.addressbook.service.AddressBookService;
import com.reecegroup.addressbook.service.UserService;
import com.reecegroup.addressbook.service.manager.AddressBookManagerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    private Logger log = LoggerFactory.getLogger(ContactController.class);
    private final AddressBookService addressBookService;


    public ContactController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @PostMapping(value = "/contact",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactResponse> create(@RequestBody ContactRequest contactRequest) {
        final Contact contact = addressBookService.addContact(AddressBookManagerHelper
                .createContact(contactRequest.getFirstName(),
                        contactRequest.getLastName(),
                        contactRequest.getMiddleName(),
                        contactRequest.getPhoneNumber()), contactRequest.getAddressBookId());
        return ResponseEntity.ok(new ContactResponse(contact));
    }

    @DeleteMapping(value = "/contact/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ContactResponse(addressBookService.removeContact(id)));
    }
}
