package com.app.todo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="api/v1/emailSender")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public void sendEmailWithHTMLTemplate(@RequestBody EmailPost emailPost) throws MessagingException {
        Email email = new Email();
        email.setFrom("c1gp.email.service@gmail.com");
        email.setMailTo(emailPost.getEmailTo());
        email.setSubject(emailPost.getSubject());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", emailPost.getName());
        model.put("message", emailPost.getMessage());
        email.setProps(model);
        emailService.sendEmailWithHTMLAttachment(email, "email-template");
    }
}
