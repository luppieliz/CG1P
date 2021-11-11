//package com.app.todo.authentication;
//
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
//@RestController
//@RequestMapping("/basicauth")
//public class BasicAuthenticationController {
//    @ApiOperation(value = "Check whether user is successfully authenticated")
//    @GetMapping(produces = "application/json")
//    public AuthenticationBean authenticate() {
//        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
//        return new AuthenticationBean("You are authenticated");
//    }
//
//    @ApiOperation(value = "Check whether user is successfully authenticated")
//    @PostMapping(produces = "application/json")
//    public AuthenticationBean postauthenticate() {
//        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
//        return new AuthenticationBean("You are authenticated");
//    }
//}
