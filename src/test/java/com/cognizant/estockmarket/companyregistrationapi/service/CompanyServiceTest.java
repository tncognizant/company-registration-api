package com.cognizant.estockmarket.companyregistrationapi.service;

import com.cognizant.estockmarket.companyregistrationapi.entities.Company;
import com.cognizant.estockmarket.companyregistrationapi.entities.Stock;
import com.cognizant.estockmarket.companyregistrationapi.entities.company_view.CompanyView;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.EmptyCompanyException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.ServiceNotAvailableException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.SqlWriteException;
import com.cognizant.estockmarket.companyregistrationapi.repos.CompanyRepository;
import com.cognizant.estockmarket.companyregistrationapi.util.feign.StockClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class CompanyServiceTest {

    private CompanyService service;

    @Mock
    private CompanyRepository repo;
    @Mock
    private StockClient client;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.service = new CompanyService(repo, client);
    }

    @Test
    public void create_withEmptyCompany_throwsIllegalArgumentException() {
        Company emptyCompany = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.create(emptyCompany);
        });

        String expectedMessage = "Input company data is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void create_withCompany_returnCompany() {
        Company requestCompany = new Company("company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");

        when(repo.save(any(Company.class))).thenReturn(new Company("123ABC", "company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC"));

        Company savedCompany = service.create(requestCompany);

        assertNotNull(savedCompany);
        assertEquals("123ABC", savedCompany.getCompanyCode());
        assertEquals(requestCompany.getCompanyName(), savedCompany.getCompanyName());
    }

    @Test
    public void create_withCompany_throwsException() {
        Company requestCompany = new Company("company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");

        when(repo.save(any(Company.class))).thenThrow(SqlWriteException.class);

        Exception exception = assertThrows(SqlWriteException.class, () -> {
            service.create(requestCompany);
        });
    }

    @Test
    public void getCompView_withNullCompany_throwsException() {
        String compCode = "ABC";
        when(repo.findCompanyByCompanyCode(compCode)).thenReturn(null);
        Exception exception = assertThrows(EmptyCompanyException.class, () -> {
            service.getCompViewDetail(compCode);
        });
        String expectedMessage = "Company code : ABC does not match any company";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void getCompView_withCompanyAndLatestStockAvailable_returnCompanyView() {
        String compCode = "123ABC";
        LocalDateTime sampleDateTime = LocalDateTime.of(2021, Month.AUGUST, 30, 12, 30, 30);
        LocalDate sampleDate = LocalDate.of(2021, Month.AUGUST, 30);

        when(repo.findCompanyByCompanyCode(compCode)).thenReturn(new Company("123ABC", "company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC"));
        when(client.findLatestStock(compCode)).thenReturn(new Stock(compCode, new BigDecimal(2.00), sampleDateTime, sampleDate));

        CompanyView resultView = service.getCompViewDetail(compCode);

        assertNotNull(resultView);
        assertNotNull(resultView.getLatestStock());
        assertEquals(compCode, resultView.getCompanyCode());
        assertEquals("company ABC", resultView.getCompanyName());
        assertEquals(new BigDecimal(2.00), resultView.getLatestStock().getPrice());

    }

    @Test
    public void findLatestStock_whileStockServiceNotAvailable_throwException() {
        String compCode = "123ABC";
        when(repo.findCompanyByCompanyCode(compCode)).thenReturn(new Company("123ABC", "company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC"));
        when(client.findLatestStock(compCode)).thenThrow(ServiceNotAvailableException.class);

        Exception exception = assertThrows(ServiceNotAvailableException.class, () -> {
            service.getCompViewDetail(compCode);
        });

        String expectedMessage = "Stock service is not available";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));

    }

    @Test
    public void givenUnavailableCompCode_update_throwsException(){
        String compCode = "123ABC";
        Company company = new Company("company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");
        when(repo.findCompanyByCompanyCode(compCode)).thenReturn(null);
        Exception exception = assertThrows(EmptyCompanyException.class, () -> {
            service.update(compCode, company);
        });
        String expectedMessage = "Company code : 123ABC does not match any company";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void givenAvailableCompCode_update_returnsUpdatedCompany(){
        String compCode = "123ABC";
        Company existingCompany = new Company("123ABC","company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");
        Company newCompany = new Company("company DEF", "CEO", new BigDecimal(30.00), "def.com", "DEF");
        Company returnNewCompany = new Company("123ABC","company DEF", "CEO", new BigDecimal(30.00), "def.com", "DEF");
        when(repo.findCompanyByCompanyCode(compCode)).thenReturn(existingCompany);
        when(repo.save(newCompany)).thenReturn(returnNewCompany);

        Company result = service.update(compCode,newCompany);

        assertNotNull(result);
        assertEquals(compCode, result.getCompanyCode());
        assertEquals("company DEF", result.getCompanyName());
        assertEquals(new BigDecimal(30.00), result.getCompanyTurnover());


    }


}