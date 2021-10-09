package com.app.todo.authentication;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/basicauth")
public class BasicAuthenticationController {
    @GetMapping
    public AuthenticationBean authenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationBean("You are authenticated");
    }

    @PostMapping
    public AuthenticationBean postauthenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationBean("You are authenticated");
    }
}
