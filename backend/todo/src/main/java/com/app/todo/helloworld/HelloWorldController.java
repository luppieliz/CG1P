package com.app.todo.helloworld;

import org.springframework.web.bind.annotation.*;

// Spring demo class
@RestController
@CrossOrigin(origins = "http://localhost:4200/") // allow cross origin requests (i.e., from port 4200 to 8080)
// Controller - Handle HTTP Requests
@RequestMapping("/hello-world")
public class HelloWorldController {

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path = "/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }


}
