package com.poliscrypts.api.endpoints.unit.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.repository.CompanyRepository;
import com.poliscrypts.api.service.CompanyService;
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
public class DeleteCompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void whenGivenCompanyId_shouldDelete_ifFound() {
        Company company = new Company();
        company.setUuid(java.util.UUID.randomUUID());
        when(companyRepository.findByUuid(company.getUuid())).thenReturn(Optional.of(company));
        companyService.deleteCompany(company.getUuid());
        verify(companyRepository).delete(company);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_company_does_not_exists() {
        Company company = new Company();
        company.setUuid(java.util.UUID.randomUUID());
        given(companyRepository.findByUuid(company.getUuid()).isPresent()).willReturn(false);
        companyService.deleteCompany(company.getUuid());
    }
}
