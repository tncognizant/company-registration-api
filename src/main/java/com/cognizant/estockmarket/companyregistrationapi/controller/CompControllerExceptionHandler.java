package com.cognizant.estockmarket.companyregistrationapi.controller;

import com.cognizant.estockmarket.companyregistrationapi.exceptions.EmptyCompanyException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.InvalidTurnoverException;
import com.cognizant.estockmarket.companyregistrationapi.exceptions.ServiceNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CompControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleBadRequest(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((er) ->{
            String fieldName = ((FieldError) er).getField();
            String errorMessage = er.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTurnoverException.class)
    public Map<String, String> handleTurnoverException(InvalidTurnoverException ex){
        Map<String, String> turnoverError = new HashMap<>();
        turnoverError.put("turnover error", ex.getMessage());
        return turnoverError;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceNotAvailableException.class)
    public Map<String, String> handleServiceNotAvailableException(ServiceNotAvailableException ex){
        Map<String, String> serviceError = new HashMap<>();
        serviceError.put("Internal Server Error", ex.getMessage());
        return serviceError;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyCompanyException.class)
    public Map<String, String> handleEmptyCompanyException(EmptyCompanyException ex){
        Map<String, String> serviceError = new HashMap<>();
        serviceError.put("Error message", ex.getMessage());
        return serviceError;
    }

}
