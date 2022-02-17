package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MessageSource messageSource;

    /**
     * Retrieve all contacts
     * @return code, message, list of contacts
     */
    public ExtendedGenericPojoResponse getContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return new ExtendedGenericPojoResponse(contacts);
    }

    /**
     * Create new contact
     * @param contact paylod body of the conatct
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse addNewContact(Contact contact) {
        if(contactRepository.findContactByFirstNameAndLastName(contact.getFirstName(), contact.getLastName()).isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.alreadyExist", null, new Locale("en")), 400);
        }
        contactRepository.saveAndFlush(contact);
        return new ExtendedGenericPojoResponse(contact);
    }

    /**
     * Update contact
     * @param contact paylod body of the conatct
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse updateContact(Contact contact) {
        if(!contactRepository.findById(contact.getId()).isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400);
        }
        contactRepository.saveAndFlush(contact);
        return new ExtendedGenericPojoResponse(contact);
    }

    /**
     * Delete contact
     * @param id contact id should be deleted
     * @return code, message, contact object
     */
    public GenericPojoResponse deleteContact(Integer id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if(!contact.isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400);
        }
        contactRepository.delete(contact.get());
        return new GenericPojoResponse(0, messageSource.getMessage("SUCCESS", null, new Locale("en")));
    }

}
