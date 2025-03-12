package org.isp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/another-endpoint")
    public String anotherEndpoint() {
        return "This endpoint does not allow CORS!";
    }
}