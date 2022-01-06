package com.cognizant.estockmarket.companyregistrationapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "CHAR(50)")
    private String companyCode;
    @NotBlank(message = "Company name is Mandatory")
    private String companyName;
    @NotBlank(message = "CEO name is Mandatory")
    private String companyCeo;
    @Min(value = 10, message = "Turnover must contain value greater than 10Cr")
    @NotNull(message = "Turnover is Mandatory")
    private BigDecimal companyTurnover;
    @NotBlank(message = "Company website is Mandatory")
    private String companyWebsite;
    @NotBlank(message = "Company stock exchange name is Mandatory")
    private String companyStockExchange;

    public Company(@NotBlank(message = "Company name is Mandatory") String companyName, @NotBlank(message = "CEO name is Mandatory") String companyCeo, @Min(value = 10, message = "Turnover must contain value greater than 10Cr") @NotNull(message = "Turnover is Mandatory") BigDecimal companyTurnover, @NotBlank(message = "Company website is Mandatory") String companyWebsite, @NotBlank(message = "Company stock exchange name is Mandatory") String companyStockExchange) {
        this.companyName = companyName;
        this.companyCeo = companyCeo;
        this.companyTurnover = companyTurnover;
        this.companyWebsite = companyWebsite;
        this.companyStockExchange = companyStockExchange;
    }
}
