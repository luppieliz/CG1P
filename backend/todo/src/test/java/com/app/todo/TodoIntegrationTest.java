package com.app.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.app.todo.jwt.resource.JwtTokenRequest;
import com.app.todo.jwt.resource.JwtTokenResponse;
import com.app.todo.todo.TodoRepository;
import com.app.todo.todo.Todo;
import com.app.todo.user.UserRepository;
import com.app.todo.user.User;
import com.app.todo.business.BusinessRepository;
import com.app.todo.business.Business;
import com.app.todo.industry.IndustryRepository;
import com.app.todo.industry.Industry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(
        properties = {
                "jwt.signing.key.secret=mySecret",
                "jwt.get.token.uri=/authenticate",
                "jwt.refresh.token.uri=/refresh",
                "jwt.http.request.header=Authorization",
                "jwt.token.expiration.in.seconds=604800"
        }
)
public class TodoIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

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

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Test
    public void getCreatedTodosByUserId_ValidUserId_Success() throws Exception { // restclient -> HttpMessageNotReadableException
        URI uri = new URI(baseUrl + port + "/user");
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));

        final String ownerPassword = encoder.encode("password1");
        final String employeePassword = encoder.encode("password2");

        User owner = new User("alice@gmail.com", "Alice", ownerPassword, false,false, "ROLE_BUSINESSOWNER", business);
        User employee = new User("bob@gmail.com", "Bob", employeePassword,false,false, "ROLE_EMPLOYEE", business);

        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);

        UUID ownerId = postResultOwner.getBody().getId();
        UUID employeeId = postResultEmployee.getBody().getId();

        Date createdDate = Date.valueOf(LocalDate.now());
        List<UUID> createdForIds = new ArrayList<>();
        createdForIds.add(employeeId);

        Todo todo1 = new Todo(owner, createdDate,"Submit Vaccination Status", createdForIds);

        uri = new URI(baseUrl + port + "/authenticate");
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(owner.getEmail(), ownerPassword);
        ResponseEntity<JwtTokenResponse> test1 = null;
        try {
            test1 = restTemplate.postForEntity(uri, jwtTokenRequest, JwtTokenResponse.class);
            System.out.println(test1.getBody().getToken());
        } catch (Exception ex) {
            System.out.println("Failed authentication!");
        }

        String token = test1.getBody().getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Todo> request = new HttpEntity<>(todo1,headers);

        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
        ResponseEntity<Void> postTodo = restTemplate.exchange(uri, HttpMethod.POST,request,Void.class);

        uri = new URI(baseUrl + port + "/" + ownerId + "/todos/created");
        request = new HttpEntity<>(headers);
        ResponseEntity<Todo[]> getTodo = restTemplate.exchange(uri, HttpMethod.GET,request,Todo[].class);

        assertEquals(200, getTodo.getStatusCodeValue());
        assertEquals(1, getTodo.getBody().length);

    }
//
//    @Test
//    public void getAssignedTodoByUserId_ValidUserId_Success() throws Exception { // HttpMessageConversionException:Granted Authority
//        URI uri = new URI(baseUrl + port + "/user");
//        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
//        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
//        final String userRawPassword = "password1";
//        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
//        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);
//
//        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
//        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
//
//        UUID ownerId = postResultOwner.getBody().getId();
//        UUID employeeId = postResultEmployee.getBody().getId();
//
//        Date targetDate = Date.valueOf(LocalDate.now());
//        List<UUID> createdForIds = new ArrayList<UUID>();
//        createdForIds.add(employeeId);
//
//        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
//        todo.setCreatedBy(owner);
//
//        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
//        ResponseEntity<Void> postResultTodo = restTemplate.withBasicAuth(owner.getEmail(), userRawPassword)
//                                            .postForEntity(uri, todo, Void.class);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
//        ResponseEntity<Todo[]> result = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo[].class);
//        Todo[] todos = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(1, todos.length);
//    }
//
//    @Test
//    public void getTodoByTodoId_ValidTodoId_Success() throws Exception {
//        URI uri = new URI(baseUrl + port + "/user");
//        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
//        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
//        final String userRawPassword = "password1";
//        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
//        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);
//
//        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
//        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
//
//        UUID ownerId = postResultOwner.getBody().getId();
//        UUID employeeId = postResultEmployee.getBody().getId();
//
//        Date targetDate = Date.valueOf(LocalDate.now());
//        List<UUID> createdForIds = new ArrayList<UUID>();
//        createdForIds.add(employeeId);
//
//        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
//        todo.setCreatedBy(owner);
//
//        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
//        ResponseEntity<Void> postResultTodo = restTemplate.withBasicAuth(owner.getEmail(), userRawPassword)
//                                            .postForEntity(uri, todo, Void.class);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
//        ResponseEntity<Todo[]> getResultTodoList = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo[].class);
//        Todo[] todos = getResultTodoList.getBody();
//        UUID todoId = todos[0].getId();
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/" + todoId);
//        ResponseEntity<Todo> result = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo.class);
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(1, todos.length);
//        assertEquals(todoId, result.getBody().getId());
//    }
//
//    @Test
//    public void addTodowithUserId_ValidUserId_Success() throws Exception {
//        URI uri = new URI(baseUrl + port + "/user");
//        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
//        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
//        final String userRawPassword = "password1";
//        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
//        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);
//
//        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
//        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
//
//        UUID ownerId = postResultOwner.getBody().getId();
//        UUID employeeId = postResultEmployee.getBody().getId();
//
//        Date targetDate = Date.valueOf(LocalDate.now());
//        List<UUID> createdForIds = new ArrayList<UUID>();
//        createdForIds.add(employeeId);
//
//        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
//        todo.setCreatedBy(owner);
//
//        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
//        ResponseEntity<Void> postResultTodo = restTemplate.withBasicAuth(owner.getEmail(), userRawPassword)
//                                            .postForEntity(uri, todo, Void.class);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
//        ResponseEntity<Todo[]> result = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo[].class);
//        Todo[] todos = result.getBody();
//
//        assertEquals(201, postResultTodo.getStatusCode().value());
//        assertEquals(1, todos.length);
//    }
//
//    @Test
//    public void updateTodowithTodoId_ValidTodoId_Success() throws Exception {
//        URI uri = new URI(baseUrl + port + "/user");
//        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
//        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
//        final String userRawPassword = "password1";
//        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
//        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);
//
//        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
//        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
//
//        UUID ownerId = postResultOwner.getBody().getId();
//        UUID employeeId = postResultEmployee.getBody().getId();
//
//        Date targetDate = Date.valueOf(LocalDate.now());
//        List<UUID> createdForIds = new ArrayList<UUID>();
//        createdForIds.add(employeeId);
//
//        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
//        todo.setCreatedBy(owner);
//
//        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
//        ResponseEntity<Void> postResultTodo = restTemplate.withBasicAuth(owner.getEmail(), userRawPassword)
//                                            .postForEntity(uri, todo, Void.class);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
//        ResponseEntity<Todo[]> getResultTodoList = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo[].class);
//        Todo[] todos = getResultTodoList.getBody();
//        UUID todoId = todos[0].getId();
//
//        Todo newTodo = new Todo("Submit Vaccination Form", targetDate, createdForIds);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/" + todoId);
//        ResponseEntity<Todo> putResultTodo = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .exchange(uri, HttpMethod.PUT, new HttpEntity<>(newTodo), Todo.class);
//
//        assertEquals(200, putResultTodo.getStatusCode().value());
//        assertEquals(newTodo.getDescription(), putResultTodo.getBody().getDescription());
//    }
//
//    @Test
//    public void deleteTodowithTodoId_ValidTodoId_Success() throws Exception {
//        URI uri = new URI(baseUrl + port + "/user");
//        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
//        Business business = businessRepository.save(new Business("asd789fhgj", "Singapore Museum", industry));
//        final String userRawPassword = "password1";
//        User owner = new User("alice@gmail.com", "Alice", userRawPassword, "ROLE_BUSINESSOWNER", business);
//        User employee = new User("bob@gmail.com", "Bob", "password1", "ROLE_EMPLOYEE", business);
//
//        ResponseEntity<User> postResultOwner = restTemplate.postForEntity(uri, owner, User.class);
//        ResponseEntity<User> postResultEmployee = restTemplate.postForEntity(uri, employee, User.class);
//
//        UUID ownerId = postResultOwner.getBody().getId();
//        UUID employeeId = postResultEmployee.getBody().getId();
//
//        Date targetDate = Date.valueOf(LocalDate.now());
//        List<UUID> createdForIds = new ArrayList<UUID>();
//        createdForIds.add(employeeId);
//
//        Todo todo = new Todo("Submit Vaccination Status", targetDate, createdForIds);
//        todo.setCreatedBy(owner);
//
//        uri = new URI(baseUrl + port + "/" + ownerId + "/todos");
//        ResponseEntity<Void> postResultTodo = restTemplate.withBasicAuth(owner.getEmail(), userRawPassword)
//                                            .postForEntity(uri, todo, Void.class);
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/assigned");
//        ResponseEntity<Todo[]> getResultTodoList = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .getForEntity(uri, Todo[].class);
//        Todo[] todos = getResultTodoList.getBody();
//        UUID todoId = todos[0].getId();
//
//        uri = new URI(baseUrl + port + "/" + employeeId + "/todos/" + todoId);
//        ResponseEntity<Void> result = restTemplate.withBasicAuth(employee.getEmail(), userRawPassword)
//                                        .exchange(uri, HttpMethod.DELETE, null, Void.class);
//
//        assertEquals(204, result.getStatusCode().value());
//        assertEquals(Optional.empty(), todoRepository.findById(todoId));
//    }
}
