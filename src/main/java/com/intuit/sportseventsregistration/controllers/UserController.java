package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.services.UserService;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        } catch (Exception ex){
            throw new UserException(String.format(Constants.USER_CREATION_ERROR_MESSAGE,user.getUsername()));
        }
    }
}
