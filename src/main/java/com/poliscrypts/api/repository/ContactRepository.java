package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findContactByFirstNameAndLastName(String firstName, String lastName);
    Optional<Contact> findByUuid(UUID uuid);
}
