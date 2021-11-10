package com.app.todo.business;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessAlreadyRegisteredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessAlreadyRegisteredException(UUID businessId) {
        super("Business with ID: " + businessId + " is already registered!");
    }

    public BusinessAlreadyRegisteredException(String UEN) {
        super("Business " + UEN + " is already registered!");
    }
}