package com.poliscrypts.api.controller;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(tags = "Contact", description = "CRUD operations related to contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @ApiOperation(value = "Get list of contacts", response = ExtendedGenericPojoResponse.class)
    @GetMapping("/contacts")
    public ExtendedGenericPojoResponse getContacts() {
        return contactService.getContacts();
    }

    @ApiOperation(value = "Create contact", response = ExtendedGenericPojoResponse.class)
    @PostMapping("/contact")
    public ExtendedGenericPojoResponse addContact(@RequestBody Contact contact) {
        return contactService.addNewContact(contact);
    }

    @ApiOperation(value = "Edit contact", response = ExtendedGenericPojoResponse.class)
    @PatchMapping("/contact")
    public ExtendedGenericPojoResponse updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @ApiOperation(value = "Delete contact", response = GenericPojoResponse.class)
    @DeleteMapping("/contact")
    public GenericPojoResponse deleteContact(@RequestParam("uuid") UUID uuid) {
        return contactService.deleteContact(uuid);
    }

    @ApiOperation(value = "Affect given contact into specific company", response = GenericPojoResponse.class)
    @PatchMapping("/assign-contact2company")
    public GenericPojoResponse assignContact2Company(@RequestParam(name = "contact-uuid") UUID contactUuid, @RequestParam(name = "company-uuid") UUID companyUuid) {
        return contactService.assignContact2Company(contactUuid, companyUuid);
    }
}
