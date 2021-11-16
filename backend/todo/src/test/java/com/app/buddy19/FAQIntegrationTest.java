package com.app.buddy19;

import com.app.buddy19.faq.FAQ;
import com.app.buddy19.faq.FAQRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FAQIntegrationTest {
    private final String baseUrl = "http://localhost:";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FAQRepository faqRepository;

    @Test
    void retrieveImage_Success() throws URISyntaxException {
        String URL = "https://www.enterprisesg.gov.sg/covid-19/safe-distance";
        URI uri = new URI(baseUrl + port + "/faq/scrape?URL=" + URL);
        ResponseEntity<FAQ[]> result = restTemplate.getForEntity(uri, FAQ[].class);

        assertEquals(200, result.getStatusCode()
                                .value());
        assertNotNull(result.getBody());
    }

    @Test
    void retrieveFAQFromDB_Success() throws URISyntaxException {
        FAQ newFAQ1 = faqRepository.save(new FAQ("https://www.enterprisesg.gov.sg/-/media/esg/images/covid-19/safe-distance/for-businesses-safe-distancing_delivery_english.png?la=en&hash=6F7BF007B67E68856A3840576B3EADB60695810F"));
        FAQ newFAQ2 = faqRepository.save(new FAQ("https://www.enterprisesg.gov.sg/-/media/esg/images/covid-19/safe-distance/for-businesses-safe-distancing_fb_english.png?la=en"));
        List<FAQ> faqList = new ArrayList<>();
        faqList.add(newFAQ1);
        faqList.add(newFAQ2);

        URI uri = new URI(baseUrl + port + "/faq/retrieveFromDB");
        ResponseEntity<FAQ[]> result = restTemplate.getForEntity(uri, FAQ[].class);
        FAQ[] faqArr = result.getBody();

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(faqList.size(), faqArr.length);
        assertEquals(faqList.get(0)
                            .getURL(), faqArr[0].getURL());
    }
}
