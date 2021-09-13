package com.app.todo.helloworld;

import org.springframework.web.bind.annotation.*;

// Spring demo class
@RestController
@CrossOrigin(origins = "http://localhost:4200/") // allow cross origin requests (i.e., from port 4200 to 8080)
// Controller - Handle HTTP Requests
public class HelloWorldController {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }


}
