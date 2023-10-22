package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.exceptions.LoginException;
import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.responses.UserResponse;
import com.intuit.sportseventsregistration.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUserSuccess() {
        LoginRequest request = new LoginRequest();
        UserResponse userResponse = new UserResponse();
        Mockito.when(loginService.loginUser(request)).thenReturn(userResponse);

        ResponseEntity<?> result = loginController.loginUser(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testLoginUserLoginException() {
        LoginRequest request = new LoginRequest();

        Mockito.when(loginService.loginUser(request)).thenThrow(new LoginException("Login exception"));

        ResponseEntity<?> result = loginController.loginUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testLoginUserException() {
        LoginRequest request = new LoginRequest();

        Mockito.when(loginService.loginUser(request)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> result = loginController.loginUser(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
