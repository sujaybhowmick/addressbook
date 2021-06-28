package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
