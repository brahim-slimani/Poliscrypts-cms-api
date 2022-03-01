package com.poliscrypts.api.endpoints.unit.service;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.repository.ContactRepository;
import com.poliscrypts.api.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void when_GivenContact_shouldUpdate_ifFound() {
        Contact contact = new Contact();
        given(contactRepository.findByUuid(contact.getUuid())).willReturn(Optional.of(contact));
        contactService.updateContact(contact);
        verify(contactRepository).saveAndFlush(contact);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_contact_does_not_exists() {
        Contact contact = new Contact();
        given(contactRepository.findByUuid(contact.getUuid()).isPresent()).willReturn(false);
        contactService.updateContact(contact);
    }
}
