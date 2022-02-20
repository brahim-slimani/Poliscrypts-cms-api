package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.exception.CompanyException;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MessageSource messageSource;

    /**
     * Retrieve all companies
     *
     * @return code, message, list of companies
     */
    public ExtendedGenericPojoResponse getCompanies() {
        List<Company> companies = companyRepository.findAll();
        return new ExtendedGenericPojoResponse(companies);
    }

    /**
     * Create new company
     *
     * @param company payload body of the company
     * @return code, message, company object
     */
    public ExtendedGenericPojoResponse addNewCompany(Company company) {
        if (companyRepository.findByTvaNumber(company.getTvaNumber()).isPresent()) {
            throw new CompanyException(messageSource.getMessage("company.alreadyExist", null, new Locale("en")), 400);
        }
        companyRepository.save(company);
        return new ExtendedGenericPojoResponse(company);
    }

    /**
     * Update company
     *
     * @param company payload body of the company
     * @return code, message, company object
     */
    public ExtendedGenericPojoResponse updateCompany(Company company) {
        companyRepository.findById(company.getId())
                .orElseThrow(() -> new ContactException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400));
        companyRepository.save(company);
        return new ExtendedGenericPojoResponse(company);
    }

    /**
     * Delete company
     *
     * @param id company id should be deleted
     * @return code, message, company object
     */
    public GenericPojoResponse deleteCompany(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        company.orElseThrow(() ->
                new ContactException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400));
        companyRepository.delete(company.get());
        return new GenericPojoResponse(0, "Success");
    }

}
