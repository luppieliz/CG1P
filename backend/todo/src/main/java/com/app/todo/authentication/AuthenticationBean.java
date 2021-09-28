package com.app.todo.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationBean {
    private String message;

    public AuthenticationBean(String message) {
        this.message = message;
    }
}
