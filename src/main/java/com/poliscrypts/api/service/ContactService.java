package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.enumeration.ContactTypeEnum;
import com.poliscrypts.api.exception.CompanyException;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.repository.CompanyRepository;
import com.poliscrypts.api.repository.ContactRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@CommonsLog
@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

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
     * Secondly before persist the object we make checkout for tva constraint regarding employee & freelancer contact
     *
     * @param contact payload body of the contact
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse addNewContact(Contact contact) {
        if (contactRepository.findContactByFirstNameAndLastName(contact.getFirstName(), contact.getLastName()).isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.alreadyExist", null, new Locale("en")), 400);
        }
        //CHECK THE TVA NUMBER CONSTRAINT ACCORDING TO CONTACT TYPE
        if (contact.getContactType() == ContactTypeEnum.FREELANCER && contact.getTvaNumber() == null) {
            throw new ContactException(messageSource.getMessage("contact.tva.freelancer", null, new Locale("en")), 400);
        } else if (contact.getContactType() == ContactTypeEnum.EMPLOYEE) {
            contact.setTvaNumber(null);
        }
        return new ExtendedGenericPojoResponse(contactRepository.save(contact));
    }

    /**
     * Update contact
     *
     * @param contact payload body of the contact
     * @return code, message, contact object
     */
    public ExtendedGenericPojoResponse updateContact(Contact contact) {
        Contact contactToBeUpdated = contactRepository.findByUuid(contact.getUuid())
                .orElseThrow(() -> new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400));
        //CHECK THE TVA NUMBER CONSTRAINT ACCORDING TO CONTACT TYPE
        if (contact.getContactType() == ContactTypeEnum.FREELANCER && contact.getTvaNumber() == null) {
            throw new ContactException(messageSource.getMessage("contact.tva.freelancer", null, new Locale("en")), 400);
        } else if (contact.getContactType() == ContactTypeEnum.EMPLOYEE) {
            contact.setTvaNumber(null);
        }
        contact.setId(contactToBeUpdated.getId());
        contact.setCompanies(contactToBeUpdated.getCompanies());
        return new ExtendedGenericPojoResponse(contactRepository.saveAndFlush(contact));
    }

    /**
     * Delete contact
     *
     * @param uuid index of contact that should be deleted
     * @return code, message, contact object
     */
    public GenericPojoResponse deleteContact(UUID uuid) {
        Optional<Contact> contact = contactRepository.findByUuid(uuid);
        contact.orElseThrow(() ->
                new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400));
        contactRepository.delete(contact.get());
        return new GenericPojoResponse(0, "Success");
    }

    /**
     * Assign a contact into specific company
     *
     * @param contactUuid contact id to be assigned
     * @param companyUuid company id
     * @return code, message
     */
    public GenericPojoResponse assignContact2Company(UUID contactUuid, UUID companyUuid) {
        Optional<Contact> contact = contactRepository.findByUuid(contactUuid);
        Optional<Company> company = companyRepository.findByUuid(companyUuid);
        contact.orElseThrow(() ->
                new ContactException(messageSource.getMessage("contact.notExist", null, new Locale("en")), 400));
        company.orElseThrow(() ->
                new CompanyException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400));
        Contact pojoContact = contact.get();
        Set<Company> companies = pojoContact.getCompanies();
        companies.add(company.get());
        pojoContact.setCompanies(companies);
        contactRepository.saveAndFlush(pojoContact);
        return new GenericPojoResponse(0, "Success");
    }

}
