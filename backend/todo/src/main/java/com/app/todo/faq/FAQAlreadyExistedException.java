package com.app.todo.faq;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FAQAlreadyExistedException extends RuntimeException{
    public FAQAlreadyExistedException(String URL) {
        super("FAQ with URL: " + URL + " is already registered!");
    }

}
