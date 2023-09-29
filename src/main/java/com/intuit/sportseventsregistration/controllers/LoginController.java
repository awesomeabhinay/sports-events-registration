package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.LoginException;
import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    @PostMapping("login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(loginService.loginUser(loginRequest));
        } catch (Exception ex){
            throw new LoginException("username is not valid");
        }
    }
}
