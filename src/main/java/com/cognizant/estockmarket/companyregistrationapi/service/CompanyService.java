package com.cognizant.estockmarket.companyregistrationapi.service;

import com.cognizant.estockmarket.companyregistrationapi.entities.Company;
import com.cognizant.estockmarket.companyregistrationapi.entities.Stock;
import com.cognizant.estockmarket.companyregistrationapi.entities.company_view.CompanyView;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.EmptyCompanyException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.ServiceNotAvailableException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.SqlWriteException;
import com.cognizant.estockmarket.companyregistrationapi.repos.CompanyRepository;
import com.cognizant.estockmarket.companyregistrationapi.util.feign.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private CompanyRepository repo;
    private StockClient stockClient;

    @Autowired
    public CompanyService(CompanyRepository repository, StockClient client) {
        this.repo = repository;
        this.stockClient = client;
    }


    public Company create(Company comp) {
        if (comp == null) {
            throw new IllegalArgumentException("Input company data is empty");
        }

        try {
             return repo.save(comp);
        } catch (Exception ex) {
            throw new SqlWriteException("Error inserting company in database");
        }
    }

    public Company update(String compCode, Company reqComp) {
        Company company = repo.findCompanyByCompanyCode(compCode);

        if (company == null)
            throw new EmptyCompanyException("Company code : " + compCode + " does not match any company");

        reqComp.setCompanyCode(compCode);

        try {
            return repo.save(reqComp);
        } catch (Exception ex) {
            throw new SqlWriteException("Error updating company in database");
        }
    }


    public CompanyView getCompViewDetail(String compCode) {
        Company company = repo.findCompanyByCompanyCode(compCode);
        if (company == null) {
            throw new EmptyCompanyException("Company code : " + compCode + " does not match any company");
        }
        CompanyView companyView = mapCompanyView(company);
        return companyView;
    }

    private CompanyView mapCompanyView(Company comp) {
        CompanyView companyView = new CompanyView();
        companyView.setCompanyCode(comp.getCompanyCode());
        companyView.setCompanyCeo(comp.getCompanyCeo());
        companyView.setCompanyName(comp.getCompanyName());
        companyView.setCompanyTurnover(comp.getCompanyTurnover());
        companyView.setCompanyStockExchange(comp.getCompanyStockExchange());
        companyView.setCompanyWebsite(comp.getCompanyWebsite());
        companyView.setLatestStock(findLatestStock(comp.getCompanyCode()));
        return companyView;
    }

    private Stock findLatestStock(String companyCode) {
        try {
            return stockClient.findLatestStock(companyCode);
        } catch (Exception ex) {
            throw new ServiceNotAvailableException("Stock service is not available");
        }
    }

}
