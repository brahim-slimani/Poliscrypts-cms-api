package com.poliscrypts.api.controller;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.service.ContactService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contacts")
    public ExtendedGenericPojoResponse getContacts() {
        return contactService.getContacts();
    }

    @PostMapping("/contact")
    public ExtendedGenericPojoResponse addContact(@RequestBody Contact contact) {
        return contactService.addNewContact(contact);
    }

    @PatchMapping("/contact")
    public ExtendedGenericPojoResponse updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @DeleteMapping("/contact")
    public GenericPojoResponse deleteContact(@RequestParam("id") Integer id) {
        return contactService.deleteContact(id);
    }

    @PatchMapping("/assign-contact2company")
    public GenericPojoResponse assignContact2Company(@RequestParam Integer contactId, @RequestParam Integer companyId) {
        return contactService.assignContact2Company(contactId, companyId);
    }
}
