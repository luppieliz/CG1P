package com.app.todo.industry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IndustryAlreadyRegistered extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IndustryAlreadyRegistered(Long industryId) {
        super("Industry with ID: " + industryId + " is already registered!");
    }

    public IndustryAlreadyRegistered(String industryName) {
        super("Industry " + industryName + " is already registered!");
    }
}
