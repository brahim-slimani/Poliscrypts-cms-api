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

@RunWith(MockitoJUnitRunner.class)
public class UpdateCompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void when_GivenCompany_shouldUpdate_ifFound() {
        Company company = new Company();
        given(companyRepository.findById(company.getId())).willReturn(Optional.of(company));
        companyService.updateCompany(company);
        verify(companyRepository).save(company);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_company_does_not_exists() {
        Company company = new Company();
        given(companyRepository.findById(company.getId()).isPresent()).willReturn(false);
        companyService.updateCompany(company);
    }
}
