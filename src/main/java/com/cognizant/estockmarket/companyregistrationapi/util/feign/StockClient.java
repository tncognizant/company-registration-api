package com.cognizant.estockmarket.companyregistrationapi.util.feign;

import com.cognizant.estockmarket.companyregistrationapi.entities.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//USE IN PROD
//@FeignClient(name = "${clients.get.name}")
//USE THIS IN LOCAL DEVELOPMENT:
@FeignClient(value = "stock-prices-api", url="http://localhost:8084")
public interface StockClient {

    @GetMapping("/api/v1.0/market/stock/")
    List<Stock> getAll();

    @GetMapping("/api/v1.0/market/stock/latest/{companyId}")
    Stock findLatestStock(@PathVariable("companyId") String companyId);
}
