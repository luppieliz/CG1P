package com.app.todo.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String from;
    private String mailTo;
    private String[] mailToMultiple;
    private String subject;
    private String message;
    private Map<String, Object> props;

    public Email(String from, String mailTo, String subject, String message, Map<String, Object> props) {
        this.from = from;
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.props = props;
    }
}
