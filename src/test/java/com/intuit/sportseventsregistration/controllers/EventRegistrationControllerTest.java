package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import com.intuit.sportseventsregistration.services.EventRegistrationService;
import com.intuit.sportseventsregistration.exceptions.EventException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventRegistrationControllerTest {

    @InjectMocks
    private EventRegistrationController eventRegistrationController;

    @Mock
    private EventRegistrationService eventRegistrationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterEventForUserSuccess() throws Exception {
        EventRegistrationRequest request = new EventRegistrationRequest();
        EventRegistrationResponse response = new EventRegistrationResponse();

        Mockito.when(eventRegistrationService.registerEvent(request)).thenReturn(response);

        ResponseEntity<?> result = eventRegistrationController.registerEventForUser(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRegisterEventForUserEventException() throws Exception {
        EventRegistrationRequest request = new EventRegistrationRequest();

        Mockito.when(eventRegistrationService.registerEvent(request)).thenThrow(new EventException("Event exception"));

        ResponseEntity<?> result = eventRegistrationController.registerEventForUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testRegisterEventForUserException() throws Exception {
        EventRegistrationRequest request = new EventRegistrationRequest();

        Mockito.when(eventRegistrationService.registerEvent(request)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> result = eventRegistrationController.registerEventForUser(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testUnregisterEventForUserSuccess() {
        EventUnregisterRequest request = new EventUnregisterRequest();
        String response = "SUCCESS";

        Mockito.when(eventRegistrationService.unregisterEvent(request)).thenReturn(response);

        ResponseEntity<?> result = eventRegistrationController.unregisterEventForUser(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testUnregisterEventForUserEventException() {
        EventUnregisterRequest request = new EventUnregisterRequest();

        Mockito.when(eventRegistrationService.unregisterEvent(request)).thenThrow(new EventException("Event exception"));

        ResponseEntity<?> result = eventRegistrationController.unregisterEventForUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testUnregisterEventForUserException() {
        EventUnregisterRequest request = new EventUnregisterRequest();

        Mockito.when(eventRegistrationService.unregisterEvent(request)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> result = eventRegistrationController.unregisterEventForUser(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}