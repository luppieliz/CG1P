package com.app.buddy19.industry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IndustryAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IndustryAlreadyExistsException(UUID industryId) {
        super("Industry with ID: " + industryId + " already exists!");
    }

    public IndustryAlreadyExistsException(String industryName) {
        super("Industry " + industryName + " already exists!");
    }
}