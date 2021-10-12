package com.app.todo.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public BusinessNotFoundException(Long businessId) {
        super("Business with ID:" + businessId + " is not registered!");
    }

    public BusinessNotFoundException(String businessName) {
        super("Business " + businessName + " is not registered!");
    }
}
