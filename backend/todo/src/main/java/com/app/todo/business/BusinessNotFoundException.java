package com.app.todo.business;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessNotFoundException(UUID businessId) {
        super("Business with ID: " + businessId + " is not registered!");
    }

    public BusinessNotFoundException(String UEN) {
        super("Business " + UEN + " is not registered!");
    }
}