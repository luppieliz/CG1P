package com.app.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.UUID;

import com.app.todo.business.Business;
import com.app.todo.industry.Industry;
import com.app.todo.user.User;
import com.app.todo.business.BusinessRepository;
import com.app.todo.industry.IndustryRepository;
import com.app.todo.user.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BusinessIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @AfterEach
    void tearDown() {
        businessRepository.deleteAll();
        industryRepository.deleteAll();
    }

    @Test
    public void getBusinesses_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/business");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        // Need to use array with a ReponseEntity here
        ResponseEntity<Business[]> result = restTemplate.getForEntity(uri, Business[].class);
        Business[] businesses = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, businesses.length);
        assertEquals(business.getUEN(), result.getBody()[0].getUEN());
    }

    @Test
    public void getBusinessWithId_ValidBusinessId_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        UUID id = business.getId();
        URI uri = new URI(baseUrl + port + "/business/" + id);

        ResponseEntity<Business> result = restTemplate.getForEntity(uri, Business.class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(business.getId(), result.getBody().getId());
        assertEquals(business.getUEN(), result.getBody().getUEN());
    }

    @Test
    public void getBusinessWithId_InvalidBusinessId_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository
                .save(new Business(UUID.randomUUID(), "asd789fhgj", "Singapore Museum", industry));
        UUID id = UUID.randomUUID();
        URI uri = new URI(baseUrl + port + "/business/" + id);

        ResponseEntity<Business> result = restTemplate.getForEntity(uri, Business.class);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void getBusinessWithUEN_ValidBusinessUEN_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String UEN = business.getUEN();
        URI uri = new URI(baseUrl + port + "/business/uen/" + UEN);

        ResponseEntity<Business> result = restTemplate.getForEntity(uri, Business.class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(business.getId(), result.getBody().getId());
        assertEquals(business.getUEN(), result.getBody().getUEN());
    }

    @Test
    public void getBusinessWithUEN_InvalidBusinessUEN_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);

        URI uri = new URI(baseUrl + port + "/business/uen/" + business.getUEN());
        ResponseEntity<Business> result = restTemplate.getForEntity(uri, Business.class);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void addBusiness_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/business");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "goodpassword";
        User user = userRepository
                .save(new User("admin@abc.com", "admin", encoder.encode(userRawPassword), "ROLE_ADMIN", business));

        Business testBusiness = new Business("asd788fhgj", "SMU Museum", industry);

        ResponseEntity<Business> result = restTemplate.withBasicAuth(user.getEmail(), userRawPassword)
                .postForEntity(uri, testBusiness, Business.class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(testBusiness.getUEN(), result.getBody().getUEN());
    }

    @Test
    public void addBusiness_BusinessAlreadyRegistered_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/business");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "goodpassword";
        User user = userRepository
                .save(new User("admin@abc.com", "admin", encoder.encode(userRawPassword), "ROLE_ADMIN", business));

        ResponseEntity<Business> result = restTemplate.postForEntity(uri, business, Business.class);

        assertEquals(404, result.getStatusCode().value());
    }
}
