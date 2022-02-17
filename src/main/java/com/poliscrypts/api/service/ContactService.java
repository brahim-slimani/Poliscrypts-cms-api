package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    /**
     * Retrieve all contacts
     * @return code, message, list of contacts
     */
    public ExtendedGenericPojoResponse getContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return new ExtendedGenericPojoResponse(contacts);
    }
}
