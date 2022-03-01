package com.poliscrypts.api.endpoints.unit.controller;

import com.poliscrypts.api.ContactManagementApiApplication;
import com.poliscrypts.api.endpoints.unit.utility.Utils;
import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactManagementApiApplication.class)
public class DeleteContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    @Autowired
    Utils utils;

    @Test
    public void deleteContact_whenDeleteMethod() throws Exception {
        Contact contact = new Contact();
        contact.setUuid(UUID.randomUUID());
        GenericPojoResponse response = new GenericPojoResponse(0, "Success");
        when(service.deleteContact(contact.getUuid())).thenReturn(response);
        mockMvc.perform(delete("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", utils.generateAuthJWTToken())
                .param("uuid", String.valueOf(contact.getUuid())))
                .andExpect(status().isOk());
    }

}
