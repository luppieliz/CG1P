package com.app.todo.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessAlreadyRegisteredException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public BusinessAlreadyRegisteredException(String businessName) {
        super("Business " + businessName + " is already registered!");
    }

    public BusinessAlreadyRegisteredException(Long businessId) {
        super("Business with business ID: " + businessId + " is already registered!");
    }
}