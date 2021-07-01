package com.reecegroup.addressbook.controller;

import com.reecegroup.addressbook.entity.User;
import com.reecegroup.addressbook.model.response.DataResponse;
import com.reecegroup.addressbook.model.response.UniqueContact;
import com.reecegroup.addressbook.model.response.UserResponse;
import com.reecegroup.addressbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody User user) {
        log.info("Creating user {}", user.getUserName());
        return ResponseEntity.ok(new UserResponse(userService.create(user)));
    }

    @GetMapping(value = "/user/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<Collection<User>>> list() {
        log.info("Listing users");
        return ResponseEntity.ok(new DataResponse<>(userService.getUsers()));
    }

    @GetMapping(value = "/user/{userId}/contact/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<Collection<UniqueContact>>> uniqueContactList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(new DataResponse<>(this.userService.getUniqueContactsByUser(userId)));
    }
}
