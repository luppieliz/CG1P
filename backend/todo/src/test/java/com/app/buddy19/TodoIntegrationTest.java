package com.app.buddy19;

import com.app.buddy19.business.Business;
import com.app.buddy19.business.BusinessRepository;
import com.app.buddy19.industry.Industry;
import com.app.buddy19.industry.IndustryRepository;
import com.app.buddy19.jwt.resource.JwtTokenResponse;
import com.app.buddy19.todo.Todo;
import com.app.buddy19.todo.TodoRepository;
import com.app.buddy19.user.User;
import com.app.buddy19.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TodoIntegrationTest {
    private final String baseUrl = "http://localhost:";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private String getBody(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }

    @Test
    public void getCreatedTodosByUserId_ValidUserId_Success() throws Exception { // restclient -> HttpMessageNotReadableException
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        // Authenticate User and get JWT
        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        HttpEntity<Todo> jwtEntityPost = new HttpEntity<Todo>(todo, headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        restTemplate.exchange(uri, HttpMethod.POST, jwtEntityPost, Todo.class);

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo[].class);
        Todo[] result = getResultTodoList.getBody();

        assertEquals(200, getResultTodoList.getStatusCode()
                                           .value());
        assertEquals(true, (todo.getDescription()).equals(result[0].getDescription())); // can only check by desc since original id not avail
    }

    @Test
    public void getAssignedTodoByUserId_ValidUserId_Success() throws Exception { // HttpMessageConversionException:Granted Authority
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication for owner to post
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<Todo> jwtEntityOwner = new HttpEntity<Todo>(todo, headers);

        // authentication for employee to get
        authenticationBody = getBody(employee);
        authenticationHeaders = getHeaders();
        authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        token = "Bearer " + authenticationResponse.getBody()
                                                  .getToken();
        headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntityEmployee = new HttpEntity<String>(headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        ResponseEntity<Todo> postResultTodo = restTemplate.exchange(uri, HttpMethod.POST, jwtEntityOwner, Todo.class);
        assertEquals(201, postResultTodo.getStatusCode()
                                        .value());

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntityEmployee, Todo[].class);
        Todo[] result = getResultTodoList.getBody();

        assertEquals(200, getResultTodoList.getStatusCode()
                                           .value());
        assertEquals(true, (todo.getDescription()).equals(result[0].getDescription())); // can only check by desc since original id not avail
    }

    @Test
    public void getTodoByTodoId_ValidTodoId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        // Authenticate User and get JWT
        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        HttpEntity<Todo> jwtEntityPost = new HttpEntity<Todo>(todo, headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        restTemplate.exchange(uri, HttpMethod.POST, jwtEntityPost, Todo.class);

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo[].class);
        Todo[] todoList = getResultTodoList.getBody();
        UUID todoId = todoList[0].getId();

        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/" + todoId);
        ResponseEntity<Todo> result = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo.class);

        assertEquals(200, result.getStatusCode()
                                .value());
        assertEquals(todoId, result.getBody()
                                   .getId());
    }

    @Test
    public void addTodowithUserId_ValidUserId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        // Authenticate User and get JWT
        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        HttpEntity<Todo> jwtEntityPost = new HttpEntity<Todo>(todo, headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        ResponseEntity<Todo> postResultTodo = restTemplate.exchange(uri, HttpMethod.POST, jwtEntityPost, Todo.class);

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo[].class);
        Todo[] todoList = getResultTodoList.getBody();

        assertEquals(201, postResultTodo.getStatusCode()
                                        .value());
        assertEquals(1, todoList.length);
    }

    @Test
    public void updateTodowithTodoId_ValidTodoId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        // Authenticate User and get JWT
        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        HttpEntity<Todo> jwtEntityPost = new HttpEntity<Todo>(todo, headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        restTemplate.exchange(uri, HttpMethod.POST, jwtEntityPost, Todo.class);

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo[].class);
        Todo[] result = getResultTodoList.getBody();
        UUID todoId = result[0].getId();

        Todo newTodo = new Todo("Submit Vaccination Form", targetDate, createdForIds);
        jwtEntityPost = new HttpEntity<Todo>(newTodo, headers);

        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/" + todoId);
        ResponseEntity<Todo> putResultTodo = restTemplate.exchange(uri, HttpMethod.PUT, jwtEntityPost, Todo.class);

        assertEquals(200, putResultTodo.getStatusCode()
                                       .value());
        assertEquals(newTodo.getDescription(), putResultTodo.getBody()
                                                            .getDescription());
    }

    @Test
    public void deleteTodowithTodoId_ValidTodoId_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
        final String userRawPassword = "password1";
        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
        UUID ownerId = postResultOwner.getBody()
                                      .getId();
        UUID employeeId = postResultEmployee.getBody()
                                            .getId();

        Date targetDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<UUID>();
        createdForIds.add(employeeId);

        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
        todo.setCreatedBy(owner);

        // authentication
        String authenticationBody = getBody(owner);
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

        String AUTHENTICATION_URL = baseUrl + port + "/authenticate";

        // Authenticate User and get JWT
        ResponseEntity<JwtTokenResponse> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authenticationEntity, JwtTokenResponse.class);

        String token = "Bearer " + authenticationResponse.getBody()
                                                         .getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        HttpEntity<Todo> jwtEntityPost = new HttpEntity<Todo>(todo, headers);

        // Use Token to post Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        restTemplate.exchange(uri, HttpMethod.POST, jwtEntityPost, Todo.class);

        // Use Token to get Response
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        ResponseEntity<Todo[]> getResultTodoList = restTemplate.exchange(uri, HttpMethod.GET, jwtEntity, Todo[].class);
        Todo[] result = getResultTodoList.getBody();
        UUID todoId = result[0].getId();

        HttpEntity<Todo> jwtEntityDelete = new HttpEntity<Todo>(null, headers);
        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/" + todoId);
        ResponseEntity<Void> deleteResultTodo = restTemplate.exchange(uri, HttpMethod.DELETE, jwtEntityDelete, Void.class);

        assertEquals(204, deleteResultTodo.getStatusCode()
                                          .value());
        assertEquals(Optional.empty(), todoRepository.findById(todoId));
    }
}
