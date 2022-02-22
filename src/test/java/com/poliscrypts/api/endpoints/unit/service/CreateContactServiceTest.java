package com.poliscrypts.api.endpoints.unit.service;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.repository.ContactRepository;
import com.poliscrypts.api.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void whenCreateContact_shouldReturnCompany() {
        Contact contact = new Contact();
        when(contactRepository.save(ArgumentMatchers.any(Contact.class))).thenReturn(contact);
        Contact created = (Contact) contactService.addNewContact(contact).getData();
        assertThat(created.getId()).isSameAs(contact.getId());
        verify(contactRepository).save(contact);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_company_exists_withSame_FirstAndLastName() {
        Contact contact = new Contact();
        contact.setFirstName("Rafik");
        contact.setLastName("Nadir");
        given(contactRepository.findContactByFirstNameAndLastName(contact.getFirstName(), contact.getLastName()).isPresent()).willReturn(true);
        contactService.addNewContact(contact);
    }
}
