package com.poliscrypts.api.controller;


import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.service.CompanyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/companies")
    public ExtendedGenericPojoResponse getCompanies() {
        return companyService.getCompanies();
    }

    @PostMapping("/company")
    public ExtendedGenericPojoResponse addCompany(@RequestBody Company company) {
        return companyService.addNewCompany(company);
    }

    @PatchMapping("/company")
    public ExtendedGenericPojoResponse updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/company")
    public GenericPojoResponse deleteCompany(@RequestParam("id") Integer id) {
        return companyService.deleteCompany(id);
    }
}
