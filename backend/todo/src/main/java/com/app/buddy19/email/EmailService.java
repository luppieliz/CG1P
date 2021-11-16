package com.app.buddy19.email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String bodyText);

    void sendSimpleEmail(String[] to, String subject, String bodyText);

    void sendEmailWithHTMLAttachment(Email email, String template) throws MessagingException;

    boolean checkValidToAdd(String to);

    boolean checkValidToAdd(String[] to);

    boolean checkValidSubject(String subject);

    boolean checkValidFromAdd(String from);
}
