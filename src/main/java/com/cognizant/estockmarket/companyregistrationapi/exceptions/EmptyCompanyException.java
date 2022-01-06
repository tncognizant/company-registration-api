package com.cognizant.estockmarket.companyregistrationapi.exceptions;

public class EmptyCompanyException extends RuntimeException {
    public EmptyCompanyException(){super();}
    public EmptyCompanyException(String msg){super(msg);}
}
