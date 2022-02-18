package com.poliscrypts.api.utility;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.entity.ContactType;
import com.poliscrypts.api.enumeration.ContactTypeEnum;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.repository.ContactTypeRepository;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

public class ContactHelper {

    /**
     * Check a tva constraint validation if the contact is a freelancer so the tva number must be present otherwise it will be nullable for employee case
     * Expect freelancer contact if it does not have a tva number -> fire custom exception
     * As well as for an employee contact if a tva number has been filled
     * @param contactTypeRepository contactType jpa repository
     * @param contact body of contact sent in request
     * @param messageSource messageSource bean for customizing exception messages
     */
    public static void verifiedTvaConstraint(ContactTypeRepository contactTypeRepository, Contact contact, MessageSource messageSource) {
        Optional<ContactType> contactType = contactTypeRepository.findById(contact.getContactType().getId());
        //NOT FOUND CONTACT_TYPE CASE
        if(!contactType.isPresent()) {
            throw new ContactException(messageSource.getMessage("contact.typeNotExist", null, new Locale("en")), 400);
        //FREELANCER WITHOUT TVA NUMBER CASE
        }else if (contactType.get().getType() == ContactTypeEnum.FREELANCER.name() && contact.getTvaNumber() == null) {
            throw new ContactException(messageSource.getMessage("contact.tva.freelancer", null, new Locale("en")), 400);
        //EMPLOYEE WITH TVA NUMBER CASE
        } else if (contactType.get().getType() == ContactTypeEnum.EMPLOYEE.name() && contact.getTvaNumber() != null) {
            throw new ContactException(messageSource.getMessage("contact.tva.employee", null, new Locale("en")), 400);
        }
    }
}
