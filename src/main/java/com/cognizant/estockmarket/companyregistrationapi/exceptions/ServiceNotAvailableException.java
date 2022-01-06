package com.cognizant.estockmarket.companyregistrationapi.exceptions;

public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException(){
        super();
    }
    public ServiceNotAvailableException(String msg){
        super(msg);
    }
}
