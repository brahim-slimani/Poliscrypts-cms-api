package com.poliscrypts.api.controller;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contact")
    public ExtendedGenericPojoResponse getContacts() {
        return contactService.getContacts();
    }

    @PostMapping("/contact")
    public ExtendedGenericPojoResponse addContact(@RequestBody Contact contact) {
        return contactService.addNewContact(contact);
    }
}