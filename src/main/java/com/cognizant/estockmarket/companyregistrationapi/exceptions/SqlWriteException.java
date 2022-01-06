package com.cognizant.estockmarket.companyregistrationapi.exceptions;

public class SqlWriteException extends RuntimeException {
    public SqlWriteException() {
        super();
    }

    public SqlWriteException(String message) {
        super(message);
    }
}
