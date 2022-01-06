package com.cognizant.estockmarket.companyregistrationapi.repos;

import com.cognizant.estockmarket.companyregistrationapi.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.companyCode = ?1")
    Company findCompanyByCompanyCode(String compCode);
}
