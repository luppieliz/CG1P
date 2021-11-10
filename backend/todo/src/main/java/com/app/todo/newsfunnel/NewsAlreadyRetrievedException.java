package com.app.todo.newsfunnel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewsAlreadyRetrievedException extends RuntimeException{

    public NewsAlreadyRetrievedException(String title) {
        super("News title: " + title + ", has already retrieved!");
    }
}
