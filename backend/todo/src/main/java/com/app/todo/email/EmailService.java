package com.app.todo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    private SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Send a simple templated email.
     * @param to
     * @param subject
     * @param bodyText
     */
    public void sendSimpleEmail(String to, String subject, String bodyText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(bodyText);
        emailSender.send(message);
    }

    /**
     * Send a templated email with an attachment.
     * @param email
     * @param template
     * @throws MessagingException
     */
    public void sendEmailWithHTMLAttachment(Email email, String template) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getProps());

        String html = templateEngine.process(template, context);
        helper.setTo(email.getMailTo());
        helper.setText(html, true);
        helper.setSubject(email.getSubject());
        helper.setFrom(email.getFrom());
        emailSender.send(message);
    }
}
