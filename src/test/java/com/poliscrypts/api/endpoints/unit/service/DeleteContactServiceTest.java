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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void whenGivenContactId_shouldDelete_ifFound() {
        Contact contact = new Contact();
        contact.setId(1);
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
        //contactService.deleteContact(contact.getId());
        verify(contactRepository).delete(contact);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_contact_does_not_exists() {
        Contact contact = new Contact();
        contact.setId(1);
        given(contactRepository.findById(contact.getId()).isPresent()).willReturn(false);
        //contactService.deleteContact(contact.getId());
    }
}
