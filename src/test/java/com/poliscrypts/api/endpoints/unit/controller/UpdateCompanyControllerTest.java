package com.poliscrypts.api.endpoints.unit.controller;

import com.poliscrypts.api.ContactManagementApiApplication;
import com.poliscrypts.api.endpoints.unit.utility.Utils;
import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactManagementApiApplication.class)
public class UpdateCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService service;

    @Autowired
    Utils utils;

    @Test
    public void updateCompany_whenPatchMethod() throws Exception {
        Company company = new Company();
        company.setUuid(UUID.randomUUID());
        ExtendedGenericPojoResponse response = new ExtendedGenericPojoResponse(company);
        given(service.updateCompany(ArgumentMatchers.any(Company.class))).willReturn(response);
        mockMvc.perform(patch("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", utils.generateAuthJWTToken())
                .content(utils.convert2Json(company)))
                .andExpect(status().isOk());
    }

}
