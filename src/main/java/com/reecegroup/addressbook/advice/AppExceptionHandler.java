package com.reecegroup.addressbook.advice;

import com.reecegroup.addressbook.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

    Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    @Value(value = "${exception.all.message}")
    private String exceptionMessage;

    @Value(value = "${exception.user.exists.message}")
    private String userExistsMessage;

    @Value(value = "${exception.user.not-found.message}")
    private String userNotFoundMessage;

    @Value(value = "${exception.addressBook.exists.message}")
    private String addressBookExistsMessage;

    @Value(value = "${exception.addressBook.not-found.message}")
    private String addressBookNotFoundMessage;

    @Value(value = "${exception.contact.not-found.message}")
    private String contactNotFoundMessage;

    @Value(value = "${exception.contact.exists.message}")
    private String contactExistsMessage;

    @ExceptionHandler({UserExistsException.class})
    public ResponseEntity<?> handleUserExistsException(final UserExistsException userExistsException,
                                                       final WebRequest request) {
        log.error(userExistsMessage, userExistsException);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                userExistsException.getClass().getSimpleName(),
                String.format(userExistsMessage, userExistsException.getUserName()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AddressBookExistsException.class})
    public ResponseEntity<?> handleAddressBookExistsException(
            final AddressBookExistsException addressBookExistsException,
            final WebRequest request) {
        log.error(addressBookExistsMessage, addressBookExistsException);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                addressBookExistsException.getClass().getSimpleName(),
                String.format(addressBookExistsMessage, addressBookExistsException.getAddressBookName(),
                        addressBookExistsException.getUserName()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AddressBookNotFoundException.class})
    public ResponseEntity<?> handleAddressBookNotFoundException(
            final AddressBookNotFoundException addressBookNotFoundException,
            final WebRequest webRequest) {
        log.error(addressBookNotFoundMessage, addressBookNotFoundException);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                addressBookNotFoundException.getClass().getSimpleName(),
                String.format(addressBookNotFoundMessage, addressBookNotFoundException.getAddressBookId()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ContactExistsException.class})
    public ResponseEntity<?> handleContactExistsException(
            final ContactExistsException contactExistsException,
            final WebRequest webRequest) {
        log.error(contactExistsMessage, contactExistsException);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                contactExistsException.getClass().getSimpleName(),
                String.format(contactExistsMessage,
                        contactExistsException.getFirstName(),
                        contactExistsException.getLastName(),
                        contactExistsException.getPhoneNumber()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ContactNotFoundException.class})
    public ResponseEntity<?> handleContactNotFoundException(
            final ContactNotFoundException contactNotFoundException,
            final WebRequest webRequest) {
        log.error(contactNotFoundMessage, contactNotFoundException);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                contactNotFoundException.getClass().getSimpleName(),
                String.format(contactNotFoundMessage, contactNotFoundException.getContactId()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException userNotFoundException,
                                                              final WebRequest request) {
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
                userNotFoundException.getClass().getSimpleName(),
                String.format(userNotFoundMessage, userNotFoundException.getUserName()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleExceptions(Exception exception) {
        log.error(exceptionMessage, exception);
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getCause().getClass().getSimpleName(), exceptionMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
