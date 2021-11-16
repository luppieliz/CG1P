package com.app.buddy19;

import com.app.buddy19.email.EmailController;
import com.app.buddy19.email.EmailPost;
import com.app.buddy19.email.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {
                "spring.mail.host=smtp.gmail.com",
                "spring.mail.port=587",
                "spring.mail.username=cs203g3t1@gmail.com",
                "spring.mail.password=kdfdpivevdllmbbi",
                "spring.mail.properties.mail.smtp.auth=true",
                "spring.mail.properties.mail.smtp.starttls.enable=true"
        }
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmailIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmailController emailController;

    @Autowired
    private EmailServiceImpl emailService;


    @Test
    public void sendEmailWithHTMLTemplate_Success() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", "test@gmail.com", "This is an email subject");

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void sendEmailWithHTMLTemplateZeroLengthSubject_Unsuccessful() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", "test@gmail", "");

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    public void sendEmailWithHTMLTemplateNoSubject_Unsuccessful() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", "test@gmail", null);

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    public void sendEmailWithHTMLTemplateZeroLengthAddress_Unsuccessful() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", "", "This is an email subject");

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    public void sendEmailWithHTMLTemplateNoAddress_Unsuccessful() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", null, "This is an email subject");

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    public void sendEmailWithHTMLTemplateInvalidAddress_Unsuccessful() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "to");
        map.put("message", "hi");
        EmailPost emailPost = new EmailPost("to", "hi", "test.gmail.com", "This is an email subject");

        URI uri = new URI(baseUrl + port + "/api/v1/emailSender");

        ResponseEntity<Void> result = restTemplate.postForEntity(uri, emailPost, Void.class);

        assertEquals(500, result.getStatusCode().value());
    }
}
