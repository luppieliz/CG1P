package com.app.todo.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPost {
    private String name;
    private String message;
    private String emailTo;
    private String subject;

    public EmailPost () {}

    public EmailPost(@JsonProperty("name") String name,
                     @JsonProperty("message") String message,
                     @JsonProperty("email_to") String emailTo,
                     @JsonProperty("subject") String subject) {
        this.name = name;
        this.message = message;
        this.emailTo = emailTo;
        this.subject = subject;
    }
}
