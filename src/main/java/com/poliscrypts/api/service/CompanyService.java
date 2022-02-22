package com.poliscrypts.api.service;

import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.exception.CompanyException;
import com.poliscrypts.api.exception.ContactException;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.repository.CompanyRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@CommonsLog
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
        Company companyToBeUpdated = companyRepository.findByUuid(company.getUuid())
                .orElseThrow(() -> new ContactException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400));
        company.setId(companyToBeUpdated.getId());
        return new ExtendedGenericPojoResponse(companyRepository.save(company));
    }

    /**
     * Delete company
     *
     * @param uuid uuid index of company that should be deleted
     * @return code, message, company object
     */
    public GenericPojoResponse deleteCompany(UUID uuid) {
        Optional<Company> company = companyRepository.findByUuid(uuid);
        company.orElseThrow(() ->
                new ContactException(messageSource.getMessage("company.notExist", null, new Locale("en")), 400));
        companyRepository.delete(company.get());
        return new GenericPojoResponse(0, "Success");
    }

}
