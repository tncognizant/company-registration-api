package com.cognizant.estockmarket.companyregistrationapi.entities.company_view;

import com.cognizant.estockmarket.companyregistrationapi.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyView {

    private String companyCode;
    private String companyName;
    private String companyCeo;
    private BigDecimal companyTurnover;
    private String companyWebsite;
    private String companyStockExchange;
    private Stock latestStock;


}
