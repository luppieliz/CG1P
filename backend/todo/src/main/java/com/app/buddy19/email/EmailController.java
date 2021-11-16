package com.app.buddy19.email;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/emailSender")
public class EmailController {

    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String hostEmail;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully send an email"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})


    @ApiOperation(value = "Send email")
    @PostMapping(produces = "application/json")
    public void sendEmailWithHTMLTemplate(@RequestBody EmailPost emailPost) throws MessagingException {
        Email email = new Email();
        email.setFrom(hostEmail);
        email.setMailTo(emailPost.getEmailTo());
        email.setSubject(emailPost.getSubject());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", emailPost.getName());
        model.put("message", emailPost.getMessage());
        email.setProps(model);
        emailService.sendEmailWithHTMLAttachment(email, "email-template");
    }
}
