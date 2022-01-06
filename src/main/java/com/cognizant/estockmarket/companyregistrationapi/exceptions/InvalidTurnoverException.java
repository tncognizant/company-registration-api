package com.cognizant.estockmarket.companyregistrationapi.exceptions;

public class InvalidTurnoverException extends RuntimeException {
    public InvalidTurnoverException(){super();}
    public InvalidTurnoverException(String errorMessage) {
        super(errorMessage);
    }
}
