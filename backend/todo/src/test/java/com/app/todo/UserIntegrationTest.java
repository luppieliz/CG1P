package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.business.BusinessRepository;
import com.app.todo.industry.Industry;
import com.app.todo.industry.IndustryRepository;
import com.app.todo.jwt.resource.JwtTokenRequest;
import com.app.todo.jwt.resource.JwtTokenResponse;
import com.app.todo.todo.Todo;
import com.app.todo.user.User;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import com.app.todo.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Test
    void addUser_Sucess() throws URISyntaxException {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User newUser = new User("admin@gmail", "admin", userPassword,  "ROLE_ADMIN" ,business);

        ResponseEntity<User> result = restTemplate.postForEntity(uri, newUser, User.class);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void getAllUsers_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User[]> getAllUser = restTemplate.exchange(uri, HttpMethod.GET, request, User[].class);
        assertEquals(200, getAllUser.getStatusCodeValue());
        assertEquals(1, getAllUser.getBody().length);
        assertEquals(user.getEmail(), getAllUser.getBody()[0].getEmail());
    }

    @Test
    public void getUserWithId_ValidUserId_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);
        UUID userID = postUser.getBody().getId();

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/" + userID.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User> getAllUser = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        assertEquals(200, getAllUser.getStatusCodeValue());
        assertEquals(user.getEmail(), getAllUser.getBody().getEmail());
    }

    @Test
    public void getUserWithId_InvalidUserId_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);
        UUID userID = postUser.getBody().getId();
        UUID randomID = UUID.randomUUID();

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/" + randomID.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User> getAllUser = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        assertEquals(404, getAllUser.getStatusCodeValue());
    }

    @Test
    public void getUserWithEmail_ValidUserEmail_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/email/" + user.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User> getAllUser = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        assertEquals(200, getAllUser.getStatusCodeValue());
        assertEquals(user.getEmail(), getAllUser.getBody().getEmail());
    }

    @Test
    public void getUserWithEmail_InvalidUserEmail_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);
        String testEmail = "notadmin@gmail.com";

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/email/" + testEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User> getAllUser = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        assertEquals(404, getAllUser.getStatusCodeValue());
    }

    @Test
    public void getUserByBusiness_ValidBusinessId_Success() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);
        UUID businessID = postUser.getBody().getBusiness().getId();

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/business/" + businessID.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User[]> getUser = restTemplate.exchange(uri, HttpMethod.GET, request, User[].class);
        assertEquals(200, getUser.getStatusCodeValue());
        assertEquals(businessID, getUser.getBody()[0].getBusiness().getId());
    }

    @Test
    public void getUserByBusiness_InvalidBusinessId_Failure() throws Exception {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User user = new User("admin@gmail.com", "admin", userPassword, false,false, "ROLE_ADMIN", business);

        URI uri = new URI(baseUrl + port + "/user");
        ResponseEntity<User> postUser = restTemplate.postForEntity(uri, user, User.class);
        UUID testID = UUID.randomUUID();

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(user.getEmail(), userPassword);
        ResponseEntity<JwtTokenResponse> postToken = null;

        try {
            postToken = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = postToken.getBody().getToken();

        uri = new URI(baseUrl + port + "/user/business/" + testID.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> request = new HttpEntity<>(headers);

        ResponseEntity<User> getUser = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        assertEquals(404, getUser.getStatusCodeValue());
    }

    @Test
    public void addUser_UserAlreadyRegistered_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User newUser = new User("admin@gmail", "admin", userPassword,  "ROLE_ADMIN" ,business);

        ResponseEntity<User> postOne = restTemplate.postForEntity(uri, newUser, User.class);

        ResponseEntity<User> postTwo = restTemplate.postForEntity(uri, newUser, User.class);

        assertEquals(404, postTwo.getStatusCodeValue());
    }

    @Test
    public void updateUser_ExistingUser_Success() throws Exception{
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User newUser = new User("admin@gmail", "admin", userPassword,  "ROLE_ADMIN" ,business);

        ResponseEntity<User> postOne = restTemplate.postForEntity(uri, newUser, User.class);

        postOne.getBody().setName("admin1");

        HttpEntity<User> request = new HttpEntity<>(postOne.getBody());

        ResponseEntity<User> updateRequest = restTemplate.exchange(uri, HttpMethod.PUT ,request, User.class);

        assertEquals(200, updateRequest.getStatusCodeValue());
        assertEquals(newUser.getEmail(), updateRequest.getBody().getUsername());
    }

    @Test
    public void updateUser_UserNotFound_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        String userPassword = encoder.encode("goodpassword");
        User newUser = new User("admin@gmail", "admin", userPassword,  "ROLE_ADMIN" ,business);

        ResponseEntity<User> postOne = restTemplate.postForEntity(uri, newUser, User.class);

        postOne.getBody().setName("admin1");
        postOne.getBody().setId(UUID.randomUUID());

        HttpEntity<User> request = new HttpEntity<>(postOne.getBody());

        ResponseEntity<User> updateRequest = restTemplate.exchange(uri, HttpMethod.PUT ,request, User.class);

        assertEquals(404, updateRequest.getStatusCodeValue());
    }
}
