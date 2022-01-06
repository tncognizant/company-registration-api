package com.cognizant.estockmarket.companyregistrationapi.controller;

import com.cognizant.estockmarket.companyregistrationapi.entities.Company;
import com.cognizant.estockmarket.companyregistrationapi.entities.Stock;
import com.cognizant.estockmarket.companyregistrationapi.entities.company_view.CompanyView;
import com.cognizant.estockmarket.companyregistrationapi.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService mockService;

    @Test
    public void whenGetTestService_thenReturnTestString() throws Exception {
        mvc.perform(get("/api/v1.0/market/company"))
                .andExpect(status().isOk())
                .andExpect(content().string("Yay you are able to hit this API Get Route!"));
    }

    @Test
    public void givenCompany_whenAddCompany_thenStatus200_andReturnCompany() throws Exception {

        Company reqBodyCompany = new Company("company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");
        Company expectedCompany = new Company("123ABC", "company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC");
        given(mockService.create(reqBodyCompany)).willReturn(expectedCompany);

        MvcResult mvcResult = mvc.perform(post("/api/v1.0/market/company/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqBodyCompany)))
                .andExpect(status().isOk())
                .andReturn();

        String actualCompResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualCompResponse).isEqualTo(objectMapper.writeValueAsString(expectedCompany));

    }

    @Test
    public void givenExistingCompanyCode_whenGetStocks_thenStatus200_andReturnCompanyView()throws Exception{
        String compCode = "123ABC";
        Stock sampleStock= new Stock("123ABC", new BigDecimal(4.00), LocalDateTime.now(), LocalDate.now());
        CompanyView expectedCompanyView = new CompanyView("123ABC", "company ABC", "CEO", new BigDecimal(15.00), "company.com", "ABC", sampleStock);
        given(mockService.getCompViewDetail(compCode)).willReturn(expectedCompanyView);

        MvcResult mvcResult = mvc.perform(get("/api/v1.0/market/company/info/123ABC"))
                .andExpect(status().isOk())
                .andReturn();
        String actualCompViewResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualCompViewResponse).isEqualTo(objectMapper.writeValueAsString(expectedCompanyView));

    }

    @Test
    public void givenExistingCompanyCode_whenUpdateCompany_thenStatus200_andReturnUpdatedCompany() throws  Exception{
        String compCode = "123ABC";
        Company reqBodyCompany = new Company("company DEF", "CEO", new BigDecimal(20.00), "company-def.com", "DEF");
        Company expectedCompany = new Company("123ABC", "company DEF", "CEO", new BigDecimal(20.00), "company-def.com", "DEF");
        given(mockService.update(compCode,reqBodyCompany)).willReturn(expectedCompany);

        MvcResult mvcResult = mvc.perform(put("/api/v1.0/market/company/update/123ABC")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqBodyCompany)))
                .andExpect(status().isOk())
                .andReturn();

        String actualCompResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualCompResponse).isEqualTo(objectMapper.writeValueAsString(expectedCompany));
    }

}