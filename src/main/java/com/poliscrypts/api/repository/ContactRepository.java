package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findContactByFirstNameAndLastName(String firstName, String lastName);
}
