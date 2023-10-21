package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.exceptions.LoginException;
import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(loginService.loginUser(loginRequest));
        } catch (LoginException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while login.");
        }
    }
}
