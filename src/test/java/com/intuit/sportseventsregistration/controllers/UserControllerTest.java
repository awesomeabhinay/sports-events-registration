package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.responses.UserResponse;
import com.intuit.sportseventsregistration.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccess() {
        User user = new User();
        UserResponse userResponse = new UserResponse();
        Mockito.when(userService.createUser(user)).thenReturn(userResponse);

        ResponseEntity<?> result = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testCreateUserUserException() {
        User user = new User();

        Mockito.when(userService.createUser(user)).thenThrow(new UserException("User exception"));

        ResponseEntity<?> result = userController.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testCreateUserException() {
        User user = new User();

        Mockito.when(userService.createUser(user)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> result = userController.createUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}