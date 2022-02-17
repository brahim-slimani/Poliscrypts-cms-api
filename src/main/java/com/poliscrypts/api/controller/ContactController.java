package com.poliscrypts.api.controller;

import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contact")
    public ExtendedGenericPojoResponse getContacts() {
        return contactService.getContacts();
    }
}
