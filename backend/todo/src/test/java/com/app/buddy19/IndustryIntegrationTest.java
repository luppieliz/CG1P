package com.app.buddy19;

import com.app.buddy19.industry.Industry;
import com.app.buddy19.industry.IndustryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URLEncoder;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndustryIntegrationTest {

    private final String baseUrl = "http://localhost:";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IndustryRepository industryRepository;

    @Test
    public void getIndustries_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/industry");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        // Need to use array with a ReponseEntity here
        ResponseEntity<Industry[]> result = restTemplate.getForEntity(uri, Industry[].class);
        Industry[] industries = result.getBody();

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(1, industries.length);
    }

    @Test
    public void getIndustryWithId_ValidIndustryId_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        UUID id = industry.getId();

        URI uri = new URI(baseUrl + port + "/industry/" + id);

        ResponseEntity<Industry> result = restTemplate.getForEntity(uri, Industry.class);

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(industry.getId(), result.getBody()
                                             .getId());
    }

    @Test
    public void getIndustryWithId_InvalidIndustryId_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry(UUID.randomUUID(), "Arts and Culture"));
        UUID id = UUID.randomUUID();
        URI uri = new URI(baseUrl + port + "/industry/" + id);

        ResponseEntity<Industry> result = restTemplate.getForEntity(uri, Industry.class);

        assertEquals(404, result.getStatusCode()
                                .value());
    }

    @Test
    public void getIndustryWithName_ValidIndustryName_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry(UUID.randomUUID(), "Arts"));

        String name = URLEncoder.encode(industry.getName(), "UTF-8")
                                .replace("+", "%20");
        URI uri = new URI(baseUrl + port + "/industry/name/" + name);

        ResponseEntity<Industry> result = restTemplate.getForEntity(uri, Industry.class);

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(industry.getId(), result.getBody()
                                             .getId());
        assertEquals(industry.getName(), result.getBody()
                                               .getName());
    }

    @Test
    public void getIndustryWithName_InvalidIndustryName_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        String name = URLEncoder.encode("Culture and Arts", "UTF-8")
                                .replace("+", "%20");
        URI uri = new URI(baseUrl + port + "/industry/name/" + name);

        ResponseEntity<Industry> result = restTemplate.getForEntity(uri, Industry.class);

        assertEquals(404, result.getStatusCode()
                                .value());
    }

    @Test
    public void addIndustry_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/industry");
        Industry industry = new Industry("Arts and Culture", null);
        ResponseEntity<Industry> result = restTemplate.postForEntity(uri, industry, Industry.class);

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(industry.getName(), result.getBody()
                                               .getName());
    }

    @Test
    public void addIndustry_IndustryAlreadyExists_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/industry");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        ResponseEntity<Industry> result = restTemplate.postForEntity(uri, industry, Industry.class);
        assertEquals(404, result.getStatusCode()
                                .value());
    }
}
