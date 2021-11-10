package com.app.todo.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Email {
    private String from;
    private String mailTo;
    private String subject;
    private String message;
    private Map<String, Object> props;

    public Email() {}
}
