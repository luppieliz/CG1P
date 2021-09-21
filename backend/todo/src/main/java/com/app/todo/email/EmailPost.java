package com.app.todo.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPost {
    private String name;
    private String message;
    private String emailTo;
    private String subject;
}
