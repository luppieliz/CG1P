package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.faq.FAQ;
import com.app.todo.faq.FAQRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FAQIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    /**
     * Use TestRestTemplate for testing a real instance of your application as an external actor.
     * TestRestTemplate is just a convenient subclass of RestTemplate that is suitable for integration tests.
     * It is fault tolerant, and optionally can carry Basic authentication headers.
     */
    private TestRestTemplate restTemplate;

    @Autowired
    private FAQRepository faqRepository;

    @Test
    void retrieveImage_Sucess() throws URISyntaxException {
        String URL = "https://www.enterprisesg.gov.sg/covid-19/safe-distance";
        URI uri = new URI(baseUrl + port + "/faq/scrape?URL=" + URL);
        ResponseEntity<FAQ[]> result = restTemplate.getForEntity(uri, FAQ[].class);

        assertEquals(200, result.getStatusCode().value());

    }
}
