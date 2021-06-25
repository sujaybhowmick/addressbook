package com.reecegroup.addressbook.repository;

import com.reecegroup.addressbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(final String userName);
}
