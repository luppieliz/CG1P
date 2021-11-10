package com.app.todo;

import com.app.todo.email.Email;
import com.app.todo.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    void sendSimpleEmail_NoToAddress_ReturnIllegalArgumentException() {
        String to = null;
        String subject = "This is an email subject";
        String body = "This is an email body!";

        Throwable exception = null;

        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Missing to address or missing email subject" ,exception.getMessage());

    }

    @Test
    void sendSimpleEmail_ZeroLengthToAddress_ReturnIllegalArgumentException() {
        String to = "";
        String subject = "This is an email subject";
        String body = "This is an email body!";

        Throwable exception = null;
        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Missing to address or missing email subject" ,exception.getMessage());

    }

    @Test
    void sendSimpleEmail_InvalidToAddress_ReturnIllegalArgumentException() {
        String to = "test.gmail.com";
        String subject = "This is an email subject";
        String body = "This is an email body!";

        Throwable exception = null;
        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Missing to address or missing email subject" ,exception.getMessage());

    }

    @Test
    void sendSimpleEmail_NoSubject_ReturnIllegalArgumentException() {
        String to = "test@gmail.com";
        String subject = null;
        String body = "This is an email body!";

        Throwable exception = null;
        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Missing to address or missing email subject" ,exception.getMessage());

    }

    @Test
    void sendSimpleEmail_ZeroLengthSubject_ReturnIllegalArgumentException() {
        String to = "test@gmail.com";
        String subject = "";
        String body = "This is an email body!";

        Throwable exception = null;
        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Missing to address or missing email subject" ,exception.getMessage());

    }

    @Test
    void sendSimpleEmail_ValidToAddressValidSubject_ReturnNoException() {
        String to = "test@gmail.com";
        String subject = "This is an email subject";
        String body = "This is an email body!";

        Throwable exception = null;
        try {
            emailService.sendSimpleEmail(to, subject, body);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertNull(exception);
    }

    @Test
    void sendEmailWithHTMLAttachment_WrongFromAdd_ReturnIllegalArgumentException() throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        Email email = new Email("test@gmail.com", "test@gmail.com", "This is a test email subject", "This is a test email message", map);

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        Throwable exception = null;
        try {
            emailService.sendEmailWithHTMLAttachment(email, "email-template");
        } catch (Throwable ex) {
            exception = ex;
        }
        assertEquals(IllegalArgumentException.class ,exception.getClass());
        assertEquals("Wrong host address!" ,exception.getMessage());
    }
}
