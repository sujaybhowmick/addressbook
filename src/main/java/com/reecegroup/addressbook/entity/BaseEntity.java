package com.reecegroup.addressbook.entity;

import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

public class BaseEntity {

    @CreatedDate
    private Instant createdAt;
}
