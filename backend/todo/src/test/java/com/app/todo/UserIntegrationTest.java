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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.UUID;

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

    @Test
    public void getAllUsers_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);
        ResponseEntity<User[]> getResult = restTemplate.withBasicAuth(user1.getEmail(), userRawPassword).getForEntity(uri, User[].class);
        User[] users = getResult.getBody();

        assertEquals(200, postResult.getStatusCode().value());
        assertEquals(200, getResult.getStatusCode().value());
        assertEquals(1, users.length);
        assertEquals(user1.getEmail(), getResult.getBody()[0].getEmail());
    }

    @Test
    public void getUserWithId_ValidUserId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        UUID user1ID = postResult.getBody().getId();

        URI newURI = new URI(baseUrl + port + "/user/" + user1ID.toString());
        ResponseEntity<User> getResult = restTemplate.getForEntity(newURI, User.class);

        assertEquals(200, postResult.getStatusCode().value());
        assertEquals(200, getResult.getStatusCode().value());

    }

    @Test
    public void getUserWithId_InvalidUserId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        UUID testId = UUID.randomUUID();
        uri = new URI(baseUrl + port + "/user/" + testId);

        ResponseEntity<User> result = restTemplate.getForEntity(uri, User.class);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void getUserWithEmail_ValidUserEmail_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        uri = new URI(baseUrl + port + "/user/email/" + user1.getEmail());

        ResponseEntity<User> result = restTemplate.getForEntity(uri, User.class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(user1.getEmail(), result.getBody().getEmail());
    }

    @Test
    public void getUserWithEmail_InvalidUserEmail_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        String testEmail = "abc@gmail.com";
        uri = new URI(baseUrl + port + "/user/email/" + testEmail);

        ResponseEntity<User> result = restTemplate.getForEntity(uri, User.class);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void getUserByBusiness_ValidBusinessId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        UUID businessID = business.getId();

        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        URI newURI = new URI(baseUrl + port + "/user/business/" + businessID.toString());
        ResponseEntity<User[]> getResult = restTemplate.getForEntity(newURI, User[].class);

        assertEquals(200, postResult.getStatusCode().value());
        assertEquals(200, getResult.getStatusCode().value());
    }

    @Test
    public void getUserByBusiness_InvalidBusinessId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userRawPassword = "goodpassword";
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        UUID falseBusinessId = UUID.randomUUID();
        URI newURI = new URI(baseUrl + port + "/user/business/" + falseBusinessId.toString());
        ResponseEntity<User> getResult = restTemplate.getForEntity(newURI, User.class);

        assertEquals(200, postResult.getStatusCode().value());
        assertEquals(404, getResult.getStatusCode().value());
    }

    @Test
    public void addUser_UserAlreadyRegistered_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        final String userRawPassword = "goodpassword";
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);
        ResponseEntity<User> secondPostResult = restTemplate.postForEntity(uri, user1, User.class);

        assertEquals(404, secondPostResult.getStatusCode().value());
    }

    @Test
    public void updateUser_ExistingUser_Success() throws Exception{
        URI uri = new URI(baseUrl + port + "/user");
        final String userRawPassword = "goodpassword";

        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        User newUser = postResult.getBody();
        newUser.setEmail("bad@gmail.com");

        ResponseEntity<User> result = restTemplate.withBasicAuth(user1.getEmail(), userRawPassword)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(newUser), User.class);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(newUser.getId(), result.getBody().getId());
    }

    @Test
    public void updateUser_UserNotFound_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        final String userRawPassword = "goodpassword";

        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        User user1 = new User("admin@abc.com", "admin", userRawPassword, "ROLE_ADMIN", business);

        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user1, User.class);

        User newUser = postResult.getBody();
        newUser.setId(UUID.randomUUID());

        ResponseEntity<User> result = restTemplate.withBasicAuth(user1.getEmail(), userRawPassword)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(newUser), User.class);

        assertEquals(404, result.getStatusCode().value());
    }
}
