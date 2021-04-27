package com.nixsolutions.ppp.controller;

import com.captcha.botdetect.web.servlet.SimpleCaptchaServlet;
import com.nixsolutions.ppp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@RestController
@CrossOrigin(origins = "http://localhost:8008")
@RequestMapping(produces = "application/json")
public class MainController extends SimpleCaptchaServlet {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password");
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange("http://localhost:8080/users", HttpMethod.POST, entity, Long.class);
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            super.doGet(request, response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
