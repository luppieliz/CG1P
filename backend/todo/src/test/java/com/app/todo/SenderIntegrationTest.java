package com.app.todo;

import com.app.todo.phonetext.MmsRequest;
import com.app.todo.phonetext.SmsRequest;
import com.app.todo.phonetext.TwilioSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SenderIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TwilioSenderService senderService;

    @Test
    void sendSMS_Sucess() throws URISyntaxException {
        SmsRequest testSMSRequest = new SmsRequest("+65 8742 9618", "testMsg");
        URI uri = new URI(baseUrl + port + "/api/v1/sender/sms");
        ResponseEntity<Void> result = restTemplate.postForEntity(uri, testSMSRequest ,Void.class);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void sendSMS_Unsucessful() throws URISyntaxException {
        SmsRequest testSMSRequest = new SmsRequest("8742 9618", "testMsg");
        URI uri = new URI(baseUrl + port + "/api/v1/sender/sms");
        ResponseEntity<Void> result = restTemplate.postForEntity(uri, testSMSRequest ,Void.class);
        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    void sendMMS_Sucess() throws URISyntaxException {
        MmsRequest testMMSRequest = new MmsRequest("+65 8742 9618", "testMsg", "https://picsum.photos/200");
        URI uri = new URI(baseUrl + port + "/api/v1/sender/mms");
        ResponseEntity<Void> result = restTemplate.postForEntity(uri, testMMSRequest ,Void.class);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void sendMMS_Unsucessful() throws URISyntaxException {
        MmsRequest testMMSRequest = new MmsRequest("+65 8742 9618", "testMsg", null);
        URI uri = new URI(baseUrl + port + "/api/v1/sender/mms");
        ResponseEntity<Void> result = restTemplate.postForEntity(uri, testMMSRequest ,Void.class);
        assertEquals(400, result.getStatusCode().value());
    }
}
