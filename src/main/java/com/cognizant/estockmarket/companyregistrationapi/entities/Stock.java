package com.cognizant.estockmarket.companyregistrationapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private String companyId;
    private BigDecimal price;
    private LocalDateTime dateTimeCreated;
    private LocalDate dateCreated;
}
