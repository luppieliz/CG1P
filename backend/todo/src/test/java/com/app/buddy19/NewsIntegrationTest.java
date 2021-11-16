package com.app.buddy19;

import com.app.buddy19.newsfunnel.*;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NewsIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsAPIServiceImpl newsAPIService;

    @Test
    public void getNews_Success() throws URISyntaxException {
        String countryCode = "sg";
        String query = "covid";
        URI uri = new URI(baseUrl + port + "/newsapi" + "/" + countryCode + "/" + query);

        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    public void getNews_InvalidCountryCode_Failure() throws Exception {
        String countryCode = "lj";
        String query = "covid";
        URI uri = new URI(baseUrl + port + "/newsapi" + "/" + countryCode + "/" + query);

        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(0, result.getBody().length);
    }

    @Test
    public void getNewsFromDB_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/newsapi/newsdb/all");
        // yyyy-MM-dd HH:mm:ss
        newsRepository.save(new News("cna.com.sg","Covid is all time high!", "2021-10-10 11:11:11"));

        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
        News[] news = result.getBody();

        assertEquals(200, result.getStatusCode().value());
    }

}
