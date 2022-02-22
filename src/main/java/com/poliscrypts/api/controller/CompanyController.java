package com.poliscrypts.api.controller;


import com.poliscrypts.api.entity.Company;
import com.poliscrypts.api.model.ExtendedGenericPojoResponse;
import com.poliscrypts.api.model.GenericPojoResponse;
import com.poliscrypts.api.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(tags = "Company", description = "CRUD operations related to company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @ApiOperation(value = "Get list of companies", response = ExtendedGenericPojoResponse.class)
    @GetMapping("/companies")
    public ExtendedGenericPojoResponse getCompanies() {
        return companyService.getCompanies();
    }

    @ApiOperation(value = "Create company", response = ExtendedGenericPojoResponse.class)
    @PostMapping("/company")
    public ExtendedGenericPojoResponse addCompany(@RequestBody Company company) {
        return companyService.addNewCompany(company);
    }

    @ApiOperation(value = "Edit company", response = ExtendedGenericPojoResponse.class)
    @PatchMapping("/company")
    public ExtendedGenericPojoResponse updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @ApiOperation(value = "Delete company", response = GenericPojoResponse.class)
    @DeleteMapping("/company")
    public GenericPojoResponse deleteCompany(@RequestParam("uuid") UUID uuid) {
        return companyService.deleteCompany(uuid);
    }
}
