package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.entity.ContactType;
import com.poliscrypts.api.exception.CompanyException;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.repository.CompanyRepository;
import com.poliscrypts.api.repository.ContactRepository;
import com.poliscrypts.api.repository.ContactTypeRepository;
import com.poliscrypts.api.utility.ContactHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ContactTypeRepository contactTypeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MessageSource messageSource;

    /**
     * Retrieve all contacts
     *
     * @return code, message, list of contacts
     */
    public ExtendedGenericPojoResponse getContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return new ExtendedGenericPojoResponse(contacts);
    }

    /**
     * Create new contact
     * First of all we made a check if there is an existing contact which have the same firstName & lastName
     * If the condition is verified at time an exception will be thrown
     * Secondly before persist the object we make checkout through ContactHelper for tva constraint regarding employee & freelancer contact
     *
     * @param contact payload body of the contact
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse addNewContact(Contact contact) {
        if (contactRepository.findContactByFirstNameAndLastName(contact.getFirstName(), contact.getLastName()).isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.alreadyExist", null, new Locale("en")), 400);
        }
        //CHECK THE TVA NUMBER CONSTRAINT FOR EMPLOYEE & FREELANCER CONTACT
        ContactHelper.verifiedTvaConstraint(contactTypeRepository, contact, messageSource);
        contact.setContactType(contactTypeRepository.findByType(contact.getContactType().getType()));
        contactRepository.saveAndFlush(contact);
        return new ExtendedGenericPojoResponse(contact);
    }

    /**
     * Update contact
     *
     * @param contact payload body of the contact
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse updateContact(Contact contact) {
        if (!contactRepository.findById(contact.getId()).isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400);
        }
        //CHECK THE TVA NUMBER CONSTRAINT FOR EMPLOYEE & FREELANCER CONTACT
        ContactHelper.verifiedTvaConstraint(contactTypeRepository, contact, messageSource);
        contact.setContactType(contactTypeRepository.findByType(contact.getContactType().getType()));
        contactRepository.saveAndFlush(contact);
        return new ExtendedGenericPojoResponse(contact);
    }

    /**
     * Delete contact
     *
     * @param id contact id should be deleted
     * @return code, message, contact object
     */
    public GenericPojoResponse deleteContact(Integer id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (!contact.isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400);
        }
        contactRepository.delete(contact.get());
        return new GenericPojoResponse(0, messageSource.getMessage("SUCCESS", null, new Locale("en")));
    }

    /**
     * Assign a contact into specific company
     * @param contactId contact id to be assigned
     * @param companyId company id
     * @return code, message
     */
    public GenericPojoResponse assignContact2Company(Integer contactId, Integer companyId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        Optional<Company> company = companyRepository.findById(companyId);
        if(!contact.isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400);
        }
        if(!company.isPresent()) {
            throw new CompanyException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400);
        }
        Contact pojoContact = contact.get();
        List<Company> companies = pojoContact.getCompanies();
        companies.add(company.get());
        pojoContact.setCompanies(companies);
        contactRepository.saveAndFlush(pojoContact);
        return new GenericPojoResponse(0, messageSource.getMessage("SUCCESS", null, new Locale("en")));
    }

}
