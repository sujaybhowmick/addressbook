package com.reecegroup.addressbook.controller;

import com.reecegroup.addressbook.entity.AddressBook;
import com.reecegroup.addressbook.entity.Contact;
import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.model.request.AddressBookRequest;
import com.reecegroup.addressbook.model.response.AddressBookResponse;
import com.reecegroup.addressbook.model.response.DataResponse;
import com.reecegroup.addressbook.service.AddressBookService;
import com.reecegroup.addressbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AddressBookController {

    private Logger log = LoggerFactory.getLogger(AddressBookController.class);
    private final UserService userService;
    private final AddressBookService addressBookService;

    public AddressBookController(final UserService userService, final AddressBookService addressBookService) {
        this.userService = userService;
        this.addressBookService = addressBookService;
    }

    @PostMapping(value = "/addressBook",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressBookResponse> create(@RequestBody AddressBookRequest addressBookRequest) {
        log.info("Creating address book");
        final User user = this.userService.getUser(addressBookRequest.getOwner());
        final AddressBook addressBook = this.addressBookService.create(addressBookRequest.getAddressBookName(),
                user.getId());
        return ResponseEntity.ok(new AddressBookResponse(addressBook));
    }

    @GetMapping(value = "/addressBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<Collection<AddressBook>>> list(@RequestParam Long userId) {
        return ResponseEntity.ok(new DataResponse<>(this.addressBookService.getAllAddressBooksByUser(userId)));

    }

    @GetMapping(value = "/addressBook/{addressId}/contact/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<Collection<Contact>>> contactList(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(new DataResponse<>(this.addressBookService.getContactsByAddressBook(addressId)));
    }
}
