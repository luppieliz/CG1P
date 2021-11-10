package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.business.BusinessRepository;
import com.app.todo.industry.Industry;
import com.app.todo.industry.IndustryRepository;
import com.app.todo.user.User;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private BusinessRepository businessRepository;


    @Test
    void addUser_Sucess() throws URISyntaxException {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        User newUser = new User("admin@gmail", "admin", "goodpassword",  "ROLE_ADMIN" ,business);

        ResponseEntity<User> result = restTemplate.postForEntity(uri, newUser, User.class);

        assertEquals(200, result.getStatusCodeValue());

    }


}
