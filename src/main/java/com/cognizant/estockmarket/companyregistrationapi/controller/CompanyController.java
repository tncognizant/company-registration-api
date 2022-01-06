package com.cognizant.estockmarket.companyregistrationapi.controller;

import com.cognizant.estockmarket.companyregistrationapi.entities.Company;
import com.cognizant.estockmarket.companyregistrationapi.entities.company_view.CompanyView;
import com.cognizant.estockmarket.companyregistrationapi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

    private CompanyService service;

    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public String testService() {
        return "Yay you are able to hit this API Get Route!";
    }

    @PostMapping("/register")
    public void addCompany(@Valid @RequestBody Company company) {
         service.create(company);
    }

    @PutMapping("/update/{companyCode}")
    public void updateCompany(@Valid @RequestBody Company company, @PathVariable("companyCode") String companyCode){
         service.update(companyCode, company);
    }

    @GetMapping("/info/{companyCode}")
    public CompanyView getCompanyDetail(@PathVariable("companyCode") String companyCode) {
        return service.getCompViewDetail(companyCode);
    }



}
