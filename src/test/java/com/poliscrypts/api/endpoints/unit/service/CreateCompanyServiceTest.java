package com.poliscrypts.api.endpoints.unit.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.repository.CompanyRepository;
import com.poliscrypts.api.service.CompanyService;
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
public class CreateCompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void whenCreateCompany_shouldReturnCompany() {
        Company company = new Company();
        when(companyRepository.save(ArgumentMatchers.any(Company.class))).thenReturn(company);
        Company created = (Company) companyService.addNewCompany(company).getData();
        assertThat(created.getId()).isSameAs(company.getId());
        verify(companyRepository).save(company);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_company_exists_withSame_tvaNumber() {
        Company company = new Company();
        company.setTvaNumber(Long.valueOf(490));
        given(companyRepository.findByTvaNumber(company.getTvaNumber()).isPresent()).willReturn(true);
        companyService.addNewCompany(company);
    }
}
